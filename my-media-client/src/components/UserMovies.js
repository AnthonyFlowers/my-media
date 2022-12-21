import { useContext, useEffect, useState } from "react";
import { deleteUserMovieById, getAllUserMovies } from "../services/movieService";
import AuthContext from "./AuthContext";
import UserMovie from "./UserMovie";

function UserMovies() {

    const { user } = useContext(AuthContext);
    const [userMovies, setUserMovies] = useState([]);
    const [errs, setErrs] = useState([]);

    useEffect(() => {
        getAllUserMovies(user)
            .then(setUserMovies)
            .catch(setErrs);
    }, [user]);

    function handleDelete(evt) {
        const userMovieId = evt.target.value;
        deleteUserMovieById(userMovieId)
            .then(() => {
                getAllUserMovies(user)
                    .then(setUserMovies);
            })
            .catch(setErrs)
    }

    return (
        <div id="movies">
            <div className="media-container-lg">
                {
                    userMovies.map((m) => {
                        return <UserMovie key={m.appUserMovieId} um={m} handleDelete={handleDelete} />;
                    })
                }
            </div>
            <div className="">{errs.map((e) => { return <p key={e}>{e}</p> })}</div>
        </div>
    );
}

export default UserMovies;