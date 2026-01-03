package com.hautilargi.footman.matches.model;

import java.util.Date;
import java.util.List;

import com.hautilargi.footman.clubs.model.HistorySquad;
import com.hautilargi.footman.clubs.model.Stadium;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.core.processing.MatchTypes;
import com.hautilargi.footman.leagues.model.League;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;


@Entity
@JsonIncludeProperties({"id","events","goalsHome","goalsAway","goalsHomeHalfTime","goalsAwayHalfTime","league","seasonNo","matchDay","matchType","homeTeam","awayTeam","played"})
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "league_id")
    private League league;
    
    private long season;
    
    @OneToOne (cascade = CascadeType.ALL)
    private HistorySquad homeSquad;

    @OneToOne( cascade = CascadeType.ALL)
    private HistorySquad awaySquad;

    @OneToMany( fetch = FetchType.EAGER, mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<MatchEvent> events;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_home_id")
    private Team homeTeam;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_away_id")
    private Team awayTeam;

    private int goalsHome;
    private int goalsAway;
    private int goalsHomeHalfTime;
    private int goalsAwayHalfTime;
    private int matchDay;
    private MatchTypes matchtype;
    private boolean played;

    @ManyToOne
    @JoinColumn(name = "venue_Id")
    private Stadium venue;


    public Match() {
    }

    public Match(Team hoTeam, Team aTeam, League league, long season, MatchTypes type, int matchday) {
        this.homeTeam=hoTeam;
        this.awayTeam=aTeam;
        this.league=league;
        this.season=season;
        this.matchtype=type;
        this.matchDay=matchday;
    }

    public Match(Team homeTeam, Team awayTeam, HistorySquad h, HistorySquad a, int gh, int ga, int ghHt, int gaHt,List<MatchEvent> e) {
        this.homeTeam=homeTeam;
        this.awayTeam=awayTeam;
        this.homeSquad = h;
        this.awaySquad = a;
        this.goalsHome = gh;
        this.goalsAway = ga;
        this.events = e;
        this.goalsAwayHalfTime=gaHt;
        this.goalsHomeHalfTime=ghHt;
    }

    /* Getters and setters */


    
    public MatchTypes getMatchtype() {
        return matchtype;
    }

    public int getGoalsHomeHalfTime() {
        return goalsHomeHalfTime;
    }

    public void setGoalsHomeHalfTime(int goalsHomeHalfTime) {
        this.goalsHomeHalfTime = goalsHomeHalfTime;
    }

    public int getGoalsAwayHalfTime() {
        return goalsAwayHalfTime;
    }

    public void setGoalsAwayHalfTime(int goalsAwayHalfTime) {
        this.goalsAwayHalfTime = goalsAwayHalfTime;
    }

    public void setMatchtype(MatchTypes matchtype) {
        this.matchtype = matchtype;
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

        public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public long getSeason() {
        return season;
    }

    public void setSeason(long season) {
        this.season = season;
    }

    public HistorySquad getHomeSquad() {
        return homeSquad;
    }

    public void setHomeSquad(HistorySquad homeSquad) {
        this.homeSquad = homeSquad;
    }

    public HistorySquad getAwaySquad() {
        return awaySquad;
    }

    public void setAwaySquad(HistorySquad awaySquad) {
        this.awaySquad = awaySquad;
    }

    public int getMatchDay() {
        return matchDay;
    }

    public void setMatchDay(int matchDay) {
        this.matchDay = matchDay;
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


    public boolean isPlayed() {
        return played;
    }

    public void setPlayed(boolean played) {
        this.played = played;
    }
}

