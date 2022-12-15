import { SERVER_URL } from "./API";

const tvShowApi = `${SERVER_URL}/api/tv-show`;
// const userTvShowApi = `${SERVER_URL}/api/user/tv-show`;

export async function getTvShows(page) {
    page = page == null ? 1 : page;
    page = Math.max(0, page - 1);
    const response = await fetch(`${tvShowApi}?page=${page}`);
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