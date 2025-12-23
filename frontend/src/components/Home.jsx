import { useState, useEffect } from 'react'
import '../App.css'

function Home() {
  const [posts, setPosts] = useState([]);

  return (
    <>
    <h1>Welcome</h1>
    Hier passiert noch nix. Vergiss nicht dich einzuloggen oder zu registrieren. Sonst siehst du nix
    </>
  );
}

export default Home;