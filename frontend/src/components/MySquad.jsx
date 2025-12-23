import { useState, useEffect } from 'react'
import { api } from "../api/axios";
import '../App.css'

function MySquad() {
  const [posts, setPosts] = useState([]);
  useEffect(() => {
    api.get('/api/me/squad')
      .then(response => {
        setPosts(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);
  return (
    <>
    <h1>Mein Kader</h1>
    Hier siehst du irgendwann die übersicht zu deinem Kader

    <h2>Platzhalter für Kader</h2>
    <h2>Plathalter für aktuelle Aufstellung</h2>
    </>
  );
}

export default MySquad;