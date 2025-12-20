import { useState } from 'react'
import Players from './components/Players.jsx';
import Teams from './components/Teams.jsx';
import './App.css'
import Match from './components/Match.jsx';

function App() {
  const [posts, setPosts] = useState([]);

  return (
    <div>
      <h1>Players</h1>
      <Players />
      <h1>Teams</h1>
      <Teams />
      <h1>Sample Match between the teams</h1>
      <Match /> 
    </div>
  );
}

export default App;