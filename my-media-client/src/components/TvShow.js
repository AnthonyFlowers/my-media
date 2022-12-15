import { useContext, useState } from "react";
import { addTvShowToUser, getUserTvShows } from "../services/tvShowService";
import AuthContext from "./AuthContext";

export default function TvShow({ tvShow }) {
    const { user } = useContext(AuthContext);

    function handleAdd() {
        addTvShowToUser(tvShow)
            .then((msg) => {
                // show success message
                console.log(msg);
            })
            .catch(console.log);
    }

    return (
        <div className="bg-gray-100 p-10 my-2 rounded-lg shadow-md">
            <h1 className="text-xl font-bold">{tvShow.tvShowName}</h1>
            <div className="mt-2 mb-2">
                <p className="text-gray-600">Year: {tvShow.releaseYear}</p>
                <p>Overview: {tvShow.overview}</p>
                {/* check if TvShow already added */}
                {
                    user ? <button onClick={handleAdd}>Add</button> : <></>
                }
            </div>
        </div>
    )
}

export function ListTvShow({ t, setUserTvShows, setErr }) {
    const [userTvShow, setUserTvShow] = useState(t);

    function toggleWatched(evt) {
        const nextUserTvShow = { ...userTvShow };
        nextUserTvShow.watched = evt.target.checked;
        setUserTvShow(nextUserTvShow);
        // updateUserTvShow(nextUserTvShow);
    }

    function handleDelete(evt) {
        console.log(evt.target.value)
        // deleteUserTvShow(evt.target.value)
        //     .then(() => {
        //         getUserTvShows()
        //             .then((page) => {
        //                 setUserTvShows(page["content"])
        //             })
        //             .catch(setErr);
        //     })
        //     .catch(setErr);
    }
    return (
        <li>
            <input type="checkbox"
                checked={userTvShow.watched}
                onChange={toggleWatched}
            />
            {userTvShow.tvShow.tvShowName}, Year: {userTvShow.tvShow.releaseYear}, Season: {userTvShow.season}, Episode: {userTvShow.episode}
            <button type="button"
                className="btn-small btn-red"
                value={userTvShow.appUserTvShowId}
                onClick={handleDelete}>
                Remove
            </button>
        </li>
    )
}