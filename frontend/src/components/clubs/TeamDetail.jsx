import { useState, useEffect } from 'react'
import { useParams, Link } from "react-router";
import { api } from "../../api/axios";
import PlayerSmall from '../players/PlayerSmall';
import '../../App.css'

function format(str, ...args) {
  return str.replace(/{(\d+)}/g, (match, index) => args[index]);
}

function TeamDetail() {

  const { id: teamId } = useParams();
  const [teamMembers, setTeam] = useState([]);
  const [teamData, setTeamData] = useState([]);

  useEffect(() => {
    api.get(format('/api/teams/{0}/roster',teamId))
      .then(res => setTeam(res.data.rosterMembers))
      .catch(console.error);
  }, []);

  useEffect(() => {
    api.get(format('/api/teams/{0}',teamId))
      .then(res => setTeamData(res.data))
      .catch(console.error);
  }, []);



  return (
    <>
    <h1>Teamübersicht</h1>
    TeamName: {teamData.name}
    TeamId: {teamData.id}

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