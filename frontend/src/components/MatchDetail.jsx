import '../App.css'

 function MatchDetail({ match, onClose }) {
  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>
          {match.homeTeam.name} {match.goalsHome} : {match.goalsAway} {match.awayTeam.name}
        </h2>
        Berechnet um {match.calculationTime}

        <ul className="no-bullets">
          {match.events && match.events.length > 0 ? (
            match.events.map((event, index) => (
              <li key={index}>
                {event.event_minute}. Minute - {event.type} – {event.description}
              </li>
            ))
          ) : (
            <li>Keine Events vorhanden</li>
          )}
        </ul>

        <button onClick={onClose}>Schließen</button>
      </div>
    </div>
  );
}

export default MatchDetail;

 