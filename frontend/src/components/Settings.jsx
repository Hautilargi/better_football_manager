import { useState, useEffect } from 'react'
import { api } from "../api/axios";
import '../App.css'

function Settings() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    api.get('/auth/me')
      .then(response => {
        setPosts(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);
  return (
    <>
    <h2>Einstellungen</h2>
    Username: {posts.name}<br></br>
    Email: {posts.email} <br></br>
    User_ID: {posts.id} <br></br>
    Team_ID: {posts.team}<br></br> 
    </>
  );
}
export default Settings;
