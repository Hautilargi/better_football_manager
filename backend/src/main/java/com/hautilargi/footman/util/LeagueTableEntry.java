package com.hautilargi.footman.util;

import com.hautilargi.footman.clubs.model.Team;

public class LeagueTableEntry implements Comparable<LeagueTableEntry> {

    private final Team team;
    private int games=0;
    private int goals=0;
    private int goalsAgainst=0;
    private int points=0;

    public LeagueTableEntry(Team team) {
        this.team = team;
    }

    public void spielErgebnis(int eigeneTore, int gegentore) {
        this.games++;
        this.goals += eigeneTore;
        this.goalsAgainst += gegentore;

        if (eigeneTore > gegentore) {
            points += 3;
        } else if (eigeneTore == gegentore) {
            points += 1;
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

        return this.compareTo(other);
    }

    @Override
    public String toString() {
        return String.format(
                "%-15s | Sp: %2d | Tore: %2d:%2d | Diff: %3d | Pkt: %2d",
                team.getName(), games, goals, goalsAgainst, getTordifferenz(), points
        );
    }
}