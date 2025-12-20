package com.hautilargi.footman.core;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hautilargi.footman.model.players.Player;
import com.hautilargi.footman.model.clubs.Team;
import com.hautilargi.footman.model.matches.Match;
import com.hautilargi.footman.model.matches.MatchEvent;

public class MatchProcessor {

   private static final Random RANDOM = new Random();

    public static Match processMatch(Team home,Team away) {
        List<MatchEvent> events = new ArrayList<>();
        int goalsHome = 0;
        int goalsAway = 0;

        for (int minute = 1; minute <= 90; minute++) {


            maybeCard(minute, home, events);
            maybeCard(minute, away, events);

            if (maybeGoal(home, away)) {
                Player scorer = home.getPlayers().get(RANDOM.nextInt(0,10));
                //scorer.scoreGoal();
                goalsHome++;
                events.add(new MatchEvent(minute, MatchEvent.Type.GOAL, scorer, "Goal by " + scorer.getFirstName() + " " + scorer.getLastName() + " for " + home.getName()));
            }

            if (maybeGoal(away, home)) {
                Player scorer = away.getPlayers().get(RANDOM.nextInt(0,10));
                //scorer.scoreGoal();
                goalsAway++;
                events.add(new MatchEvent(minute, MatchEvent.Type.GOAL, scorer, "Goal by " + scorer.getFirstName() + " " + scorer.getLastName() + " for " + away.getName()));
            }
        }

        //updateForm(home, away, goalsHome, goalsAway);

        return new Match(home, away, goalsHome, goalsAway, events);

    }


    private static double strength(Team team) {
        double strength = team.getPlayers().stream()
                   .mapToInt(Player::getSkillLevel)
                   .average()
                   .orElse(50);
        //TODO Korrekt karten berechnen
        return strength/11*team.getPlayers().size();
    }

    private static boolean maybeGoal(Team atk, Team def) {
        double chance =strength(atk) / (strength(atk)+ strength(def));
        //TODO Korrekt karten berechnen
        chance *= atk.getPlayers().size() / 11.0;
        return RANDOM.nextDouble() < chance * 0.015;
    }

    private static void maybeCard(int minute, Team team, List<MatchEvent> events) {
        if (RANDOM.nextDouble() < 0.01) {
            Player p = team.getPlayers().get(RANDOM.nextInt(0,10));
            //TODO Gelbe und Rote Karten verwalten
            //p.setYellowCards(p.getYellowCards() + 1);
            events.add(new MatchEvent(minute, MatchEvent.Type.YELLOW, p,  "Yellow card to " + p.getFirstName() + " " + p.getLastName() + " of " + team.getName()  ));

            if (RANDOM.nextDouble() < 0.1) {
               // p.setRedCard(true);
                events.add(new MatchEvent(minute, MatchEvent.Type.RED, p, "Red card to " + p.getFirstName() + " " + p.getLastName() + " of " + team.getName()   ));
            }
        }
    }

}

