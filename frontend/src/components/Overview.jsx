import { useState, useEffect } from 'react'
import '../App.css'

function Overview() {
  const [posts, setPosts] = useState([]);

  return (
    <>
    <h1>Mein Verein</h1>
    Hier siehst du irgendwann die übersicht zu deinem Team
    </>
  );
}

export default Overview;