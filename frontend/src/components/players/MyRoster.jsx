import { useState, useEffect } from 'react'
import { api } from "../../api/axios";
import '../../App.css'
import PlayerSmall from './PlayerSmall';

function MyRoster() {
  const [members, setMembers] = useState([]);

  useEffect(() => {
    api.get('/api/me/roster')
      .then(res => setMembers(res.data.rosterMembers))
      .catch(console.error);
  }, []);

  return (
    <>
      <h1>Mein Kader</h1>
      <ul className="no-bullets">
        {members.map(member => (
          <li className="boxed" key={member.id}>
            <PlayerSmall member={member} />
          </li>
        ))}
      </ul>
    </>
  );
}

export default MyRoster