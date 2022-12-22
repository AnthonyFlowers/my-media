import debounce from "lodash.debounce";
import { useCallback, useState } from "react"
import { updateUserMovie } from "../services/movieService";

export default function UserMovie({ um, handleDelete }) {
    const [userMovie, setUserMovie] = useState(um);
    const [errs, setErrs] = useState([]);

    function handleWatchCount(evt) {
        const nextMovie = { ...userMovie };
        const nextWatchCount = nextMovie.watchCount + parseInt(evt.target.value);
        if (nextWatchCount <= 0 && nextMovie.watched) {
            nextMovie.nextWatchCount = 0;
            nextMovie.watched = false;
        } else if (nextWatchCount > 0 && !nextMovie.watched) {
            nextMovie.watched = true;
        }
        nextMovie.watchCount = nextWatchCount;
        updateUserMovie(nextMovie)
            .then(() => {
                setUserMovie(nextMovie);
            })
            .catch(setErrs);
    }

    function deleteMovie(evt) {
        handleDelete(evt.target.value);
    }

    function unwatch() {
        const nextMovie = { ...userMovie };
        if (!nextMovie.watchCount && nextMovie.watchCount === 0) {
            return;
        }
        nextMovie.watched = false;
        nextMovie.watchCount = 0;
        updateUserMovie(nextMovie)
            .then(() => {
                setUserMovie(nextMovie);
            })
            .catch(setErrs);
    }

    return (
        <div className="media-card-lg group">
            <h1 className="text-xl font-bold">{userMovie.movie.movieName}</h1>
            <div className="details">
                <p className="attribute">
                    Watched: {userMovie.watched ? "Yes" : "No"}
                    <button onClick={unwatch} className="btn btn-small btn-red text-xs" hidden={!userMovie.watched}>Unwatch</button>
                </p>
                <p className="attribute">Watch Count: <button className="font-bold" value="-1" onClick={handleWatchCount}>&lt;</button> {userMovie.watchCount} <button className="font-bold" value="1" onClick={handleWatchCount}>&gt;</button></p>
                <p className="attribute">Length: {userMovie.movie.movieLength} Minutes</p>
                <p className="attribute">Year: {userMovie.movie.movieYear}</p>
                <p className="overview group-hover:h-auto">Overview: {userMovie.movie.movieOverview}</p>
                <button value={userMovie.appUserMovieId} onClick={deleteMovie} className="btn btn-red">Delete</button>
            </div>
            {errs && errs.length > 0 ? errs.map((e) => { return <p>(e)</p> }) : <></>}
        </div>
    )
}