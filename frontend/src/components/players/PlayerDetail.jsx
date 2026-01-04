import { useState, useEffect } from 'react'
import { useParams, Link } from "react-router";
import { api } from "../../api/axios";
import LinearProgress from "@mui/material/LinearProgress";

import '../../App.css'
import "./Player.css"


function PlayerDetail() {

  const { id: playerId } = useParams();
  const [player, setPlayer] = useState(null);

  useEffect(() => {
    api.get(`/api/players/${playerId}`)
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
        <div><strong>Nationalität:</strong> {player.nationality}</div>
        <div><strong>Beste Position:</strong> {player.preferredPosition}</div>
        <div>
          <strong>Talent:</strong> {player.talent}
          <LinearProgress variant="determinate" value={player.talent} />
        </div>
        <div><strong>SkillBasis:</strong> {player.skillLevel}</div>
        <div><strong>Effektiver Skill:</strong> {player.effectiveStrength}</div>
        <div><strong>Gehalt:</strong> {player.salery} €</div>
        <div><strong>Torwartspiel:</strong> {player.goalkeeping}</div>
        <div><strong>Schnelligkeit:</strong> {player.speed}</div>
        <div><strong>Ausdauer:</strong> {player.stamina}</div>
        <div><strong>Verteidigen:</strong> {player.defense}</div>
        <div><strong>Passen:</strong> {player.passing}</div>
        <div><strong>Schusskraft:</strong> {player.shooting}</div>
        <div><strong>Dribbling:</strong> {player.dribbling}</div>
        <div><strong>Spielintelligenz:</strong> {player.intelligence}</div>
      </div>
      <div className="player-stats">
        Saisonleistung
        <div><strong>Status:</strong> {player.playerStats.playerStatus}</div>
        <div><strong>Noch Tage</strong> {player.playerStats.remainingDaysForStatus}</div>
        <div><strong>Spiele:</strong> {player.playerStats.gamesPlayed}</div>
        <div><strong>Tore:</strong> {player.playerStats.goals}</div>
        <div><strong>Rot:</strong> {player.playerStats.red}</div>
        <div><strong>Gelb:</strong> {player.playerStats.yellow}</div>
        <div><strong>Gelbrot:</strong> {player.playerStats.yellowred}</div>
      </div>
    </div>
  );
}

export default PlayerDetail

