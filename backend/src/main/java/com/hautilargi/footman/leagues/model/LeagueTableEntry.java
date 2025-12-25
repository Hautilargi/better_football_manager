package com.hautilargi.footman.leagues.model;

import com.hautilargi.footman.clubs.model.Team;

public class LeagueTableEntry implements Comparable<LeagueTableEntry> {

    private final Team team;
    private int games=0;
    private int goals=0;
    private int goalsAgainst=0;
    private int points=0;
    private int wins=0;
    private int draws=0;
    private int losses=0;

    public LeagueTableEntry(Team team) {
        this.team = team;
    }

    public void spielErgebnis(int eigeneTore, int gegentore) {
        this.games++;
        this.goals += eigeneTore;
        this.goalsAgainst += gegentore;

        if (eigeneTore > gegentore) {
            points += 3;
            wins++;
        } else if (eigeneTore == gegentore) {
            points += 1;
            draws++;
        }
        else{
            losses++;
        }
    }

    public int getTordifferenz() {
        return goals - goalsAgainst;
    }

    public String getName() {
        return team.getName();
    }

    @Override
    public int compareTo(LeagueTableEntry other) {
        int cmp = Integer.compare(other.points, this.points);
        if (cmp != 0) return cmp;

        cmp = Integer.compare(other.getTordifferenz(), this.getTordifferenz());
        if (cmp != 0) return cmp;

        cmp = Integer.compare(other.goals, this.goals);
        if (cmp != 0) return cmp;

        return Long.compare(other.team.getId(), this.team.getId());
    }

    @Override
    public String toString() {
        return String.format(
                "%-15s | Sp: %2d | Tore: %2d:%2d | Diff: %3d | Pkt: %2d",
                team.getName(), games, goals, goalsAgainst, getTordifferenz(), points
        );
    }

    public Team getTeam() {
        return team;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

        public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}