import { SERVER_URL } from "./API"
import { LOCAL_STORAGE_TOKEN_KEY } from "./authenticationService"

const movieApi = `${SERVER_URL}/api/movie`;
const userMovieApi = `${SERVER_URL}/api/user/movie`;

export async function getMovies(page) {
    if (page == null) {
        page = 1;
    }
    page = Math.max(0, page - 1)
    const response = await fetch(`${movieApi}?page=${page}`);
    return movieQueryResponse(response, "error finding movies");
}

export async function getUserMovies(page = 1, pageSize = 10) {
    page = Math.max(0, page - 1);
    pageSize = Math.max(1, pageSize);
    const response = await fetch(`${userMovieApi}?page=${page}&pageSize=${pageSize}`, {
        headers: {
            "Authorization": `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`
        }
    });
    return userMovieQueryResponse(response, "error finding movies for the user");
}

export async function getRecentMovies(page) {
    page = Math.max(0, page - 1);
    const response = await fetch(`${movieApi}/recent?page=${page}`);
    return movieQueryResponse(response, "error finding recent movies");
}

export async function getMoviesPageMovieCount(page, movieCount) {
    page = Math.max(0, page - 1);
    const response = await fetch(`${movieApi}?page=${page}&pageSize=${movieCount}`)
    return movieQueryResponse(response, "error finding movies by page and movie count")
}

export async function addMovieToUser(movie) {
    const response = await fetch(`${userMovieApi}`, {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(movie)
    });
    if (response.status === 201) {
        return Promise.resolve("created movie entry");
    }
    return Promise.reject(["could not add movie :("]);
}


export async function updateUserMovie(userMovie) {
    const response = await fetch(`${userMovieApi}`, {
        method: "PUT",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`,
            "Content-Type": "application/json"
        },
        body: JSON.stringify(userMovie)
    });
    if(response.ok) {
        return Promise.resolve();
    } else {
        return Promise.reject(["could not update user movie"]);
    }
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

async function userMovieQueryResponse(response, errorMsg) {
    var errors = [];
    if (response.ok) {
        const body = await response.json();
        const movies = [];
        console.log(body);
        return body;
    } else if (response.status === 403) {
        errors.push("authentication error")
    } else if (response.status === 404) {
        errors.push("404 not found")
    }
    errors.push(errorMsg);
    return Promise.reject(errors);
}

