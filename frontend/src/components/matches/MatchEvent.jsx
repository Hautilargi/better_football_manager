import React from "react";
import { EVENT_CONFIG } from "./EventConfig";

export default function MatchEvent({ event }) {
  const config = EVENT_CONFIG[event.type] || EVENT_CONFIG.OTHER;

  return (
    <div className="event-row">
      <div className="minute">{event.event_minute}'</div>

      <div
        className="event-icon"
        style={{ backgroundColor: config.color }}
      >
        {config.icon}
      </div>

      <div className="event-content">
        <div className="event-title">{config.label}</div>
        <div className="event-description">
          {renderDescription(event)}
        </div>
      </div>
    </div>
  );
}

function renderDescription(event) {
  const active = event.playerActive
    ? `${event.playerActive.firstName} ${event.playerActive.lastName}`
    : "";

  const passive = event.playerPassive
    ? `${event.playerPassive.firstName} ${event.playerPassive.lastName}`
    : "";

  switch (event.type) {
    case "SUBSTITUTION":
      return (
        <>
          <strong>{active}</strong> ⟶ <strong>{passive}</strong>
        </>
      );
    default:
      return(
        <>
            {event.description}
        </>
      ) 
  }
}
