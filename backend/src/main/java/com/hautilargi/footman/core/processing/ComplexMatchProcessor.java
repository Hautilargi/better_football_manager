package com.hautilargi.footman.core.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.Future.State;

import com.hautilargi.footman.FootmanConstants;
import com.hautilargi.footman.clubs.model.HistorySquad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.core.util.WeightedRandom;
import com.hautilargi.footman.core.util.emum.Positions;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.matches.model.MatchEvent;
import com.hautilargi.footman.players.model.HistoryPlayer;

public class ComplexMatchProcessor implements MatchProcessor {

    private static final Random RANDOM = new Random();

    public static enum PitchZone {
        BOXOWN, OWN, CENTER, ENEMY, BOXENEMY
    }

    public static enum PitchSide {
        LEFT, CENTER, RIGHT
    }

    public static enum GamePhase {
        FIRST, SECOND, EXTRAFIRST, EXTRASECOND, PENALTY
    }

    public static enum GameStep {
        PASS_FLAT, PASS_DEEP, CROSS, SHOOT, DRIBBLE
    }

    public static enum StepResult {
        CRITICAL, SUCCESS, NEUTRAL, FAILURE, CRITICALFAIL
    }

    public static Match processMatch(Team homeTeam, Team awayTeam, HistorySquad home, HistorySquad away) {

        // Init all the needed variables
        Set<HistoryPlayer> bookedPlayers = new HashSet<>();
        HistoryPlayer activePlayer;
        PitchZone zone = PitchZone.CENTER;
        PitchSide side = PitchSide.CENTER;
        GamePhase phase = GamePhase.FIRST;
        activePlayer = pickPlayer(home.getPlayers(), zone, side, false);
        List<HistoryPlayer> activeTeam = home.getPlayers();
        List<HistoryPlayer> passiveTeam = away.getPlayers();

        List<MatchEvent> events = new ArrayList<>();
        
        MatchState matchState = new MatchState();
        matchState.setHometeamId(homeTeam.getId());
        matchState.setPlaytime(0);


        writeEvent(events, MatchEvent.Type.KICKOFF, activePlayer, activePlayer, matchState);

        while (matchState.getPlaytime() < 5400) {
            boolean playInProgress = true;
            while (playInProgress) {
                GameStep nextStep = makeDecision(activePlayer, side, zone);
                HistoryPlayer passivePlayer = pickPlayer(passiveTeam, zone, side, true);
                MatchStepEvent stepResult = resolveStep(nextStep, activePlayer, passivePlayer, matchState);

                /* 
                System.out.println(
                        String.format("Step for %s. %s with players %s vs %s. Ball currently in %s | %s. Result is %s ",
                                activeTeam.getFirst().getTeam().getName(),
                                nextStep,
                                activePlayer.getLastname(),
                                passivePlayer.getLastname(),
                                zone,
                                side,
                                stepResult.getResult()));
                */
                playInProgress = stepResult.isPlayContinued();
                matchState.setPlaytime(matchState.getPlaytime()+stepResult.getTimeTaken());
                if (stepResult.getTriggeredEvent() != null) {
                    //System.out.println("EVENT TRIGGERED " + stepResult.getTriggeredEvent());
                    switch (stepResult.getTriggeredEvent()) {
                        case FOUL:
                            if (!maybeCard(passivePlayer, bookedPlayers, events, matchState)) {
                                writeEvent(events, stepResult.getTriggeredEvent(), activePlayer, passivePlayer,
                                        matchState);
                            }
                            break;
                        case GOAL:
                            if (activePlayer.getTeam().getName().equals(homeTeam.getName())) {
                                matchState.setGoalsHome(matchState.getGoalsHome()+1);
                            } else {
                                matchState.setGoalsAway(matchState.getGoalsAway()+1);
                            }
                            writeEvent(events, stepResult.getTriggeredEvent(), activePlayer, passivePlayer, matchState);
                            break;
                        default:
                            writeEvent(events, stepResult.getTriggeredEvent(), activePlayer, passivePlayer, matchState);
                            break;
                    }
                }
                if (playInProgress) {
                    zone = changePitchZone(zone, stepResult.getZoneChange());
                    side = changePitchSide(side, stepResult.getSideChange());
                    if (stepResult.isPlayerChange()) {
                        activePlayer = pickPlayer(activeTeam, zone, side, false);
                    }
                }
                // Play is over, switching direction & sides
                else {
                    List<HistoryPlayer> tmp = activeTeam;
                    activeTeam = passiveTeam;
                    passiveTeam = tmp;
                    activePlayer = pickPlayer(activeTeam, zone, side, false);
                    if (zone.equals(PitchZone.BOXENEMY)) {
                        zone = PitchZone.BOXOWN;
                    }
                    if (zone.equals(PitchZone.ENEMY)) {
                        zone = PitchZone.OWN;
                    }
                    if (side.equals(PitchSide.LEFT)) {
                        side = PitchSide.RIGHT;
                    }
                    if (side.equals(PitchSide.RIGHT)) {
                        side = PitchSide.LEFT;
                    }
                }
            }
            // Half Time
            if (matchState.getPlaytime() > 2700 && phase.equals(GamePhase.FIRST)) {
                matchState.setPlaytime(2700);
                matchState.setGoalsAwayHalfTime(matchState.getGoalsAway());
                matchState.setGoalsHomeHalfTime(matchState.getGoalsHome());

                activeTeam = away.getPlayers();
                passiveTeam = home.getPlayers();
                activePlayer = pickPlayer(activeTeam, zone, side, false);
                zone = PitchZone.CENTER;
                side = PitchSide.CENTER;
                phase = GamePhase.SECOND;
                writeEvent(events, MatchEvent.Type.HALFTIME, activePlayer, activePlayer, matchState);
            }
        }
        Match match = new Match(homeTeam, awayTeam, home, away, matchState.getGoalsHome(), matchState.getGoalsAway(), matchState.getGoalsHomeHalfTime(),
                matchState.getGoalsAwayHalfTime(), events);
        match.setPlayed(true);
        //System.out.println(String.format("Processed Match -  %s %s -  %s %s ", homeTeam.getName(), goalsHome, goalsAway, awayTeam.getName()));
        writeEvent(events, MatchEvent.Type.END, activePlayer, activePlayer, matchState);
        return match;
    }

