package com.hautilargi.footman.matches.model;

import java.util.List;

import com.hautilargi.footman.clubs.model.HistorySquad;
import com.hautilargi.footman.clubs.model.Stadium;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.leagues.model.League;
import com.hautilargi.footman.leagues.model.Season;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "league_id")
    public League league;

    @ManyToOne
    @JoinColumn(name = "season_id")
    public Season season;
    
    @OneToOne (cascade = CascadeType.ALL)
    public HistorySquad homeSquad;

    @OneToOne( cascade = CascadeType.ALL)
    public HistorySquad awaySquad;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<MatchEvent> events;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_id_home")
    public Team homeTeam;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_id_away")
    public Team awayTeam;

    public int goalsHome;
    public int goalsAway;

    public int matchDay;


    @ManyToOne
    public Stadium venue;


    public Match() {
    }

    public Match(Team homeTeam, Team awayTeam, HistorySquad h, HistorySquad a, int gh, int ga, List<MatchEvent> e) {
        this.homeTeam=homeTeam;
        this.awayTeam=awayTeam;
        homeSquad = h;
        awaySquad = a;
        goalsHome = gh;
        goalsAway = ga;
        events = e;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public HistorySquad getHome() {
        return homeSquad;
    }   
    public HistorySquad getAway() {
        return awaySquad;
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
    public void setHome(HistorySquad home) {
        this.homeSquad = home;
    }
    public void setAway(HistorySquad away) {
        this.awaySquad = away;
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

    public Team getHomeTeam(){
        return this.homeTeam;
    }

        public Team getAwayTeam(){
        return this.awayTeam;
    }
  public void setHomeTeam(Team homeTeam){
        this.homeTeam=homeTeam;
    }

  public void setAwayTeam(Team awayTeam){
        this.awayTeam=awayTeam;
    }


    


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s - %s :\n",homeTeam.getName(),awayTeam.getName()));
        sb.append(String.format("Match Result: %s %d - %d %s\n", "TEST", goalsHome, goalsAway, "TEST"));
        sb.append("Match Events:\n");
        for (MatchEvent event : events) {
            sb.append(event.toString()).append("\n");
        }
        return sb.toString();
    }
}