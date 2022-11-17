import { useState } from 'react';
import { Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import AuthContext from './components/AuthContext';
import Home from './components/Home';
import Movies from './components/Movies';
import Navbar from './components/Navbar';
import { LOCAL_STORAGE_TOKEN_KEY } from './services/authenticationService';


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

  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <div>
          <Navbar />
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/movies' element={<Movies />} />
          </Routes>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
