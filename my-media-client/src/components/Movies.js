import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Movie from "./Movie";
import MoviePageNav from "./MoviePageNav";

function Movies({ movieQueury }) {

    const [movies, setMovies] = useState([]);
    const [movieNavPages, setMovieNavPages] = useState({
        start: 1,
        last: 1
    });
    const [moviePage, setMoviePage] = useState(1);
    const [searchParams] = useSearchParams();
    const [errs, setErrs] = useState([]);

    useEffect(() => {
        movieQueury(moviePage)
            .then((page) => {
                setMovies(page.content);
                setMoviePage(searchParams.get("page"));
            })
            .catch(setErrs);
    }, [movieQueury, moviePage, searchParams]);

    return (
        <div>
            <MoviePageNav />
            <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 items-center justify-center py-4">
                {
                    movies.map((m) => {
                        return <Movie key={m.movieId} movie={m} />;
                    })
                }
            </div>
            <div className="">{errs.map((e) => { return <p key={e}>{e}</p> })}</div>
        </div>
    )
}

export default Movies;