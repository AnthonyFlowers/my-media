import { useContext, useEffect, useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import Movie from "./Movie";
import MediaPageNav from "./MediaPageNav";
import { getMovies } from "../services/movieService";
import AuthContext from "./AuthContext";

function Movies() {
  const { user } = useContext(AuthContext);
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();
  const [movies, setMovies] = useState([]);
  const [movieNavPages, setMovieNavPages] = useState({
    start: 1,
    current: 1,
    end: 1,
  });
  const [moviePage, setMoviePage] = useState(1);
  const [search, setSearch] = useState("");
  const [errs, setErrs] = useState([]);

  useEffect(() => {
    getMovies(moviePage)
      .then((page) => {
        setMovies(page.content);
        setMoviePage(searchParams.get("page"));
        let nextMovieNavPages = {
          start: 1,
          current: page.pageable.pageNumber + 1,
          end: page.totalPages,
        };
        setMovieNavPages(nextMovieNavPages);
      })
      .catch(setErrs);
  }, [moviePage, searchParams]);

  function handleChange(evt) {
    setSearch(evt.target.value);
  }

  function handleSearchSubmit(evt) {
    evt.preventDefault();
    navigate(`/movies/search?page=1&title=${search}`);
  }

  return (
    <div id="movies">
      <div id="movieSearch">
        <form
          onSubmit={handleSearchSubmit}
          className="flex justify-center items-center gap-4"
        >
          <input
            onChange={handleChange}
            value={search}
            placeholder="search"
            className="p-2 w-1/2 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 sm:text-md focus:ring-blue-500 focus:border-blue-500"
          />
          <button className="btn bg-gray-400 hover:bg-gray-200" type="submit">
            Search
          </button>
          {user ? (
            <Link
              to="/movies/user"
              className="btn bg-gray-400 hover:bg-gray-200"
            >
              My Movies
            </Link>
          ) : (
            <></>
          )}
        </form>
        {/* <div className="">{errs.map((e) => { return <p key={e}>{e}</p> })}</div> */}
      </div>
      <MediaPageNav pages={movieNavPages} />
      <div className="">
        {errs && errs.length > 0
          ? errs.map((e) => {
              return <p key={e}>{e}</p>;
            })
          : ""}
      </div>

      <div className="media-container-lg">
        {movies.map((m) => {
          return <Movie key={m.movieId} movie={m} />;
        })}
      </div>
    </div>
  );
}

export default Movies;
