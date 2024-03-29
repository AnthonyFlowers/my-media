import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { deleteUserTvShow, getAllUserTvShows } from "../services/tvShowService";
import AuthContext from "./AuthContext";
import UserTvShow from "./UserTvShow";

function UserTvShows() {
  const { user } = useContext(AuthContext);
  const [userTvShows, setUserTvShows] = useState([]);
  const [errs, setErrs] = useState([]);

  useEffect(() => {
    getAllUserTvShows(user).then(setUserTvShows).catch(setErrs);
  }, [user]);

  function handleDelete(userTvShowID) {
    deleteUserTvShow(userTvShowID)
      .then(() => {
        getAllUserTvShows(user).then(setUserTvShows).catch(setErrs);
      })
      .catch(setErrs);
  }

  return (
    <div id="tvShows">
      <div className="media-container-lg">
        {userTvShows.length < 1 ? (
          <p>
            No shows found{" "}
            <Link className="btn" to="/tv-shows">
              Click to add some!
            </Link>
          </p>
        ) : (
          <></>
        )}
        {userTvShows.map((t) => {
          return (
            <UserTvShow
              key={t.appUserTvShowId}
              uts={t}
              handleDelete={handleDelete}
            />
          );
        })}
      </div>
    </div>
  );
}

export default UserTvShows;
