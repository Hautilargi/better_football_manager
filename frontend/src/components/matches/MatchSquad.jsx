import { useState, useEffect } from 'react'
import { useParams, Link } from "react-router";
import { api } from "../../api/axios";
import '../../App.css'


 function MatchSquad() {

  const { id: matchId } = useParams();
  const [match, setMatch] = useState(null);

  useEffect(() => {
    api.get(`/api/matches/'${matchId}`)
      .then(res => setMatch(res.data))
      .catch(console.error);
  }, []);
  return (
    <>

    </>
  );
}

export default MatchSquad;