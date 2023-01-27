import { useContext, useEffect, useState } from "react";
import { Link, useNavigate, useSearchParams } from "react-router-dom";
import AuthContext from "./AuthContext";
import MediaPageNav from "./MediaPageNav";
import TvShow from "./TvShow";

function TvShows({ tvShowQuery }) {
  const { user } = useContext(AuthContext);
  const [searchParams, setSearchParams] = useSearchParams();
  const navigate = useNavigate();
  const [tvShows, setTvShows] = useState([]);
  const [tvShowNavPages, setTvShowNavPages] = useState({
    start: 1,
    current: 1,
    end: 1,
  });
  const [tvShowPage, setTvShowPage] = useState(1);
  const [search, setSearch] = useState("");
  const [errs, setErrs] = useState([]);

  useEffect(() => {
    tvShowQuery(tvShowPage)
      .then((page) => {
        setTvShows(page.content);
        setTvShowPage(searchParams.get("page"));
        let tvShowNavPages = {
          start: 1,
          current: page.pageable.pageNumber + 1,
          end: page.totalPages,
        };
        setTvShowNavPages(tvShowNavPages);
      })
      .catch(setErrs);
  }, [tvShowQuery, tvShowPage, searchParams]);

  function handleChange(evt) {
    setSearch(evt.target.value);
  }

  function handleSearchSubmit(evt) {
    evt.preventDefault();
    navigate(`/tv-shows/search?page=1&title=${search}`);
  }

  return (
    <div id="tvShows">
      <div id="tvShowSearch">
        <form
          onSubmit={handleSearchSubmit}
          className="flex justify-center gap-4"
        >
          <input
            onChange={handleChange}
            value={search}
            placeholder="search"
            className="p-2 w-1/2 text-gray-900 border border-gray-300 rounded-lg bg-gray-50 sm:text-md focus:ring-blue-500 focus:border-blue-500"
          />
          <button className="btn bg-gray-400" type="submit">
            Search
          </button>
          {user ? (
            <Link className="btn bg-gray-400" to="/tv-shows/user">
              My Shows
            </Link>
          ) : (
            <></>
          )}
        </form>
        <div className="">
          {errs.map((e) => {
            return <p key={e}>{e}</p>;
          })}
        </div>
      </div>
      <MediaPageNav pages={tvShowNavPages} setParams={setSearchParams} />
      <div className="media-container-lg">
        {tvShows.map((t) => {
          return <TvShow key={t.tvShowId} tvShow={t} />;
        })}
      </div>
      <MediaPageNav pages={tvShowNavPages} setParams={setSearchParams} />
    </div>
  );
}

export default TvShows;
