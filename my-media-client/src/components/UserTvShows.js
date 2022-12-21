import { useContext, useEffect, useState } from "react";
import { getAllUserTvShows } from "../services/tvShowService";
import AuthContext from "./AuthContext";
import UserTvShow from "./UserTvShow";

function UserTvShows( ) {

    const { user } = useContext(AuthContext);
    const [userTvShows, setUserTvShows] = useState([]);
    const [errs, setErrs] = useState([]);

    useEffect(() => {
        getAllUserTvShows(user)
            .then(setUserTvShows)
            .catch(setErrs);
    }, [user])

    return (
        <div id="tvShows">
            <div className="media-container-lg">
                {
                    userTvShows.map((t) => {
                        return <UserTvShow key={t.appUserTvShowId} uts={t} />
                    })
                }
            </div>
        </div>
    )
}

export default UserTvShows;