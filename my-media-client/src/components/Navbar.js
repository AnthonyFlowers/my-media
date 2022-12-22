import { useContext, useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import AuthContext from './AuthContext';
import { isAdmin } from './AuthRouteAdmin';


function Navbar() {
    const homepage = { name: 'Home', href: '/', active: false };
    const defaultNav = [
        homepage,
        { name: 'Movies', href: '/movies', active: false },
        { name: 'TV Shows', href: '/tv-shows', active: false }
    ];
    const location = useLocation();
    const { user, logout } = useContext(AuthContext);
    const [pageNav, setPageNav] = useState(defaultNav);

    useEffect(() => {
        var nextPageNav = [...defaultNav];
        nextPageNav.forEach(n => {
            const path = location.pathname;
            if (path === homepage.href & n.name === homepage.name) {
                n.active = true;
            } else if (n.name !== homepage.name && path.startsWith(n.href)) {
                n.active = true;
            } else {
                n.active = false;
            }
        })
        setPageNav(nextPageNav);
    }, [user, location]);



    return (
        <nav className="bg-white rounded-lg p-5">
            <ul className="flex flex-row justify-start mx-5">
                <div className="flex flex-row">
                    {
                        pageNav.map((item) => (
                            <li className="" key={item.name}>
                                <Link className={`btn-nav ${item.active ? "btn-nav-active" : "btn-nav-selectable"}`}
                                    to={item.href}>
                                    {item.name}
                                </Link>
                            </li>
                        ))
                    }
                </div>
                <div className="flex flex-row flex-grow justify-end">
                    {
                        user && isAdmin(user) ? <>
                            <li>
                                <Link
                                    className={`btn-nav ${location.pathname === "/admin" ? "btn-nav-active" : "btn-nav-selectable"}`}
                                    to="/admin"
                                >
                                    Admin
                                </Link>
                            </li>
                        </> : <></>
                    }
                    {
                        (user ? <>
                            <li>
                                <Link
                                    className={`btn-nav ${location.pathname === "/profile" ? "btn-nav-active" : "btn-nav-selectable"}`}
                                    to="/profile"
                                >
                                    Profile
                                </Link>
                            </li>
                            <button className="btn-nav btn-nav-selectable bg-yellow-200 justify-self-end" onClick={logout}>Logout</button>
                        </> : <>
                            <li className="">
                                <Link
                                    id="loginButton"
                                    className={`btn-nav ${location.pathname === "/login" ? "btn-nav-active" : "btn-nav-selectable"}`}
                                    to="/login">
                                    Login
                                </Link>
                            </li>
                            <li>
                                <Link
                                    className={`btn-nav ${location.pathname === "/register" ? "btn-nav-active" : "btn-nav-selectable"}`}
                                    to="/register">
                                    Create Account
                                </Link>
                            </li>
                        </>
                        )
                    }
                </div>
            </ul>
        </nav>
    )

}

export default Navbar;