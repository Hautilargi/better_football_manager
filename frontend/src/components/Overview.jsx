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
    Hier siehst du die Übersicht zu deinem Team {posts.name}
    <h2>Finanzen</h2>
    {posts.balance} €
    <h2>Links zu aktueller Liga & Spieltag</h2>
    Aktuelle Ligastufe {posts.tier}

    <h2>Mein Stadion</h2>
    </>
  );
}

export default Overview;