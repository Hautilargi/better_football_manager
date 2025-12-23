import { useState, useEffect } from 'react'
import '../App.css'

function MySquad() {
  const [posts, setPosts] = useState([]);

  return (
    <>
    <h1>Mein Team</h1>
    Hier siehst du irgendwann die übersicht zu deinem Kader
    </>
  );
}

export default MySquad;