import { useState } from 'react'
import {  BrowserRouter, Routes, Route, Link  } from "react-router"
import './App.css'
import AllPlayers from './components/debug/AllPlayers.jsx';
import AllTeams from './components/debug/AllTeams.jsx';
import TestMatch from './components/debug/TestMatch.jsx';

import Home from './components/Home.jsx';
import MatchDay from './components/MatchDay.jsx';
import Login from './components/Login.jsx';
import MySquad from './components/MySquad.jsx';
import Overview from './components/Overview.jsx';
import Settings from './components/Settings.jsx';





function App() {
  const [posts, setPosts] = useState([]);

  return (
      <BrowserRouter>
      {/* Navigation */}
      <nav>
        <Link to="/">Home</Link> |{" "}
        <Link to="/players">Spieler (Alle)</Link> |{" "}
        <Link to="/teams">Teams (Alle)</Link> |{" "}
        <Link to="/overview">Mein Verein</Link> |{" "}
        <Link to="/squad">Mein Kader</Link> |{" "}
        <Link to="/league">Meine Liga</Link> |{" "}
        <Link to="/testmatch">Testmatch</Link> |{" "}
        <Link to="/login">Login/Logout</Link>  |{" "}
        <Link to="/settings">Einstellungen</Link>
      </nav>

      {/* Routes */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/players" element={<AllPlayers />} />
        <Route path="/teams" element={<AllTeams />} />
        <Route path="/overview" element={<Overview />} />
        <Route path="/squad" element={<MySquad />} />
        <Route path="/league" element={<MatchDay />} />
        <Route path="/testmatch" element={<TestMatch />} />
        <Route path="/login" element={<Login />} />
        <Route path="/settings" element={<Settings />} />
      </Routes>
    </BrowserRouter>

  );
}

export default App;