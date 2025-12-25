import { useState, useEffect } from 'react'
import { api } from "../../api/axios";
import '../../App.css'
import "./Player.css"
import { useSearchParams } from "react-router";
import { Link } from 'react-router';

function format(str, ...args) {
  return str.replace(/{(\d+)}/g, (match, index) => args[index]);
}

function PlayerDetail() {

  const [searchParams] = useSearchParams();
  const playerId = searchParams.get("playerId") ?? "1";
  const [player, setPlayer] = useState(null);

  useEffect(() => {
    api.get(format('/api/players/{0}',playerId))
      .then(res => setPlayer(res.data))
      .catch(console.error);
  }, []);

  if (!player) {
    return <div>Lade Spieler…</div>;
  }

  return (
    <div className="player-card">
      <div className="player-header">
        <h2>{player.firstName} {player.lastName}</h2>
        <div className="player-team">
          Team:{" "}
          <Link to={`/teams?teamId=${player.team.id}`}>
            {player.team.name}
          </Link>
        </div>
      </div>
            
      <div className="player-stats">
        <div><strong>Alter:</strong> {player.age}</div>
        <div><strong>Skill:</strong> {player.skillLevel}</div>
        <div><strong>Nationalität:</strong> {player.nationality}</div>
        <div><strong>Gehalt:</strong> {player.salery} €</div>
        <div><strong>Schnelligkeit:</strong> {player.speed}</div>
        <div><strong>Ausdauer:</strong> {player.stamina}</div>
        <div><strong>Passen:</strong> {player.passing}</div>
        <div><strong>Schusskraft:</strong> {player.shooting}</div>
        <div><strong>Dribbling:</strong> {player.dribbling}</div>
      </div>
    </div>
  );
}

export default PlayerDetail