    private static GameStep makeDecision(HistoryPlayer player, PitchSide side, PitchZone zone) {
        Map<GameStep, Double> weightedEvents = new HashMap<>();

        if (PitchZone.BOXOWN.equals(zone)) {
            weightedEvents.put(GameStep.CROSS, 2D);
            weightedEvents.put(GameStep.PASS_DEEP, 1D);
            weightedEvents.put(GameStep.DRIBBLE, 1D);
        }
        else if (PitchZone.OWN.equals(zone)) {
            weightedEvents.put(GameStep.CROSS, 1D);
            weightedEvents.put(GameStep.PASS_DEEP, 1D);
            weightedEvents.put(GameStep.PASS_FLAT, 1D);
            weightedEvents.put(GameStep.DRIBBLE, 1D);
        }
        else if (PitchZone.CENTER.equals(zone)) {
            weightedEvents.put(GameStep.CROSS, 1D);
            weightedEvents.put(GameStep.PASS_DEEP, 1D);
            weightedEvents.put(GameStep.PASS_FLAT, 1D);
            weightedEvents.put(GameStep.DRIBBLE, 1D);
        }
        else if (PitchZone.ENEMY.equals(zone)) {
            weightedEvents.put(GameStep.CROSS, 1D);
            weightedEvents.put(GameStep.PASS_FLAT, 1D);
            weightedEvents.put(GameStep.PASS_DEEP, 1D);
            weightedEvents.put(GameStep.DRIBBLE, 1D);
            if (PitchSide.CENTER.equals(side)) {
                weightedEvents.put(GameStep.SHOOT, 1D);
            }
        }
        else if (PitchZone.BOXENEMY.equals(zone)) {
            weightedEvents.put(GameStep.PASS_FLAT, 1D);
            weightedEvents.put(GameStep.SHOOT, 4D);
            if (!PitchSide.CENTER.equals(side)) {
                weightedEvents.put(GameStep.PASS_FLAT, 1D);
                weightedEvents.put(GameStep.CROSS, 1D);
                weightedEvents.put(GameStep.DRIBBLE, 1D);
                weightedEvents.put(GameStep.SHOOT, 1D);
            }
        }
        return WeightedRandom.getRandomByWeight(weightedEvents);
    }

