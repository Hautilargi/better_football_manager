import MatchEvent from './MatchEvent';
import '../../App.css'
import './MatchReport.css'

 function MatchReport({ match, onClose }) {
  return (
    <div className="modal-overlay">
      <div className="modal">
        <h2>
          {match.homeTeam.name} {match.goalsHome} : {match.goalsAway} {match.awayTeam.name}
        </h2>
          <div className="timeline">

          {match.events && match.events.length > 0 ? (
            match.events.map((event) => (  
                <MatchEvent key={event.id} event={event} />
            ))
          ) : (
            <div>Keine Events vorhanden</div>
          )}
          </div>

        <button onClick={onClose}>Schließen</button>
      </div>
    </div>
  );
}

export default MatchReport;

 