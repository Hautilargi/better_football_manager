function MatchDayFilters({ season, league, matchday, setSearchParams }) {

    const seasons = [1, 2, 3];
    const leagues = [1];
    const matchdays = Array.from({ length: 34 }, (_, i) => i + 1);
    
  const updateParam = (key, value) => {
    setSearchParams(prev => {
      const params = new URLSearchParams(prev);
      params.set(key, value);
      return params;
    });
  };

  return (
    <div style={{ marginBottom: "1rem" }}>
      <label>
        Saison:
        <select
          value={season}
          onChange={e => updateParam("season", e.target.value)}
        >
          {seasons.map(s => (
            <option key={s} value={s}>{s}</option>
          ))}
        </select>
      </label>

      <label style={{ marginLeft: "1rem" }}>
        Liga:
        <select
          value={league}
          onChange={e => updateParam("league", e.target.value)}
        >
          {leagues.map(l => (
            <option key={l} value={l}>{l}</option>
          ))}
        </select>
      </label>

      <label style={{ marginLeft: "1rem" }}>
        Spieltag:
        <select
          value={matchday}
          onChange={e => updateParam("matchday", e.target.value)}
        >
          {matchdays.map(m => (
            <option key={m} value={m}>{m}</option>
          ))}
        </select>
      </label>
    </div>
  );
}

export default MatchDayFilters;
