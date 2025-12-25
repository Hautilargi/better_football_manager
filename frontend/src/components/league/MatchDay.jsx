import { useState, useEffect } from 'react'
import { useSearchParams } from "react-router";
import { api } from "../../api/axios";
import MatchDetail from './MatchDetail'
import MatchDayFilters from './MatchDayFilters'
import MatchDayTable from './MatchDayTable'

import '../../App.css'


function format(str, ...args) {
  return str.replace(/{(\d+)}/g, (match, index) => args[index]);
}


function MatchDay() {
  const [posts, setPosts] = useState([]);
  const [searchParams, setSearchParams] = useSearchParams();
  const [selectedMatch, setSelectedMatch] = useState(null)
  const season = searchParams.get("season") ?? "1";
  const league = searchParams.get("league") ?? "1";
  const matchday = searchParams.get("matchday") ?? "1";


  useEffect(() => {
    api.get(format('/api/matches?season={0}&league={1}&matchday={2}',season,league,matchday))
      .then(response => {
        setPosts(response.data);
      })
      .catch(error => {
        console.error(error);
      });
  }, [season, league, matchday]);


  return (
    <>
    <MatchDayFilters
      season={season}
      league={league}
      matchday={matchday}
      setSearchParams={setSearchParams}
    />

    <h2>Ligaübersicht Testsaison {season}  - Liga {league} - Spieltag {matchday}</h2>
    Tipp: Oben in der URL kann der Spieltag angepasst werden (?season={0}&league={1}&matchday={2})
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
      <MatchDayTable
          leagueNo={league}
          seasonNo={season}
          matchdayNo={matchday}
      />
    </>
  )
}

export default MatchDay;