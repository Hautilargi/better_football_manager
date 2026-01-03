package com.hautilargi.footman.core.processing;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hautilargi.footman.clubs.model.HistorySquad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.matches.model.MatchEvent;
import com.hautilargi.footman.players.model.HistoryPlayer;

public class SimpleMatchProcessor implements MatchProcessor {

   private static final Random RANDOM = new Random();
        
public static Match processMatch(Team homeTeam, Team awayTeam, HistorySquad home,HistorySquad away) {




        List<MatchEvent> events = new ArrayList<>();
        int goalsHome = 0;
        int goalsAway = 0;

        for (int minute = 1; minute <= 90; minute++) {

            maybeCard(minute, home, events);
            maybeCard(minute, away, events);

            if (maybeGoal(home, away)) {
                HistoryPlayer scorer = home.getPlayers().get(RANDOM.nextInt(0,10));
                //scorer.scoreGoal();
                goalsHome++;
                events.add(new MatchEvent(minute, MatchEvent.Type.GOAL, scorer, "Goal by " + scorer.getFirstName() + " " + scorer.getLastname() + " for "+ homeTeam.getName() ));
            }

            if (maybeGoal(away, home)) {
                HistoryPlayer scorer = away.getPlayers().get(RANDOM.nextInt(0,10));
                //scorer.scoreGoal();
                goalsAway++;
                events.add(new MatchEvent(minute, MatchEvent.Type.GOAL, scorer, "Goal by " + scorer.getFirstName() + " " + scorer.getLastname() + " for "+ awayTeam.getName() ));
            }
        }


        Match match= new Match(homeTeam, awayTeam,home, away, goalsHome, goalsAway, 0,0, events);
        match.setPlayed(true);
        System.out.println(String.format("Processing Match -  %s -  %s ", strength(home), strength(away)));
        System.out.println(String.format("Processing Match -  %s %s -  %s %s ", homeTeam.getName(), goalsHome, goalsAway, awayTeam.getName()));
        return match;

    }


    private static double strength(HistorySquad team) {
        double strength = team.getPlayers().stream()
                   .mapToInt(HistoryPlayer::getSkillLevel)
                   .average()
                   .orElse(50);
        //TODO Korrekt karten berechnen
        return strength/11*team.getPlayers().size();
    }

    private static boolean maybeGoal(HistorySquad atk, HistorySquad def) {
        double chance =strength(atk) / (strength(atk)+ strength(def));
        //TODO Korrekt karten berechnen
        chance *= atk.getPlayers().size() / 11.0;
        return RANDOM.nextDouble() < chance * 0.015;
    }

    private static void maybeCard(int minute, HistorySquad squad, List<MatchEvent> events) {
        if (RANDOM.nextDouble() < 0.01) {
            HistoryPlayer p = squad.getPlayers().get(RANDOM.nextInt(0,10));
            //TODO Gelbe und Rote Karten verwalten
            //p.setYellowCards(p.getYellowCards() + 1);
            events.add(new MatchEvent(minute, MatchEvent.Type.YELLOW, p,  "Yellow card to " + p.getFirstName() + " " + p.getLastname()));

            if (RANDOM.nextDouble() < 0.1) {
               // p.setRedCard(true);
                events.add(new MatchEvent(minute, MatchEvent.Type.RED, p, "Red card to " + p.getFirstName() + " " + p.getLastname()));
            }
        }
    }

}

