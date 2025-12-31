import { useState, useEffect } from 'react'
import { Link } from 'react-router';
import { api } from "../../api/axios";
import '../../App.css'
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
        <li key={post.id}>{post.lastName}, {post.firstName} - Stärke: {post.skillLevel}
        <br></br>
        <Link to={`/players/${post.id}`}>
          Details
        </Link>
        </li>
      ))}
    </ul>
  );
}

export default AllPlayers;