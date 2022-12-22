import { Navigate } from "react-router-dom";

// https://www.robinwieruch.de/react-router-private-routes/
function AuthRouteUser({ user, redirectRoute = '/', children }) {

    if (!user) {
        return <Navigate to={redirectRoute} replace={true} />;
    }
    return children;
}

export default AuthRouteUser;