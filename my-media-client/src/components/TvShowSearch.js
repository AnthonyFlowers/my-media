import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import { getTvShowsSearch } from "../services/tvShowService";
import MediaPageNav from "./MediaPageNav";
import TvShow from "./TvShow";

function TvShowSearch() {
  const [searchParams, setSearchParams] = useSearchParams();
  const [tvShows, setTvShows] = useState([]);
  const [tvShowNavPages, setTvShowNavPages] = useState({
    start: 1,
    current: 1,
    end: 1,
  });
  const [tvShowPage, setTvShowPage] = useState(1);
  const [errs, setErrs] = useState([]);

  useEffect(() => {
    getTvShowsSearch(searchParams.get("title"), searchParams.get("page"))
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
  }, [tvShowPage, searchParams]);

  return (
    <div id="tvShows">
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

export default TvShowSearch;
