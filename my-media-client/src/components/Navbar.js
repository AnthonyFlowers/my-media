import { useContext, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import AuthContext from './AuthContext';


function Navbar() {
    const defaultNav = [
        { name: 'Home', href: '/' },
        { name: 'Movies', href: '/movies' }
    ];
    const { user, logout } = useContext(AuthContext);
    const [ pageNav, setPageNav ] = useState(defaultNav);

    useEffect(() => {
        var nextPageNav = [...defaultNav].concat((user ? [
            { name: 'Logout', href: '/logout' }
        ] : [
            { name: 'Login', href: '/login' },
            { name: 'Create Account', href: '/register' }
        ]));
        setPageNav(nextPageNav);
    }, [user]);

    return (
        <nav>
            <div className='mx-auto'>
                <div className='flex flex-1 items-center justify-start'>
                    {
                        pageNav.map((item) => (
                            <Link to={item.href} key={item.name}>
                                <button >{item.name}</button>
                            </Link>
                        ))
                    }
                </div>
            </div>
        </nav>
    )

}

export default Navbar;