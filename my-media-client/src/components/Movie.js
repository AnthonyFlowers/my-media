import { ErrorResponse } from "@remix-run/router";
import { useContext, useState } from "react";
import { Link } from "react-router-dom";
import { addMovieToUser, deleteUserMovieById, getUserMovies, updateUserMovie } from "../services/movieService";
import AuthContext from "./AuthContext";

export default function Movie({ movie }) {

    const { user } = useContext(AuthContext);
    const [errs, setErrs] = useState([]);
    const [success, setSuccess] = useState(false);

    function handleAdd() {
        addMovieToUser(movie)
            .then((msg) => {
                // show success message
                console.log(msg)
                setSuccess(true);
            })
            .catch((e) => {
                setErrs(e);

            });
    }

    return (
        <div className="media-card-lg group">
            <h1 className="text-xl font-bold">{movie.movieName}</h1>
            <div className="details">
                <p className="attribute">Length: {movie.movieLength} Minutes</p>
                <p className="attribute">Year: {movie.movieYear}</p>
                <p className="overview group-hover:h-auto">Overview: {movie.movieOverview}</p>
            </div>
            {
                user && !success ? <button className="btn-media-add" onClick={handleAdd}>Add</button> : <></>
            }
            {
                success ? <div role="alert">
                    <p className="bg-green-500 text-white font-bold rounded px-4 py-2 mt-2">Movie Added</p>
                </div> : <></>
            }
            {
                errs.length > 0 ? <div role="alert">
                    <p className="bg-orange-500 text-white font-bold rounded-t px-4 py-2 mt-2">Could not add that movie.</p>
                    <p className="border border-t-0 border-orange-400 rounded-b bg-orange-100 px-4 py-3 text-orange-700">
                        {!user ?
                            <>You need to be <Link className="link" to="/login">logged in</Link> to add movies to your list</> :
                            <>You already added that movie to your list update it <Link className="link" to="/movies/user">here</Link></>}
                    </p>
                </div> : <></>
            }
        </div>
    )
}

export function SmallMovie({ movie }) {

    return (
        <div className="m-2 text-gray-600 border">
            <p className="font-bold">{movie.movieName}</p>
            <p className="">Length: {movie.movieLength} Minutes</p>
            <p className="">Year: {movie.movieYear}</p>
            <p>Overview: {movie.movieOverview}</p>
        </div>
    )
}

export function ListMovie({ m, setUserMovies, setErr }) {
    const [userMovie, setMovie] = useState(m);

    function toggleWatched(evt) {
        const nextMovie = { ...userMovie };
        nextMovie.watched = evt.target.checked;
        setMovie(nextMovie);
        updateUserMovie(nextMovie);
    }

    function handleDelete(evt) {
        console.log(evt.target.value)
        deleteUserMovieById(evt.target.value)
            .then(() => {
                getUserMovies()
                    .then((page) => {
                        setUserMovies(page["content"])
                    })
                    .catch(setErr);
            })
            .catch(setErr);
    }
    return (
        <li>
            <input type="checkbox"
                checked={userMovie.watched}
                onChange={toggleWatched}
            />
            {userMovie.movie.movieName}, Year: {userMovie.movie.movieYear}, Length: {userMovie.movie.movieLength}
            <button type="button"
                className="btn-small btn-red"
                value={userMovie.appUserMovieId}
                onClick={handleDelete}>
                Remove
            </button>
        </li>
    )
}