    private static MatchStepEvent resolveStep(GameStep step, HistoryPlayer player, HistoryPlayer playerAgainst, MatchState state) {
        // Stepspecific modifiers
        StepResult result = duell(getEffectiveStrenghtForStepAndMintute(player, step, true, state),
                getEffectiveStrenghtForStepAndMintute(playerAgainst, step, false, state));
        int sideChange = 0;
        int zoneChange = 0;
        int timeTaken = 30;
        MatchEvent.Type type = null;
        boolean continuePlay = true;
        boolean playerChange = false;

        switch (step) {
            case SHOOT:
                switch (result) {
                    case CRITICAL, SUCCESS:
                        type = MatchEvent.Type.GOAL;
                        continuePlay = false;
                        break;
                    case NEUTRAL:
                        type = MatchEvent.Type.SHOT;
                        continuePlay = true;
                        playerChange = true;
                        break;
                    case FAILURE, CRITICALFAIL:
                        type = MatchEvent.Type.SHOT;
                        continuePlay = false;
                        zoneChange = 5;
                        break;
                }
                break;
            case CROSS:
                switch (result) {
                    case CRITICAL, SUCCESS:
                        zoneChange = 1;
                        sideChange = 1;
                        continuePlay = true;
                        playerChange = true;
                        break;
                    case NEUTRAL, FAILURE:
                        zoneChange = 1;
                        sideChange = 1;
                        continuePlay = false;
                        break;
                    case CRITICALFAIL:
                        continuePlay = false;
                        break;
                }
                break;
            case DRIBBLE:
                switch (result) {
                    case CRITICAL, SUCCESS:
                        zoneChange = 1;
                        continuePlay = true;
                        break;
                    case NEUTRAL:
                        continuePlay = true;
                        type = MatchEvent.Type.FOUL;
                        break;
                    case FAILURE, CRITICALFAIL:
                        continuePlay = false;
                        break;
                }
                break;
            case PASS_DEEP:
                switch (result) {
                    case CRITICAL:
                        zoneChange = 2;
                        continuePlay = true;
                        playerChange = true;
                        break;
                    case SUCCESS:
                        zoneChange = 1;
                        continuePlay = true;
                        playerChange = true;
                    case NEUTRAL:
                        continuePlay = true;
                        break;
                    case FAILURE, CRITICALFAIL:
                        continuePlay = false;
                        break;
                }
                break;
            case PASS_FLAT:
                switch (result) {
                    case CRITICAL:
                        zoneChange = 1;
                        continuePlay = true;
                        playerChange = true;
                        break;
                    case SUCCESS:
                        sideChange = 1;
                        continuePlay = true;
                        playerChange = true;
                    case NEUTRAL:
                        continuePlay = true;
                        break;
                    case FAILURE, CRITICALFAIL:
                        continuePlay = false;
                        break;
                }
                break;
        }
        return new MatchStepEvent(sideChange, zoneChange, timeTaken, continuePlay, type, playerChange, result);

    }

    private static HistoryPlayer pickPlayer(List<HistoryPlayer> squad, PitchZone zone, PitchSide side,
            boolean reverse) {
        Map<HistoryPlayer, Double> weightedPlayers = new HashMap<>();
        if (reverse) {
            if (side.equals(PitchSide.LEFT)) {
                side = PitchSide.RIGHT;
            } else if (side.equals(PitchSide.RIGHT)) {
                side = PitchSide.LEFT;
            }
            if (zone.equals(PitchZone.OWN)) {
                zone = PitchZone.ENEMY;
            } else if (zone.equals(PitchZone.BOXOWN)) {
                zone = PitchZone.BOXENEMY;
            } else if (zone.equals(PitchZone.ENEMY)) {
                zone = PitchZone.OWN;
            } else if (zone.equals(PitchZone.BOXENEMY)) {
                zone = PitchZone.BOXOWN;
            }
        }
        for (HistoryPlayer player : squad) {
            weightedPlayers.put(player, getWeightForPositionAndZone(player.getPosition(), zone, side));
        }
        HistoryPlayer pickedPlayer = WeightedRandom.getRandomByWeight(weightedPlayers);
        return pickedPlayer;
    }

    private static double getWeightForPositionAndZone(Positions position, PitchZone zone, PitchSide side) {
        if (position.equals(Positions.GOALKEEPER)) {
            if (zone.equals(PitchZone.BOXOWN) && side.equals(PitchSide.CENTER)) {
                return 1D;
            } else {
                return 0D;
            }
        }
        if (position.equals(Positions.DEFENDER)) {
            switch (zone) {
                case BOXOWN:
                    return 0.2;
                case OWN:
                    return 1D;
                case CENTER:
                    return 0.5;
                case ENEMY:
                    return 0.2;
                case BOXENEMY:
                    return 0.1;
                default:
                    break;
            }
        }
        if (position.equals(Positions.MIDFIELDER)) {
            switch (zone) {
                case BOXOWN:
                    return 0.05;
                case OWN:
                    return 1D;
                case CENTER:
                    return 1D;
                case ENEMY:
                    return 1D;
                case BOXENEMY:
                    return 0.5;
                default:
                    break;
            }
        }
        if (position.equals(Positions.STRIKER)) {
            switch (zone) {
                case BOXOWN:
                    return 0.01;
                case OWN:
                    return 0.2;
                case CENTER:
                    return 0.5;
                case ENEMY:
                    return 0.8;
                case BOXENEMY:
                    return 1D;
                default:
                    break;
            }
        }
        return 1D;
    }

    private static PitchSide changePitchSide(PitchSide side, int change) {
        if (change == 1 && side.equals(PitchSide.CENTER)) {
            Random random = new Random();
            if (random.nextDouble() >= 0.5) {
                return PitchSide.RIGHT;
            } else {
                return PitchSide.LEFT;
            }
        }
        if (change == 2) {
            if (side.equals(PitchSide.LEFT)) {
                return PitchSide.RIGHT;
            } else {
                return PitchSide.LEFT;
            }
        } else {
            return side;
        }
    }

