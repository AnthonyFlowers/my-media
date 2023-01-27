import { useContext, useState } from "react";
import { Link } from "react-router-dom";
import {
  addTvShowToUser,
  deleteUserTvShow,
  getUserTvShows,
  updateUserTvShow,
} from "../services/tvShowService";
import AuthContext from "./AuthContext";

export default function TvShow({ tvShow }) {
  const { user } = useContext(AuthContext);
  const [success, setSuccess] = useState(false);
  const [errs, setErrs] = useState([]);

  function handleAdd() {
    addTvShowToUser(tvShow)
      .then((msg) => {
        // show success message
        console.log(msg);
        setSuccess(true);
      })
      .catch((e) => {
        setErrs(e);
        setSuccess(false);
      });
  }

  return (
    <div className="media-card-lg group">
      <h1 className="text-xl font-bold">{tvShow.tvShowName}</h1>
      <div className="details">
        <p className="attribute">Year: {tvShow.releaseYear}</p>
        <p className="overview group-hover:h-auto">
          Overview: {tvShow.overview}
        </p>
        {user && !success ? (
          <button className="btn-media-add" onClick={handleAdd}>
            Add
          </button>
        ) : (
          <></>
        )}
        {success ? (
          <div role="alert">
            <p className="bg-green-500 text-white font-bold rounded px-4 py-2 mt-2">
              Show Added
            </p>
          </div>
        ) : (
          <></>
        )}
        {errs.length > 0 ? (
          <div role="alert">
            <p className="bg-orange-500 text-white font-bold rounded-t px-4 py-2 mt-2">
              Could not add that show
            </p>
            <p className="border border-t-0 border-orange-400 rounded-b bg-orange-100 px-4 py-3 text-orange-700">
              {!user ? (
                <>
                  You need to be{" "}
                  <Link className="link" to="/login">
                    logged in
                  </Link>{" "}
                  to add shows to your list
                </>
              ) : (
                <>
                  You already added that show to your list update it{" "}
                  <Link className="link" to="/tv-shows/user">
                    here
                  </Link>
                </>
              )}
            </p>
          </div>
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}

export function SmallTvShow({ tvShow }) {
  return (
    <div className="m-2 text-gray-600 border">
      <p className="font-bold">{tvShow.tvShowName}</p>
      <p className="">Year: {tvShow.releaseYear}</p>
      <p>Overview: {tvShow.overview}</p>
    </div>
  );
}

export function ListTvShow({ t, setUserTvShows, setErrs }) {
  const [userTvShow, setUserTvShow] = useState(t);

  function toggleWatched(evt) {
    const nextUserTvShow = { ...userTvShow };
    nextUserTvShow.watched = evt.target.checked;
    setUserTvShow(nextUserTvShow);
    updateUserTvShow(nextUserTvShow);
  }

  function handleDelete(evt) {
    deleteUserTvShow(evt.target.value)
      .then(() => {
        getUserTvShows()
          .then((page) => {
            setUserTvShows(page["content"]);
          })
          .catch(setErrs);
      })
      .catch(setErrs);
  }

  function handleSeasonChange(evt) {
    const nextValue = evt.target.value;
    if (nextValue > 100 || nextValue < 0) {
      return;
    }
    const nextUserTvShow = { ...userTvShow };
    nextUserTvShow[evt.target.name] = evt.target.value;
    updateUserTvShow(nextUserTvShow)
      .then(() => {
        getUserTvShows().then((page) => {
          setUserTvShows(page["content"]);
          setUserTvShow(nextUserTvShow);
        });
      })
      .catch((err) => {
        setErrs(err);
      });
  }

  return (
    <li>
      <input
        type="checkbox"
        checked={userTvShow.watched}
        onChange={toggleWatched}
      />
      {userTvShow.tvShow.tvShowName}, Year: {userTvShow.tvShow.releaseYear},
      Season:{" "}
      <input
        name="season"
        type="number"
        value={userTvShow.season}
        onChange={handleSeasonChange}
      />
      , Episode:{" "}
      <input
        name="episode"
        type="number"
        max="100"
        value={userTvShow.episode}
        onChange={handleSeasonChange}
      />
      <button
        type="button"
        className="btn-small btn-red"
        value={userTvShow.appUserTvShowId}
        onClick={handleDelete}
      >
        Remove
      </button>
    </li>
  );
}
