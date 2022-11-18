import { useEffect, useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import AuthContext from './components/AuthContext';
import Home from './components/Home';
import Login, { Logout } from './components/Login';
import Movies from './components/Movies';
import Navbar from './components/Navbar';
import { LOCAL_STORAGE_TOKEN_KEY, refresh } from './services/authenticationService';


function App() {

  const [user, setUser] = useState();
  const [refreshed, setRefreshed] = useState(false);
  const login = setUser;

  const logout = () => {
    setUser(null);
    localStorage.removeItem(LOCAL_STORAGE_TOKEN_KEY);
  }

  const auth = {
    user,
    login,
    logout
  }

  useEffect(() => {
    if (!refreshed) {
      refresh()
        .then(login)
        .catch((ex) => {
          logout();
          console.log(ex);
          setRefreshed(true);
        });
    }
  }, [refreshed]);

  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <div className="my-5 mx-10">
          <Navbar />
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/movies' element={<Movies />} />
            <Route path='/login' element={<Login />} />
          </Routes>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
