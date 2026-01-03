package com.hautilargi.footman.matches.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.hautilargi.footman.players.model.HistoryPlayer;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIncludeProperties({"id","event_minute","type","description","playerActive","playerPassive"})
public class MatchEvent {

    public enum Type { GOAL, YELLOW, RED, YELLOWRED, SHOT, SAVED_SHOT, FOUL, SUBSTITUTION, PENALTY, SAVED_PENALTY, INJURY, MISC, HALFTIME, KICKOFF, END }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id_active")
    private HistoryPlayer playerActive;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id_passive")
    @Nullable
    private HistoryPlayer playerPassive;

    private String description;
    private int event_minute;
    private Type type;

    public MatchEvent(){

    }

    public MatchEvent(int event_minute, Type type, HistoryPlayer player, String description) {
        this.event_minute = event_minute;
        this.type = type;
        this.playerActive = player;
        this.description = description;
    }

    /* Getters and Setters */
    public int getEventMinute() {
        return event_minute;
    }   
    public Type getType() {
        return type;
    }       
    public HistoryPlayer getPlayerActive() {
        return playerActive;
    }   
    public String getDescription() {
        return description;
    }

    public void setMatch(Match match) {
        this.match = match;
    }
    public Match getMatch() {
        return match;
    }
    public void setEventMinute(int event_minute) {
        this.event_minute = event_minute;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public void setPlayerActive(HistoryPlayer player) {
        this.playerActive = player;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
 
    public int getEvent_minute() {
        return event_minute;
    }

    public void setEvent_minute(int event_minute) {
        this.event_minute = event_minute;
    }

    public HistoryPlayer getPlayerPassive() {
        return playerPassive;
    }

    public void setPlayerPassive(HistoryPlayer playerPassive) {
        this.playerPassive = playerPassive;
    }

    @Override
    public String toString() {
        return "Minute: " + event_minute + ", Type: " + type + ", Player: " + playerActive.getFirstName() + " " + playerActive.getLastname() + ", Description: " + description;
    }

}