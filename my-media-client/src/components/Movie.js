import { useState } from "react";
import { addMovieToUser } from "../services/movieService";

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

export function ListMovie({ m }) {
    const [movie, setMovie] = useState(m);
    function toggleWatched() {
        const nextMovie = { ...movie };
        nextMovie["watched"] = !nextMovie["watched"];
        setMovie(nextMovie);
    }
    return (
        <li><input type="checkbox" value={movie.watched}/>{movie.movieName}, Year: {movie.movieYear}, Length: {movie.movieLength}</li>
    )
}