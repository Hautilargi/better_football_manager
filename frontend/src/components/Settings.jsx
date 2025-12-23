import { useState, useEffect } from 'react'
import '../App.css'

function Settings() {
  const [posts, setPosts] = useState([]);

  return (
    <>
    <h1>Einstellungen</h1>
    Hier siehst du irgendwann deine Userdaten
    </>
  );
}

export default Settings;