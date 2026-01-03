import { useState, useEffect } from 'react'
import { useParams, Link } from "react-router";
import { api } from "../../api/axios";
import '../../App.css'


 function MatchDetail() {

  const { id: matchId } = useParams();
  const [match, setMatch] = useState(null);

  useEffect(() => {
    api.get(`/api/matches/'${matchId}`)
      .then(res => setMatch(res.data))
      .catch(console.error);
  }, []);
  return (
    <>
    <h2>
    {match.homeTeam.name} {match.goalsHome} : {match.goalsAway} {match.awayTeam.name}
    </h2>
    <h3>( {match.goalsHomeHalfTime} : {match.goalsAwayHalfTime} )</h3>
    <MatchSquad squad={match.homeSquad} ></MatchSquad>
    <MatchSquad squad={match.awaySquad} ></MatchSquad>
    <MatchReport
        match={match}
    />
    </>
  );
}

export default MatchDetail;