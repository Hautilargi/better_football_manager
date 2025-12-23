import { useState, useEffect } from 'react'
import { api } from "../api/axios";
import '../App.css'

function Overview() {
  const [posts, setPosts] = useState([]);
  useEffect(() => {
    api.get('/api/me/team')
      .then(response => {
        setPosts(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);
  return (
    <>
    <h1>Mein Verein</h1>
    Hier siehst du irgendwann die übersicht zu deinem Team

    <h2>Finanzen</h2>
    <h2>Links zu aktueller Liga & Spieltag</h2>
    <h2>Mein Stadion</h2>
    </>
  );
}

export default Overview;