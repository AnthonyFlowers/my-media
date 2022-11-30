import { useEffect, useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import AuthContext from './components/AuthContext';
import Home from './components/Home';
import Login from './components/Login';
import Movies from './components/Movies';
import Navbar from './components/Navbar';
import Profile from './components/Profile';
import { LOCAL_STORAGE_TOKEN_KEY, refresh } from './services/authenticationService';
import { getMovies, getRecentMovies, getUserMovies } from './services/movieService';


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
          <div className="bg-white rounded-lg p-5 my-5">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/movies" >
              <Route path="/movies" element={<Movies movieQueury={getMovies} />} />
              <Route path="/movies/user" element={<Movies movieQueury={getUserMovies} />} />
              <Route path="/movies/recent" element={<Movies movieQueury={getRecentMovies} />} />
            </Route>
            <Route path="/profile">
              <Route path="/profile" element={<Profile />} />
            </Route>
            <Route path="/login" element={<Login />} />
          </Routes>
          </div>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
