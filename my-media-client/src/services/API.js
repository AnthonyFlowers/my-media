export const SERVER_URL =
  process.env.NODE_ENV === "development"
    ? "http://127.0.0.1:8080"
    : "https://my-media.anthonyf.net:8443/my-media";

export const BASENAME =
  process.env.NODE_ENV === "development" ? "/" : "/my-media";
