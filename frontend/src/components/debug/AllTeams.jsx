import { useState, useEffect } from 'react'
import { Link } from 'react-router';
import { api } from "../../api/axios";
import '../../App.css'

function AllTeams() {
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
        <li key={post.id}>{post.name}
                <br></br>
        <Link to={`/teams/${post.id}`}>
          Details
        </Link>
        </li>
      ))}
    </ul>
  );
}

export default AllTeams;