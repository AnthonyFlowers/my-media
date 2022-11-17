export const LOCAL_STORAGE_TOKEN_KEY = "mymedia-jwt-token";
const AUTH_URL = "http://localhost:8080";

function makeUser(body) {
    const sections = body.jwt_token.split(".");
    const json = atob(sections[1]);
    const user = JSON.parse(json);
    localStorage.setItem(LOCAL_STORAGE_TOKEN_KEY, body.jwt_token);
    return user;
}

export async function authenticate(credentials) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(credentials)
    }

    const response = await fetch(`${AUTH_URL}/authenticate`, init);
    if (response.ok) {
        const body = await response.json();
        return makeUser(body);
    } else if (response.status === 403){
        return Promise.reject(["Could not login. Eamil/Password combination incorrect."]);
    }
}

export async function register(credentials) {
    const init = {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(credentials)
    };

    const response = await fetch(`${AUTH_URL}/register`, init);
    if(response.ok) {
        const body = await response.json();
        return makeUser(body);
    } else if (response.status === 400){
        const body = await response.json();
        return Promise.reject([body]);
    } else if (response.status === 409) {
        return Promise.reject(["User with that email already exists."]);
    }
    return Promise.reject(["Error registering user: " + response.status]);
}

export async function refresh(){
    if(!localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)){
        return Promise.resolve(["no key in storage"]);
    }
    const init = {
        method: "POST",
        headers: {
            "Authorization": `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`
        }
    }
    const response = await fetch(`${AUTH_URL}/refresh`, init);
    if(response.ok) {
        const body = await response.json();
        return makeUser(body);
    } else if (response.status === 403) {
        return Promise.reject(["authentication error no refresh. token may have expired"]);
    }
    return Promise.reject(["there was a problem refreshing your token"]);
}