import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getMoviesLimit } from "../services/movieService";
import { getTvShowsLimit } from "../services/tvShowService";
import { SmallMovie } from "./Movie";
import { SmallTvShow } from "./TvShow";

const DEFAULT_COUNT = 6;

function Home() {
  const [movies, setMovies] = useState([]);
  const [tvShows, setTvShows] = useState([]);
  const [errs, setErrs] = useState([]);

  useEffect(() => {
    let newErrors = [];
    getMoviesLimit(DEFAULT_COUNT)
      .then((page) => {
        setMovies(page.content);
      })
      .catch((e) => {
        newErrors += e;
      });
    getTvShowsLimit(DEFAULT_COUNT)
      .then((page) => {
        setTvShows(page.content);
      })
      .catch((e) => {
        newErrors += e;
      });
    setErrs(newErrors);
  }, []);

  return (
    <>
      <h1 className="text-xl">Welcome to My Media!</h1>
      <h3 className="text-lg">
        <Link to="/movies">Explore Movies</Link>
      </h3>
      <div id="smallMovies" className="media-container-md">
        {movies.map((m) => {
          return <SmallMovie key={m.movieId} movie={m} />;
        })}
      </div>
      <h3 className="text-lg">
        <Link to="/tv-shows">Explore Tv Shows</Link>
      </h3>
      <div id="smallTvShows" className="media-container-md">
        {tvShows.map((t) => {
          return <SmallTvShow key={t.tvShowId} tvShow={t} />;
        })}
      </div>
    </>
  );
}

export default Home;
