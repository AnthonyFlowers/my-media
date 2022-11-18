import { useEffect, useState } from "react";
import { getMovies } from "../services/movieService";
import Movie from "./Movie";

function Movies() {

    const [movies, setMovies] = useState([]);
    const [errs, setErrs] = useState([]);

    useEffect(() => {
        getMovies()
        .then(setMovies)
        .catch(setErrs);
    }, [])

    return (
        <div>
            <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 items-center justify-center py-4">
            {
                movies.map((m) => {
                    return <Movie key={m.movieId} movie={m}/>;
                })
            }
            {
                movies.map((m) => {
                    return <Movie key={m.movieId} movie={m}/>;
                })
            }
            </div>
        </div>
    )
}

export default Movies;