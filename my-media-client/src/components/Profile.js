import { useContext, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getUserMovies } from "../services/movieService";
import { getUserTvShows } from "../services/tvShowService";
import AuthContext from "./AuthContext";

function Profile() {

    const { user } = useContext(AuthContext);
    const [userMovies, setUserMovies] = useState([]);
    const [userMovieStats, setUserMovieStats] = useState({});
    const [userTvShows, setUserTvShows] = useState([]);
    const [userTvShowStats, setUserTvShowStats] = useState({});
    const [errs, setErrs] = useState([])


    useEffect(() => {
        getUserMovies()
            .then((page) => {
                setUserMovies(page["content"]);
            })
            .catch(setErrs);
        getUserTvShows()
            .then((page) => {
                setUserTvShows(page["content"]);
            })
            .catch(setErrs);
    }, []);

    useEffect(() => {
        const nextUserMovieStats = {
            count: 0,
            watched: 0,
            rewatchCount: 0,
            time: 0,
            mostWatched: userMovies[0]
        };
        nextUserMovieStats.count = userMovies.length;
        for (const um of userMovies) {
            if (um.watchCount >= nextUserMovieStats.mostWatched.watchCount) {
                nextUserMovieStats.mostWatched = um;
            }
            if (um.watchCount > 0) { nextUserMovieStats.watched += 1 }
            nextUserMovieStats.rewatchCount += um.watchCount;
            nextUserMovieStats.time += um.watchCount * um.movie.movieLength;
        }
        setUserMovieStats(nextUserMovieStats);
    }, [userMovies]);

    useEffect(() => {
        const nextUserTvShowStats = {
            count: 0,
            watched: 0,
            totalSeasons: 0,
            watchCount: 0,
            mostWatched: userTvShows[0]
        };
        nextUserTvShowStats.count = userTvShows.length;
        for (const uts of userTvShows) {
            if (uts.watchCount >= nextUserTvShowStats.mostWatched.watchCount) {
                nextUserTvShowStats.mostWatched = uts;
            }
            if (uts.watchCount > 0) { nextUserTvShowStats.watched += 1 }
            nextUserTvShowStats.totalSeasons += uts.season;
            nextUserTvShowStats.watchCount += uts.watchCount;

        }
        setUserTvShowStats(nextUserTvShowStats);
    }, [userTvShows])

    return (
        <>
            <div className="grid grid-cols-3 gap-2">
                <div className="col-span-1 border-4 border-gray-500 bg-gray-200 rounded-lg">
                    <div className="m-2 p-3 border-2 border-gray-400 bg-gray-50">
                        <h2 id="username" className="text-3xl">{user ? user.sub : ''}</h2>
                        <p id="status">Status: <b>{user ? user.authorities : ''}</b></p>
                    </div>
                    <div className="m-2 p-1 border-2 border-gray-400 bg-gray-50">
                        <h3 className="text-2xl">recents</h3>
                        <ul>
                            <li>movies...</li>
                        </ul>
                        <ul>
                            <li>tv shows...</li>
                        </ul>
                        <ul>
                            <li>etc...</li>
                        </ul>
                    </div>
                </div>
                <div className="grid-cols-1 col-span-2 p-3 rounded-lg border-4 border-gray-500 space-y-3">
                    <div
                        id="profileMovieStats"
                        className="p-3 border rounded-md border-gray-400">
                        <h3 className="text-2xl"><Link to="/movies/user">Movie Stats</Link></h3>
                        <p>Movie Count: {userMovieStats.count}</p>
                        <p>Completed: {userMovieStats.watched}</p>
                        <p>Rewatched Count: {userMovieStats.rewatchCount}</p>
                        <p>Time Watching: {userMovieStats.time} Hours</p>
                        <p>Most Watched: {userMovieStats.mostWatched ? userMovieStats.mostWatched.movie.movieName : "none"}</p>
                    </div>
                    <div
                        id="profileTvShowStats"
                        className="p-3 border rounded-md border-gray-400">
                        <h3 className="text-2xl"><Link to="/tv-shows/user">Show Stats</Link></h3>
                        <p>Show Count: {userTvShowStats.count}</p>
                        <p>Completed: {userTvShowStats.watchCount}</p>
                        <p>Seasons Watched: {userTvShowStats.totalSeasons}</p>
                        <p>Most Watched: {userTvShowStats.mostWatched ? userTvShowStats.mostWatched.tvShow.tvShowName : ""}</p>
                    </div>
                </div>
                <div>{errs.map((e) => { return <p>{e}</p>; })}</div>
            </div>
        </>
    )
}

export default Profile;