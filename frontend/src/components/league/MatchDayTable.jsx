import '../../App.css'
import './MatchDayTable.css'

import { useState, useEffect } from 'react'
import { api } from "../../api/axios";
import { Link } from "react-router";

function format(str, ...args) {
  return str.replace(/{(\d+)}/g, (match, index) => args[index]);
}
function MatchDayTable(params) {
  const [table, setTable] = useState([]);
  useEffect(() => {
    api.get(format('/api/leagues/table?season={0}&league={1}&matchday={2}',params.seasonNo,params.leagueNo,params.matchdayNo))
        .then(res => setTable(res.data.table))
        .catch(console.error);
  }, [params]);

  return (
    <>
      <h1>Spieltagstabelle</h1>
      <div>
        <table className='table'>
        <thead>
          <tr>
            <th>#</th>
            <th>Team</th>
            <th>Sp</th>
            <th>S</th>
            <th>U</th>
            <th>N</th>
            <th>Tore</th>
            <th>Diff</th>
            <th>Pkt</th>
          </tr>
        </thead>
        <tbody>
        {table.map((team) => (
        <tr
          key={team.teamId}
          className={`table-row
            ${team.position <= 2 ? "promotion" : ""}
            ${team.position >= 15 ? "relegation-down" : ""}
          `}>
        <td>{team.position}</td>
              <td>
                <Link to={`/teams/${team.teamId}`}>
                  {team.name}
                </Link>
              </td>

              <td>{team.gamesPlayed}</td>
              <td>{team.wins}</td>
              <td>{team.draws}</td>
              <td>{team.losses}</td>
              <td>
                {team.goalsFor}:{team.goalsAgainst}
              </td>

              <td
                style={{
                  color:
                    team.diff > 0
                      ? "green"
                      : team.diff < 0
                      ? "red"
                      : "inherit",
                }}
              >
                {team.diff}
              </td>

              <td style={{ fontWeight: "bold" }}>{team.points}</td>
            </tr>
          ))}
        </tbody>
      </table>
      </div>
    </>
  );
}
export default MatchDayTable