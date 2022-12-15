import { SERVER_URL } from "./API";
import { LOCAL_STORAGE_TOKEN_KEY } from "./authenticationService";

const tvShowApi = `${SERVER_URL}/api/tv-show`;
const userTvShowApi = `${SERVER_URL}/api/user/tv-show`;

export async function getTvShows(page) {
    page = page == null ? 1 : page;
    page = Math.max(0, page - 1);
    const response = await fetch(`${tvShowApi}?page=${page}`);
    return tvShowQueryResponse(response, "error finding movies")
}

export async function getUserTvShows() {
    const response = await fetch(`${userTvShowApi}`, {
        headers: {
            "Authorization": `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`
        }
    });
    return tvShowQueryResponse(response, "error finding movies") 
}


async function tvShowQueryResponse(response, errorMsg) {
    var errors = [];
    if (response.ok) {
        const body = await response.json();
        return body;
    } else if (response.status === 403) {
        errors.push("authentication error")
    } else if (response.status === 404) {
        errors.push("404 not found")
    }
    errors.push(errorMsg);
    return Promise.reject(errors);
}

export async function addTvShowToUser(tvShow) {
    const response = await fetch(`${userTvShowApi}`, {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(tvShow)
    });
    if (response.status === 201) {
        return Promise.resolve("created movie entry");
    }
    return Promise.reject(["could not add movie :("]);
}