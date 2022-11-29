import { useContext, useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import AuthContext from './AuthContext';


function Navbar() {
    const defaultNav = [
        { name: 'Home', href: '/', active: false },
        { name: 'Movies', href: '/movies', active: false },
        { name: 'Tv Shows', href: '/tv-shows', active: false }
    ];
    const { user, logout } = useContext(AuthContext);
    const [pageNav, setPageNav] = useState(defaultNav);
    const location = useLocation();

    useEffect(() => {
        var nextPageNav = [...defaultNav];
        nextPageNav.forEach(n => {
            if (location.pathname === (n.href)) {
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
                        </> :
                            <li className="">
                                <Link
                                id="loginButton"
                                    className={`btn-nav ${location.pathname === "/login" ? "btn-nav-active" : "btn-nav-selectable"}`}
                                    to="/login">
                                    Login
                                </Link>
                            </li>)
                    }
                </div>
            </ul>
        </nav>
    )

}

export default Navbar;