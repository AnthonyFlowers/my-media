import { useState } from "react";
import { addMovieToUser, deleteUserMovie, getUserMovies, updateUserMovie } from "../services/movieService";

function Movie({ movie }) {
    function handleAdd() {
        addMovieToUser(movie)
            .then((msg) => {
                // show success message
                console.log(msg)
            })
            .catch(console.log);
    }

    return (
        <div className="bg-gray-100 p-10 my-2 rounded-lg shadow-md">
            <h1 className="text-xl font-bold">{movie.movieName}</h1>
            <div className="mt-2 mb-2">
                <p className="text-gray-600">Length: {movie.movieLength} Minutes</p>
                <p className="text-gray-600">Year: {movie.movieYear}</p>
                <p>Overview: {movie.movieOverview}</p>
                {/* check if movie already added */}
                <button onClick={handleAdd}>Add</button>
            </div>
        </div>
    )
}

export default Movie;

export function SmallUserMovie({ movie }) {

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
        deleteUserMovie(evt.target.value)
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