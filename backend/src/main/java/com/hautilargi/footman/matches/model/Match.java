package com.hautilargi.footman.matches.model;

import java.util.List;

import com.hautilargi.footman.clubs.model.HistorySquad;
import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.clubs.model.Stadium;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Match {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne (cascade = CascadeType.ALL)
    public HistorySquad home;
    @ManyToOne( cascade = CascadeType.ALL)
    public HistorySquad away;

    public int goalsHome;
    public int goalsAway;


    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<MatchEvent> events;

    @ManyToOne
    public Stadium venue;


    public Match() {
    }

    public Match(HistorySquad h, HistorySquad a, int gh, int ga, List<MatchEvent> e) {
        home = h;
        away = a;
        goalsHome = gh;
        goalsAway = ga;
        events = e;
    }

    public long getId() {
        return id;
    }
    public HistorySquad getHome() {
        return home;
    }   
    public HistorySquad getAway() {
        return away;
    }
    public int getGoalsHome() {
        return goalsHome;
    }
    public int getGoalsAway() {
        return goalsAway;
    }
    public List<MatchEvent> getEvents() {
        return events;
    }
    public Stadium getVenue() {
        return venue;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setHome(HistorySquad home) {
        this.home = home;
    }
    public void setAway(HistorySquad away) {
        this.away = away;
    }
    public void setGoalsHome(int goalsHome) {
        this.goalsHome = goalsHome;
    }
    public void setGoalsAway(int goalsAway) {
        this.goalsAway = goalsAway;
    }
    public void setEvents(List<MatchEvent> events) {
        this.events = events;
    }
    public void setVenue(Stadium venue) {
        this.venue = venue;
    }
    


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s - %s :\n",home.getTeam().getName(),away.getTeam().getName()));
        sb.append(String.format("Match Result: %s %d - %d %s\n", "TEST", goalsHome, goalsAway, "TEST"));
        sb.append("Match Events:\n");
        for (MatchEvent event : events) {
            sb.append(event.toString()).append("\n");
        }
        return sb.toString();
    }
}