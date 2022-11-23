import { SERVER_URL } from "./API"
import { LOCAL_STORAGE_TOKEN_KEY } from "./authenticationService"

const movieApi = `${SERVER_URL}/api/movie`;

export async function getMovies() {
    const response = await fetch(movieApi);
    return movieQueryResponse(response, "error finding movies");
}

export async function getUserMovies() {
    const response = await fetch(`${movieApi}/user`, {
        headers: {
            "Authorization": `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`
        }
    });
    return movieQueryResponse(response, "error finding movies for the user");
}

export async function getRecentMovies(page) {
    page = Math.max(0, page - 1);
    const response = await fetch(`${movieApi}/recent/${page}`);
    return movieQueryResponse(response, "error finding recent movies");
}

async function movieQueryResponse(response, errorMsg) {
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