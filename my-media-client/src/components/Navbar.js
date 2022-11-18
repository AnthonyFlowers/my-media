import { useContext, useEffect, useState } from 'react';
import { Link, useLocation } from 'react-router-dom';
import AuthContext from './AuthContext';


function Navbar() {
    const defaultNav = [
        { name: 'Home', href: '/', active: false },
        { name: 'Movies', href: '/movies', active: false }
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
            <ul className="flex mx-5">
                {
                    pageNav.map((item) => (
                        <li className="mr-3" key={item.name}>
                            <Link className={`btn-nav ${item.active ? "btn-nav-active" : "btn-nav-selectable"}`}
                                to={item.href}>
                                {item.name}
                            </Link>
                        </li>
                    ))
                }
                {
                    (user ? <button className="btn-nav btn-nav-selectable bg-yellow-200" onClick={logout}>Logout</button> :
                        <li className="mr-3">
                            <Link
                                className={`btn-nav ${location.pathname === "/login" ? "btn-nav-active" : "btn-nav-selectable"}`}
                                to="/login">
                                Login
                            </Link>
                        </li>)
                }
            </ul>
        </nav>
    )

}

export default Navbar;