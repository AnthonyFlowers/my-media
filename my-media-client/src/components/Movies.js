import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import Movie from "./Movie";
import MediaPageNav from "./MediaPageNav";

function Movies({ movieQueury }) {

    const [movies, setMovies] = useState([]);
    const [movieNavPages, setMovieNavPages] = useState({
        start: 1,
        current: 1,
        end: 1
    });
    const [moviePage, setMoviePage] = useState(1);
    const [searchParams, setSearchParams] = useSearchParams();
    const [errs, setErrs] = useState([]);

    useEffect(() => {
        movieQueury(moviePage)
            .then((page) => {
                setMovies(page.content);
                setMoviePage(searchParams.get("page"));
                let nextMovieNavPages = {
                    start: 1,
                    current: page.pageable.pageNumber + 1,
                    end: page.totalPages
                }
                setMovieNavPages(nextMovieNavPages);
            })
            .catch(setErrs);
    }, [movieQueury, moviePage, searchParams]);

    return (
        <div id="movies">
            <MediaPageNav pages={movieNavPages} setParams={setSearchParams} />
            <div className="grid md:grid-cols-1 lg:grid-cols-2 items-center justify-center py-4 lg:space-x-4 grid-auto-flow:row">
                {
                    movies.map((m) => {
                        return <Movie key={m.movieId} movie={m} />;
                    })
                }
            </div>
            <MediaPageNav pages={movieNavPages} setParams={setSearchParams} />
            <div className="">{errs.map((e) => { return <p key={e}>{e}</p> })}</div>
        </div>
    )
}

export default Movies;