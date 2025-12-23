import { useState, useEffect } from 'react'
import { api } from "../api/axios";
import '../App.css'

function Teams() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    api.get('/api/teams')
      .then(response => {
        setPosts(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  return (
    <ul>
      {posts.map(post => (
        <li key={post.id}>{post.name}</li>
      ))}
    </ul>
  );
}

export default Teams;