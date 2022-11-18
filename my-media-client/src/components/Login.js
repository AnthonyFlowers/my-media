import { useContext, useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { authenticate } from "../services/authenticationService";
import AuthContext from "./AuthContext";
import Home from "./Home";

function Login() {

    const [credentials, setCredentials] = useState({
        username: "",
        password: ""
    });

    const { user, login } = useContext(AuthContext);
    const [errs, setErrs] = useState([]);
    const navigate = useNavigate();

    function handleChange(evt) {
        evt.preventDefault();
        const newCredentials = { ...credentials };
        newCredentials[evt.target.name] = evt.target.value;
        setCredentials(newCredentials);
    }

    function handleSubmit(evt) {
        evt.preventDefault()
        evt.stopPropagation();

        authenticate(credentials)
            .then(user => {
                login(user);
                navigate(-1);
            })
            .catch(setErrs);
    }

    return (
        <div className="flex items-center justify-center">
            <form onSubmit={handleSubmit} className="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 mt-10 w-full max-w-xs m-5">
                <div className="mb-6">
                    <label className="block mb-2" htmlFor="username">Username</label>
                    <input
                        className="w-full shadow p-2"
                        id="username" name="username" placeholder="Username"
                        onChange={handleChange} value={credentials['username']} />
                </div>
                <div className="mb-6">
                    <label className="block mb-2" htmlFor="password">Password</label>
                    <input className="w-full shadow p-2"
                        id="password" name="password" type="password" placeholder="********"
                        onChange={handleChange} value={credentials['password']} />
                </div>
                <div className="flex items-center justify-between">
                    <button className="btn btn-blue" type="submit">Login</button>
                    <Link to="/register">
                        <button className="btn btn-yellow">Create Account</button>
                    </Link>
                </div>
            </form>
        </div>
    )
}

export default Login;