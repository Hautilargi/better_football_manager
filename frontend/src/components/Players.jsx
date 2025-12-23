import { useState, useEffect } from 'react'
import { api } from "../api/axios";
import '../App.css'

function Players() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    api.get('/api/players')
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
        <li key={post.id}>{post.lastname},{post.firstName} - Stärke: {post.skillLevel}</li>
      ))}
    </ul>
  );
}

export default Players;