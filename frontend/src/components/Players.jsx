import { useState, useEffect } from 'react'
import axios from 'axios';
import '../App.css'

function Players() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    axios.get('http://localhost:8080/api/players')
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