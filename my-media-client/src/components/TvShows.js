import { useEffect, useState } from "react";
import { useSearchParams } from "react-router-dom";
import MediaPageNav from "./MediaPageNav";
import TvShow from "./TvShow";

function TvShows({ tvShowQuery }) {

    const [tvShows, setTvShows] = useState([]);
    const [tvShowNavPages, setTvShowNavPages] = useState({
        start: 1,
        current: 1,
        end: 1
    });
    const [tvShowPage, setTvShowPage] = useState(1);
    const [searchParams, setSearchParams] = useSearchParams();
    const [errs, setErrs] = useState([]);

    useEffect(() => {
        tvShowQuery(tvShowPage)
        .then((page) => {
            setTvShows(page.content);
            setTvShowPage(searchParams.get("page"));
            let tvShowNavPages = {
                start: 1,
                current: page.pageable.pageNumber + 1,
                end: page.totalPages
            }
            setTvShowNavPages(tvShowNavPages);
        })
        .catch(setErrs);
    }, [tvShowQuery, tvShowPage, searchParams])

    return (
        <div id="tvShows">
            <MediaPageNav pages={tvShowNavPages} setParams={setSearchParams} />
            <div className="grid md:grid-cols-1 lg:grid-cols-2 items-center justify-center py-4 lg:space-x-4 grid-auto-flow:row">
                {
                    tvShows.map((t) => {
                        return <TvShow key={t.tvShowId} tvShow={t} />
                    })
                }
            </div>
            <MediaPageNav pages={tvShowNavPages} setParams={setSearchParams} />
        </div>
    )
}

export default TvShows;