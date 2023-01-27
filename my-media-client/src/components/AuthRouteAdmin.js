import { Navigate } from "react-router-dom";

// https://www.robinwieruch.de/react-router-private-routes/
export default function AuthRouteAdmin({
  user,
  redirectRoute = "/",
  children,
}) {
  if (!isAdmin(user)) {
    return <Navigate to={redirectRoute} replace={true} />;
  }
  return children;
}

export function isAdmin(u) {
  return u && u["authorities"].match(/admin/i);
}
