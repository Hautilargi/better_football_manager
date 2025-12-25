import { useState, useEffect } from 'react'
import { api } from "../../api/axios";
import '../../App.css'
import { Link } from 'react-router';
function AllPlayers() {
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
        <li key={post.id}>{post.lastname}, {post.firstname} - Stärke: {post.skillLevel}
        <br></br>
        <Link to={`/players?playerId=${post.id}`}>
          Details
      </Link>
        
        </li>
      ))}
    </ul>
  );
}

export default AllPlayers;