import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import Movie from "./Movie";
import MediaPageNav from "./MediaPageNav";

function Movies({ movieQueury }) {

    const [urlParams, setUrlParams] = useSearchParams();
    const navigate = useNavigate();
    const [movies, setMovies] = useState([]);
    const [movieNavPages, setMovieNavPages] = useState({
        start: 1,
        current: 1,
        end: 1
    });
    const [moviePage, setMoviePage] = useState(1);
    const [search, setSearch] = useState("");
    const [errs, setErrs] = useState([]);

    useEffect(() => {
        movieQueury(moviePage)
            .then((page) => {
                setMovies(page.content);
                setMoviePage(urlParams.get("page"));
                let nextMovieNavPages = {
                    start: 1,
                    current: page.pageable.pageNumber + 1,
                    end: page.totalPages
                }
                setMovieNavPages(nextMovieNavPages);
            })
            .catch(setErrs);
    }, [movieQueury, moviePage, urlParams]);

    function handleChange(evt) {
        setSearch(evt.target.value);
    }

    function handleSearchSubmit(evt) {
        evt.preventDefault();
        navigate(`/movies/search?page=1&name=${search}`);
    }
    return (
        <div id="movies">
            <div id="movieSearch">
                <form onSubmit={handleSearchSubmit}>
                    <input onChange={handleChange} value={search} placeholder="search" />
                    <button className="btn" type="submit">Submit</button>
                </form>
                <div className="">{errs.map((e) => { return <p key={e}>{e}</p> })}</div>
            </div>
            <MediaPageNav pages={movieNavPages} />
            <div className="media-container-lg">
                {
                    movies.map((m) => {
                        return <Movie key={m.movieId} movie={m} />;
                    })
                }
            </div>
            <MediaPageNav pages={movieNavPages} setParams={setUrlParams} />
            <div className="">{errs.map((e) => { return <p key={e}>{e}</p> })}</div>
        </div>
    );
}

export default Movies;