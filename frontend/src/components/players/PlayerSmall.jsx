import { Link } from "react-router";
import "./Player.css"


function PlayerSmall({ member }) {
  return (
      <div className="player-header">
      <strong>
        {member.firstName} {member.lastName}
      </strong>
      <div>
      <div>Alter: {member.age}</div>
      <div>Skill: {member.skillLevel}</div>
      <div>Nationalität: {member.nationality}</div>
      <Link to={`/players?playerId=${member.id}`}>
          Details
      </Link>
    </div>
    </div>
  );
}

export default PlayerSmall;