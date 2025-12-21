import { useState } from 'react'
import {  BrowserRouter, Routes, Route, Link  } from "react-router"
import './App.css'
import Players from './components/Players.jsx';
import Teams from './components/Teams.jsx';
import Match from './components/Match.jsx';
import Home from './components/Home.jsx';
import MatchDay from './components/MatchDay.jsx';
import Login from './components/Login.jsx';




function App() {
  const [posts, setPosts] = useState([]);

  return (
      <BrowserRouter>
      {/* Navigation */}
      <nav>
        <Link to="/">Home</Link> |{" "}
        <Link to="/players">Players</Link> |{" "}
        <Link to="/teams">Teams</Link> |{" "}
        <Link to="/league?matchDay=1">Liga</Link> |{" "}
        <Link to="/testmatch">Testmatch</Link> |{" "}
         <Link to="/login">Login/Logout</Link>
      </nav>

      {/* Routes */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/players" element={<Players />} />
        <Route path="/teams" element={<Teams />} />
        <Route path="/league" element={<MatchDay />} />
        <Route path="/testmatch" element={<Match />} />
        <Route path="/login" element={<Login />} />
      </Routes>
    </BrowserRouter>

  );
}

export default App;