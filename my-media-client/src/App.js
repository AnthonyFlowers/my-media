import { useEffect, useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import AuthContext from './components/AuthContext';
import AuthRouteAdmin from './components/AuthRouteAdmin';
import AuthRouteUser from './components/AuthRouteUser';
import CreateAccount from './components/CreateAccount';
import Home from './components/Home';
import Login from './components/Login';
import Movies from './components/Movies';
import MovieSearch from './components/MovieSearch';
import Navbar from './components/Navbar';
import Profile from './components/Profile';
import TvShows from './components/TvShows';
import TvShowSearch from './components/TvShowSearch';
import { LOCAL_STORAGE_TOKEN_KEY, refresh } from './services/authenticationService';
import { getMovies, getMoviesSearch, getRecentMovies } from './services/movieService';
import { getTvShows } from './services/tvShowService';


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
        .then((user) => {
          login(user);
          setRefreshed(true);
        })
        .catch((ex) => {
          logout();
          console.log(ex);
          setRefreshed(true);
        });
    }
  }, [refreshed]);

  return (refreshed ?
    <AuthContext.Provider value={auth}>
      <Router>
        <div className="my-5 mx-10">
          <Navbar />
          <div className="bg-white rounded-lg p-5 my-5">
            <Routes>
              <Route path="/" element={<Home />} />
              <Route path="/movies" >
                <Route path="/movies" element={<Movies movieQueury={getMovies} />} />
                <Route path="/movies/search" element={<MovieSearch />} />
              </Route>
              <Route path="/tv-shows">
                <Route path="/tv-shows" element={<TvShows tvShowQuery={getTvShows} />} />
                <Route path="/tv-shows/search" element={<TvShowSearch />} />
              </Route>

              <Route path="/profile">
                <Route path="/profile" element={<AuthRouteUser user={user}><Profile /></AuthRouteUser>} />
              </Route>
              <Route path="/admin">
                <Route path="/admin" element={<AuthRouteUser user={user}><p>admin landing page</p></AuthRouteUser>} />
              </Route>

              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<CreateAccount />} />
              <Route path="*" element={<p>404 not found!</p>} />
            </Routes>
          </div>
        </div>
      </Router>
    </AuthContext.Provider> : <div className="flex justify-center items-center">
      <div className="spinner-border animate-spin inline-block w-8 h-8 border-4 rounded-full" role="status">
      </div>
      <span className="visually-hidden">Loading...</span>
    </div>
  );
}

export default App;
