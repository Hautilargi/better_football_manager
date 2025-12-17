package com.hautilargi.footman.model;

import java.util.List;

public class MatchResult {
    public Team home;
    public Team away;
    public int goalsHome;
    public int goalsAway;
    public List<MatchEvent> events;

    public MatchResult(Team h, Team a, int gh, int ga, List<MatchEvent> e) {
        home = h;
        away = a;
        goalsHome = gh;
        goalsAway = ga;
        events = e;
    }
}