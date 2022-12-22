import debounce from "lodash.debounce";
import { useCallback, useState } from "react";
import { updateUserTvShow } from "../services/tvShowService";

export default function UserTvShow({ uts, handleDelete }) {
    const [userTvShow, setUserTvShow] = useState(uts);
    const [errs, setErrs] = useState([]);

    function deleteShow(evt) {
        handleDelete(evt.target.value);
    }

    const debounceHandleUpdateShow = useCallback(debounce((nextShow) => {
        updateUserTvShow(nextShow)
            .catch(setErrs)
    }, 1000), []);

    function handleChange(evt) {
        const value = evt.target.value;
        const attribute = evt.target.name;
        const nextTvShow = { ...userTvShow };
        nextTvShow[attribute] = nextTvShow[attribute] + parseInt(value);
        debounceHandleUpdateShow(nextTvShow);
        setUserTvShow(nextTvShow);
    }

    function unwatch() {
        if (userTvShow.season === 0 && userTvShow.episode === 0) {
            return;
        }
        const nextTvShow = { ...userTvShow };
        nextTvShow.season = 0;
        nextTvShow.episode = 0;
        debounceHandleUpdateShow(nextTvShow);
        setUserTvShow(nextTvShow);
    }

    return (
        <div className="media-card-lg group">
            <h1 className="text-xl font-bold">{uts.tvShow.tvShowName}</h1>
            <div className="details">
                <p className="attribute">
                    Season: <button value="-1" name="season" onClick={handleChange}>&lt;</button> {userTvShow.season} <button value="1" name="season" onClick={handleChange}>&gt;</button>
                </p>
                <p className="attribute">
                    Episode: <button value="-1" name="episode" onClick={handleChange}>&lt;</button> {userTvShow.episode} <button value="1" name="episode" onClick={handleChange}>&gt;</button>
                </p>
                <p className="attribute">Year: {userTvShow.tvShow.releaseYear}</p>
                <p className="overview group-hover:h-auto">Overview: {userTvShow.tvShow.overview}</p>
                <div className="space-x-3">
                    <button value={userTvShow.appUserTvShowId} onClick={deleteShow} className="btn btn-red">Delete</button>
                    <button value={userTvShow.appUserTvShowId} onClick={unwatch} className="btn btn-yellow">Unwatch</button>
                </div>
            </div>
            {errs.length > 0 ? <div>
                {errs.map((e) => {
                    return <p key={e}>{e}</p>
                })}
            </div> : <></>}
        </div>
    )
}
