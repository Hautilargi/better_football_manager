package com.hautilargi.footman.model.matches;

import java.util.List;

import com.hautilargi.footman.model.clubs.Team;


import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;

public class Match {

    public Team home;
    public Team away;
    public int goalsHome;
    public int goalsAway;

    @OneToMany(mappedBy = "match", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<MatchEvent> events;

    public Match(Team h, Team a, int gh, int ga, List<MatchEvent> e) {
        home = h;
        away = a;
        goalsHome = gh;
        goalsAway = ga;
        events = e;
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Match Result: %s %d - %d %s\n", home.getName(), goalsHome, goalsAway, away.getName()));
        sb.append("Match Events:\n");
        for (MatchEvent event : events) {
            sb.append(event.toString()).append("\n");
        }
        return sb.toString();
    }
}