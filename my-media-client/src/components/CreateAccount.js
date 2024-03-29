import { useContext, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { register } from "../services/authenticationService";
import AuthContext from "./AuthContext";

function CreateAccount() {
  const [credentials, setCredentials] = useState({
    username: "",
    password: "",
    passwordConfirm: "",
  });

  const { login } = useContext(AuthContext);
  const [errs, setErrs] = useState([]);
  const navigate = useNavigate();

  function handleChange(evt) {
    evt.preventDefault();
    const newCredentials = { ...credentials };
    newCredentials[evt.target.name] = evt.target.value;
    setCredentials(newCredentials);
  }

  function handleSubmit(evt) {
    evt.preventDefault();
    evt.stopPropagation();
    if (credentials["password"] !== credentials["passwordConfirm"]) {
      setErrs(["Passwords do not match!"]);
      return;
    }

    register(credentials)
      .then((user) => {
        login(user);
        navigate("/profile");
      })
      .catch(setErrs);
  }

  return (
    <div className="flex items-center justify-center">
      <form
        id="registrationForm"
        onSubmit={handleSubmit}
        className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 mt-10 w-full max-w-xs m-5"
      >
        <div className="mb-6">
          <label className="block mb-2" htmlFor="username">
            Username
          </label>
          <input
            className="w-full shadow p-2"
            id="username"
            name="username"
            placeholder="Username"
            onChange={handleChange}
            value={credentials["username"]}
            autoComplete="new-password"
          />
        </div>
        <div className="mb-6">
          <label className="block mb-2" htmlFor="password">
            Password
          </label>
          <input
            className="w-full shadow p-2"
            id="password"
            name="password"
            type="password"
            placeholder="********"
            onChange={handleChange}
            value={credentials["password"]}
            autoComplete="new-password"
          />
        </div>
        <div className="mb-6">
          <label className="block mb-2" htmlFor="passwordConfirm">
            Confirm Password
          </label>
          <input
            className="w-full shadow p-2"
            id="passwordConfirm"
            name="passwordConfirm"
            type="password"
            placeholder="********"
            onChange={handleChange}
            value={credentials["passwordConfirm"]}
            autoComplete="off"
          />
        </div>
        <div className="flex items-center justify-between mx-8">
          <button id="createAccount" className="btn btn-green" type="submit">
            Register
          </button>
          <Link to="/login">
            <button id="login" className="btn btn-blue">
              Login
            </button>
          </Link>
        </div>
        {errs.length > 0 ? (
          <ul id="errors" className="err-box">
            {errs.map((e) => {
              return <li key={e}>{e}</li>;
            })}
          </ul>
        ) : (
          <></>
        )}
      </form>
    </div>
  );
}

export default CreateAccount;
