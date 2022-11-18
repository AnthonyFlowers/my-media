import { SERVER_URL } from "./API"
import { LOCAL_STORAGE_TOKEN_KEY } from "./authenticationService"

const movieApi = `${SERVER_URL}/api/movie`

export async function getMovies() {
    const response = await fetch(movieApi);
    if(response.ok) {
        const body = await response.json();
        return body;
    } else {
        return Promise.reject(["error finding movies"]);
    }
}

export async function getUserMovies() {
    const response = fetch(movieApi, {
        "Authorization": `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`
    });
    if(response.ok) {
        const body = await response.json();
        return body;
    } else {
        return Promise.reject(["error finding movies for the user"]);
    }
}