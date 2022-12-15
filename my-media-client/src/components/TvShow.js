import { useContext } from "react";
import AuthContext from "./AuthContext";

function TvShow({ tvShow }) {
    const { user } = useContext(AuthContext);

    function handleAdd() {
        // impl
        alert("to impl");
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

export default TvShow;