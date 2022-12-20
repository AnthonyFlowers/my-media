import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Movie from "./Movie";
import MediaPageNav from "./MediaPageNav";
import { getMoviesSearch } from "../services/movieService";

function Movies() {

    const [movies, setMovies] = useState([]);
    const [movieNavPages, setMovieNavPages] = useState({
        start: 1,
        current: 1,
        end: 1
    });
    const [searchParams, setSearchParams] = useSearchParams();
    const [errs, setErrs] = useState([]);

    useEffect(() => {
        getMoviesSearch(searchParams.get("title"), searchParams.get("page"))
            .then((page) => {
                setMovies(page.content);
                let nextMovieNavPages = {
                    start: 1,
                    current: page.pageable.pageNumber + 1,
                    end: page.totalPages
                }
                setMovieNavPages(nextMovieNavPages);
            })
            .catch(setErrs);
    }, [searchParams]);


    return (
        <div id="movies">
            <MediaPageNav pages={movieNavPages} />
            <div className="media-container-lg">
                {
                    movies.map((m) => {
                        return <Movie key={m.movieId} movie={m} />;
                    })
                }
            </div>
            <MediaPageNav pages={movieNavPages} />
            <div className="">{errs.map((e) => { return <p key={e}>{e}</p> })}</div>
        </div>
    )
}

export default Movies;