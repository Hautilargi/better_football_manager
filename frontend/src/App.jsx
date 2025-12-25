import { useState } from 'react'
import {  BrowserRouter, Routes, Route, Link  } from "react-router"
import './App.css'
import AllPlayers from './components/debug/AllPlayers.jsx';
import AllTeams from './components/debug/AllTeams.jsx';
import TestMatch from './components/debug/TestMatch.jsx';

import Home from './components/Home.jsx';
import MatchDay from './components/league/MatchDay.jsx';
import Login from './components/Login.jsx';
import MyRoster from './components/players/MyRoster.jsx';
import Overview from './components/Overview.jsx';
import Settings from './components/Settings.jsx';
import PlayerDetail from './components/players/PlayerDetail.jsx'




function App() {
  const [posts, setPosts] = useState([]);

  return (
      <BrowserRouter>
      {/* Navigation */}
      <nav>
        <Link to="/">Home</Link> |{" "}
        <Link to="/allPlayers">Spieler (Alle)</Link> |{" "}
        <Link to="/allTeams">Teams (Alle)</Link> |{" "}
        <Link to="/overview">Mein Verein</Link> |{" "}
        <Link to="/roster">Mein Kader</Link> |{" "}
        <Link to="/league">Meine Liga</Link> |{" "}
        <Link to="/testmatch">Testmatch</Link> |{" "}
        <Link to="/login">Login/Logout</Link>  |{" "}
        <Link to="/settings">Einstellungen</Link>
      </nav>

      {/* Routes */}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/allPlayers" element={<AllPlayers />} />
        <Route path="/allTeams" element={<AllTeams />} />
        <Route path="/overview" element={<Overview />} />
        <Route path="/roster" element={<MyRoster />} />
        <Route path="/league" element={<MatchDay />} />
        <Route path="/testmatch" element={<TestMatch />} />
        <Route path="/login" element={<Login />} />
        <Route path="/settings" element={<Settings />} />
        <Route path="/players" element={<PlayerDetail />} />
        <Route path="/teams" element={<PlayerDetail />} />
      </Routes>
    </BrowserRouter>

  );
}

export default App;