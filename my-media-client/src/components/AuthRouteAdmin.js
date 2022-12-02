import { useContext } from "react";
import { Navigate } from "react-router-dom";
import AuthContext from "./AuthContext";

// https://www.robinwieruch.de/react-router-private-routes/
function AuthRouteAdmin({ user, redirectRoute = "/", children }) {

    console.log(user);
    if (!isAdmin(user)) {
        return <Navigate to={redirectRoute} replace={true} />;
    }
    return children;
}

export function isAdmin(u) {
    return u && u["authorities"].match(/admin/i);
}

export default AuthRouteAdmin;