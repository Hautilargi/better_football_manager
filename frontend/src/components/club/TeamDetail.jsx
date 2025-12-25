import { useState, useEffect } from 'react'
import { api } from "../../api/axios";
import '../../App.css'
import { useSearchParams } from "react-router";
import PlayerSmall from '../players/PlayerSmall';

function format(str, ...args) {
  return str.replace(/{(\d+)}/g, (match, index) => args[index]);
}

function PlayerDetail() {

  const [searchParams] = useSearchParams();
  const teamId = searchParams.get("teamId") ?? "1";
  const [teamMembers, setTeam] = useState([]);

  useEffect(() => {
    api.get(format('/api/teams/{0}',teamId))
      .then(res => setTeam(res.data))
      .catch(console.error);
  }, []);
  return (
    <>
      <h1>Kader</h1>
      <ul className="no-bullets">
        {teamMembers.map(member => (
          <li className="boxed" key={member.id}>
            <PlayerSmall member={member} />
          </li>
        ))}
      </ul>
    </>
  );
}

export default TeamDetail