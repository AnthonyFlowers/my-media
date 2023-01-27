import { SERVER_URL } from "./API";
import { LOCAL_STORAGE_TOKEN_KEY } from "./authenticationService";

const tvShowApi = `${SERVER_URL}/api/tv-show`;
const userTvShowApi = `${SERVER_URL}/api/user/tv-show`;

export async function getTvShows(page) {
  page = page == null ? 1 : page;
  page = Math.max(1, page);
  const response = await fetch(`${tvShowApi}?page=${page}`);
  return tvShowQueryResponse(response, "error finding tv shows");
}

export async function getTvShowsSearch(title, page = 1) {
  if (title == null || title === "") {
    return getTvShows(page);
  }
  const response = await fetch(
    `${tvShowApi}/search?page=${page}&title=${title}`
  );
  return tvShowQueryResponse(response, "error searching for tv shows");
}

export async function getTvShowsLimit(count) {
  const response = await fetch(`${tvShowApi}?pageSize=${count}`);
  return tvShowQueryResponse(
    response,
    "error finding " + count + " tv shows :<"
  );
}

export async function getUserTvShows() {
  const response = await fetch(`${userTvShowApi}`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`,
    },
  });
  return tvShowQueryResponse(response, "error finding tv shows");
}

export async function getAllUserTvShows() {
  const response = await fetch(`${userTvShowApi}/all`, {
    headers: {
      Authorization: `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`,
    },
  });
  return tvShowQueryResponse(response, "error finding tv shows");
}

async function tvShowQueryResponse(response, errorMsg) {
  var errors = [];
  if (response.ok) {
    const body = await response.json();
    return body;
  } else if (response.status === 403) {
    errors.push("authentication error");
  } else if (response.status === 404) {
    errors.push("404 not found");
  }
  errors.push(errorMsg);
  return Promise.reject(errors);
}

export async function addTvShowToUser(tvShow) {
  const response = await fetch(`${userTvShowApi}`, {
    method: "POST",
    headers: {
      Authorization: `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(tvShow),
  });
  if (response.status === 201) {
    return Promise.resolve("created tv show entry");
  }
  return Promise.reject(["could not add tv show :("]);
}

export async function deleteUserTvShow(userTvShowId) {
  const response = await fetch(`${userTvShowApi}`, {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      appUserTvShowId: userTvShowId,
    }),
  });
  if (response.ok) {
    return Promise.resolve("deleted tv show entry");
  }
  return Promise.reject(["could not delete tv show :("]);
}

export async function updateUserTvShow(userTvShow) {
  const response = await fetch(`${userTvShowApi}`, {
    method: "PUT",
    headers: {
      Authorization: `Bearer ${localStorage.getItem(LOCAL_STORAGE_TOKEN_KEY)}`,
      "Content-Type": "application/json",
    },
    body: JSON.stringify(userTvShow),
  });
  if (response.ok) {
    return Promise.resolve(response);
  } else {
    return Promise.reject(["could not update user tv show"]);
  }
}
