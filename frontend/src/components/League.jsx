import { useState, useEffect } from 'react'
import axios from 'axios';
import '../App.css'

function League() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    axios.get('/api/teams')
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

export default League;