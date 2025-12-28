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
import TeamDetail from './components/clubs/TeamDetail.jsx'
import MatchDetail from './components/matches/MatchDetail.jsx'




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
        <Link to="/settings">Einstellungen</Link> |{" "}
        <Link to="/login">Login/Logout</Link>  
      </nav>

      {/* Routes */}
      <Routes>
        {/* Main Pages */}
        <Route path="/" element={<Home />} />
        <Route path="/allPlayers" element={<AllPlayers />} />
        <Route path="/allTeams" element={<AllTeams />} />
        <Route path="/overview" element={<Overview />} />
        <Route path="/roster" element={<MyRoster />} />
        <Route path="/league" element={<MatchDay />} />
        <Route path="/testmatch" element={<TestMatch />} />
        <Route path="/settings" element={<Settings />} />
        <Route path="/login" element={<Login />} />
        {/* Links to dedicated instance */}
        <Route path="/players/:id" element={<PlayerDetail />} />
        <Route path="/teams?/:id" element={<TeamDetail />} />
        <Route path="/matches?/:id" element={<MatchDetail />} />
      </Routes>
    </BrowserRouter>

  );
}

export default App;