    private static PitchZone changePitchZone(PitchZone zone, int change) {
        int index = zone.ordinal() + change;
        if (index < 0)
            return PitchZone.values()[0];
        if (index >= PitchZone.values().length)
            return PitchZone.values()[PitchZone.values().length - 1];
        return PitchZone.values()[index];
    }

    private static double getEffectiveStrenghtForStepAndMintute(HistoryPlayer player, GameStep step, boolean active,
           MatchState state) {
        int baseSkill=player.getSkillLevel();
        if(player.getTeam().getId().equals(state.getHometeamId())){
            baseSkill*=FootmanConstants.HOME_ADVANTAGE;
        }
        if (!active)
            return baseSkill;
        switch (step) {
            case CROSS:
                return baseSkill * 0.9;
            case DRIBBLE:
            return baseSkill;
            case SHOOT:
            return baseSkill;
            case PASS_DEEP:
            return baseSkill*0.8;
            case PASS_FLAT:
            return baseSkill*1.3;
            default:
            return baseSkill;
        }
    }

    private static StepResult duell(double active, double passive) {
        Map<StepResult, Double> weightedResults = new HashMap<>();
        double baseRatioActive = active / (active + passive);
        double baseRatioPassive = passive / (active + passive);

        weightedResults.put(StepResult.CRITICAL, baseRatioActive * 0.05);
        weightedResults.put(StepResult.SUCCESS, baseRatioActive * 0.9);
        weightedResults.put(StepResult.NEUTRAL, baseRatioActive * 0.05 + baseRatioPassive * 0.05);
        weightedResults.put(StepResult.FAILURE, baseRatioPassive * 0.9);
        weightedResults.put(StepResult.CRITICALFAIL, baseRatioPassive * 0.05);
        return WeightedRandom.getRandomByWeight(weightedResults);
    }

    private static boolean maybeCard(HistoryPlayer player, Set<HistoryPlayer> bookedPlayers, List<MatchEvent> events,
            MatchState state) {
        if (RANDOM.nextDouble() < 0.01) {
            writeEvent(events, MatchEvent.Type.RED, player, null, state);
            return true;
        } else if (RANDOM.nextDouble() < 0.2) {
            if (bookedPlayers.contains(player)) {
                writeEvent(events, MatchEvent.Type.YELLOWRED, player, null, state);
                return true;
            } else {
                writeEvent(events, MatchEvent.Type.YELLOW, player, null, state);
                return true;
            }
        } else {
            return false;
        }
    }

    private static void writeEvent(List<MatchEvent> events, MatchEvent.Type nextEvent, HistoryPlayer activePlayer,
            HistoryPlayer passivePlayer, MatchState state) {
        int minute = state.getPlaytime() / 60 + 1;
        switch (nextEvent) {
            case YELLOW:
                events.add(new MatchEvent(minute, nextEvent, activePlayer,
                        String.format("Gelbe Karte für %s %s", activePlayer.getFirstName(),
                                activePlayer.getLastname())));
                break;
            case YELLOWRED:
                events.add(new MatchEvent(minute, nextEvent, activePlayer,
                        String.format("Gelb/Rote Karte für %s %s", activePlayer.getFirstName(),
                                activePlayer.getLastname())));
                break;
            case RED:
                events.add(new MatchEvent(minute, nextEvent, activePlayer,
                        String.format("Rote Karte für %s %s", activePlayer.getFirstName(),
                                activePlayer.getLastname())));
                break;
            case SHOT:
                events.add(new MatchEvent(minute, nextEvent, activePlayer,
                        String.format("Schussversuch für den %s durch %s %s - Leider daneben", activePlayer.getTeam().getName(), activePlayer.getFirstName(),
                                activePlayer.getLastname())));
                break;
            case GOAL:
                events.add(new MatchEvent(minute, nextEvent, activePlayer,
                        String.format("Tor für %s durch %s %s", activePlayer.getTeam().getName(),
                                activePlayer.getFirstName(), activePlayer.getLastname())));
                break;
            case HALFTIME:
                events.add(new MatchEvent(minute, nextEvent, activePlayer,
                        String.format("Halbzeit zum Stand von %s : %s", state.getGoalsHomeHalfTime(),  state.getGoalsAwayHalfTime())));
                break;
            case KICKOFF:
                events.add(new MatchEvent(minute, nextEvent, activePlayer,
                        String.format("Anstoß für %s", activePlayer.getTeam().getName())));
                break;
            case END:
                events.add(new MatchEvent(minute, nextEvent, activePlayer,
                        String.format("Abpfiff!!")));
                break;
            default:
                break;
        }
    }
}
