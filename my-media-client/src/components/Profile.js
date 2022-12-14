import { useContext, useEffect, useState } from "react";
import { getUserMovies } from "../services/movieService";
import AuthContext from "./AuthContext";
import { ListMovie } from "./Movie";

function Profile() {

    const { user } = useContext(AuthContext);
    const [userMovies, setUserMovies] = useState([]);
    const [errs, setErrs] = useState([])


    useEffect(() => {
        getUserMovies()
            .then((page) => {
                setUserMovies(page["content"]);
            })
            .catch(setErrs);
    }, []);

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
                <div className="gird-cols-1 col-span-2 p-3 rounded-lg border-4 border-gray-500">
                    <div
                        id="profileMovieList"
                        className="p-3 border rounded-md border-gray-400">
                        <h3 className="text-2xl">Your Movies</h3>
                        <ul>
                            {
                                userMovies.map((m, i) => {
                                    return <ListMovie key={m.appUserMovieId} m={m} setUserMovies={setUserMovies} />
                                })
                            }
                        </ul>
                    </div>
                    {/* <div className="p-3 border rounded-md border-gray-400">
                        <h3 className="text-2xl">Your Shows</h3>
                        <ul>
                            <li>-- todo</li>
                        </ul>
                    </div> */}
                </div>
                <div>{errs.map((e) => { return <p>{e}</p>; })}</div>
            </div>
        </>
    )
}

export default Profile;