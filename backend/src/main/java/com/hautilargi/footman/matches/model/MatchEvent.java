package com.hautilargi.footman.matches.model;

import com.hautilargi.footman.players.model.HistoryPlayer;
import com.hautilargi.footman.players.model.Player;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MatchEvent {

    public enum Type { GOAL, YELLOW, RED, SHOT, FOUL, SUBSTITUTION, OFFSIDE, CORNER, PENALTY, SAVED_SHOT, SAVED_PENALTY, INJURY, OTHER }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int event_minute;
    private Type type;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_id")
    private HistoryPlayer player;
    private String description;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    public MatchEvent(int event_minute, Type type, HistoryPlayer player, String description) {
        this.event_minute = event_minute;
        this.type = type;
        this.player = player;
        this.description = description;
    }

    public int getEventMinute() {
        return event_minute;
    }   
    public Type getType() {
        return type;
    }       
    public HistoryPlayer getPlayer() {
        return player;
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
    public void setPlayer(HistoryPlayer player) {
        this.player = player;
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
    

    @Override
    public String toString() {
        return "Minute: " + event_minute + ", Type: " + type + ", Player: " + player.getFirstName() + " " + player.getLastName() + ", Description: " + description;
    }
}