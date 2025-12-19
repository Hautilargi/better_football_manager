import { useState, useEffect } from 'react'
import axios from 'axios';
import '../App.css'

function Teams() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/teams')
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