import { useState, useEffect } from 'react'
import { useSearchParams } from "react-router";
import { api } from "../api/axios";
import MatchDetail from './MatchDetail'
import '../App.css'

function MatchDay() {
  const [posts, setPosts] = useState([]);
  const [searchParams] = useSearchParams();
  const [selectedMatch, setSelectedMatch] = useState(null)


  useEffect(() => {
    api.get('/api/matches?season=1&league=1&matchday='+searchParams.get("matchDay"))
      .then(response => {
        setPosts(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }, []);

  return (
    <>
    <h2>Ligaübersicht Testsaison 1 - Liga 1 - Spieltag {searchParams.get("matchDay")} </h2>
    Tipp: Oben in der URL kann der Spieltag angepasst werden (1-34)
    <ul className="no-bullets">
        {posts.map(post => (
          <li className="boxed" key={post.id}>
            {post.homeTeam.name} <b>{post.goalsHome} : {post.goalsAway}</b> {post.awayTeam.name}

            {post.played && (
              <button onClick={() => setSelectedMatch(post)}>
                Details
              </button>
            )}
            {!post.played && (
              <div>Noch nicht gespielt</div>
            )}
          </li>
        ))}
      </ul>

      {selectedMatch && (
        <MatchDetail
          match={selectedMatch}
          onClose={() => setSelectedMatch(null)}
        />
      )}
    </>
  )
}

export default MatchDay;