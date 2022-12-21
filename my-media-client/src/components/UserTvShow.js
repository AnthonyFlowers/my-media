import { useContext, useState } from "react";
import { addTvShowToUser, deleteUserTvShow, getUserTvShows, updateUserTvShow } from "../services/tvShowService";
import AuthContext from "./AuthContext";

export default function UserTvShow({ uts }) {


    return (
        <div className="media-card-lg group">
            <h1 className="text-xl font-bold">{uts.tvShow.tvShowName}</h1>
            <div className="details">
                <p className="attribute">Season: {uts.season}</p>
                <p className="attribute">Episode: {uts.episode}</p>
                <p className="attribute">Year: {uts.tvShow.releaseYear}</p>
                <p className="overview group-hover:h-auto">Overview: {uts.tvShow.overview}</p>
            </div>
        </div>
    )
}
