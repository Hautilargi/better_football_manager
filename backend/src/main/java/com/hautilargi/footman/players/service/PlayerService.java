package com.hautilargi.footman.players.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hautilargi.footman.clubs.dto.ClubBasicDto;
import com.hautilargi.footman.clubs.model.HistorySquad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.core.util.Positions;
import com.hautilargi.footman.matches.model.MatchEvent;
import com.hautilargi.footman.players.dto.PlayerFullDto;
import com.hautilargi.footman.players.dto.PlayerSmallDto;
import com.hautilargi.footman.players.dto.PlayerStatsDto;
import com.hautilargi.footman.players.dto.RosterDto;
import com.hautilargi.footman.players.model.HistoryPlayer;
import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.players.model.PlayerStats;
import com.hautilargi.footman.players.repository.PlayerRepository;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

    // Helper Methods
    public void applyStatsUpdate(List<MatchEvent> events) {
        Set<Player> updatedPlayers = new HashSet<>();
        for (MatchEvent event : events) {
            if (event.getType().equals(MatchEvent.Type.RED)) {
                event.getPlayerActive().getRealPlayer().increaseReds();
            } else if (event.getType().equals(MatchEvent.Type.YELLOW)) {
                event.getPlayerActive().getRealPlayer().increseYellows();
            } else if (event.getType().equals(MatchEvent.Type.GOAL)) {
                event.getPlayerActive().getRealPlayer().increseGoals();
            }
        }
        playerRepository.saveAll(updatedPlayers);
    }

    //To be used when season changes
    public void resetStats(){
        List<Player> players= playerRepository.findAll();
        for(Player player:players){
            player.setStats(new PlayerStats());
        }
        playerRepository.saveAll(players);

    }

    public void increaseGamesForSquad(HistorySquad squad) {
        Set<Player> updatedPlayers = new HashSet<>();
        for (HistoryPlayer player : squad.getPlayers()) {
            player.getRealPlayer().increseGamesPlayed();
            updatedPlayers.add(player.getRealPlayer());
        }
        playerRepository.saveAll(updatedPlayers);
    }

    public int getEffectiveStrengthForPlayer(Player player, Positions position) {
        double staminaW, speedW, passingW, shootingW,
                defenseW, dribblingW, intelligenceW, goalkeepingW;

        switch (position) {
            case GOALKEEPER -> {
                staminaW = 0.05;
                speedW = 0.05;
                passingW = 0.05;
                shootingW = 0.00;
                defenseW = 0.15;
                dribblingW = 0.05;
                intelligenceW = 0.20;
                goalkeepingW = 0.45;
            }
            case DEFENDER -> {
                staminaW = 0.15;
                speedW = 0.15;
                passingW = 0.10;
                shootingW = 0.05;
                defenseW = 0.30;
                dribblingW = 0.05;
                intelligenceW = 0.20;
                goalkeepingW = 0.00;
            }
            case MIDFIELDER -> {
                staminaW = 0.20;
                speedW = 0.15;
                passingW = 0.25;
                shootingW = 0.10;
                defenseW = 0.10;
                dribblingW = 0.10;
                intelligenceW = 0.10;
                goalkeepingW = 0.00;
            }
            case STRIKER -> {
                staminaW = 0.10;
                speedW = 0.25;
                passingW = 0.10;
                shootingW = 0.30;
                defenseW = 0.05;
                dribblingW = 0.15;
                intelligenceW = 0.05;
                goalkeepingW = 0.00;
            }
            default -> throw new IllegalArgumentException("Unknown position");
        }

        Double effectiveStrenght = player.getStamina() * staminaW +
                player.getSpeed() * speedW +
                player.getPassing() * passingW +
                player.getShooting() * shootingW +
                player.getDefense() * defenseW +
                player.getDribbling() * dribblingW +
                player.getIntelligence() * intelligenceW +
                player.getGoalkeeping() * goalkeepingW;
        // Calculate Position Bias
        // Is 0% at a 100 and effective -20% at a 0
        double positionBias = 1 + (player.getPositionStrenght().get(position) - 100) * 0.2;
        return (int) Math.round(effectiveStrenght * positionBias);

    }

    // DTO Mapper Methods

    public RosterDto getRosterForTeam(Team team) {
        List<PlayerSmallDto> playerList = mapPlayerListToPlayerSmallDtoList(team.getPlayers());
        RosterDto dto = new RosterDto(team.getId(), playerList);
        return dto;
    }

    public PlayerStatsDto getStatsDtoForPlayer(Player player) {
        PlayerStats stats = player.getStats();
        return new PlayerStatsDto(stats.getPlayerStatus(), stats.getRemainingDaysForStatus(), stats.getGoals(),
                stats.getRed(), stats.getYellow(), stats.getYellowred(), stats.getGamesPlayed());
    }

    public PlayerFullDto getPlayerAsDto(long id) {
        var player = playerRepository.findById(id);
        var team = teamRepository.findByPlayers_id(id);
        if (player.isPresent()) {
            return mapPlayerToPlayerFullDto(player.get(), team);
        } else
            return null;
    }

    public List<PlayerSmallDto> getAllPlayers() {
        List<PlayerSmallDto> players = new ArrayList<>();
        for (Player player : playerRepository.findAll()) {
            players.add(mapPlayerToPlayerSmallDto(player));
        }
        return players;
    }

    // TO be moved to mapper
    private List<PlayerSmallDto> mapPlayerListToPlayerSmallDtoList(List<Player> players) {
        List<PlayerSmallDto> dtoList = new ArrayList<>();
        for (Player player : players) {
            dtoList.add(mapPlayerToPlayerSmallDto(player));
        }
        return dtoList;

    }

    private PlayerSmallDto mapPlayerToPlayerSmallDto(Player player) {
        return new PlayerSmallDto(player.getId(), player.getFirstName(), player.getLastname(),
                player.getNationality(), player.getSkillLevel(), player.getAge(),
                getPreferredPositionForPlayer(player));
    }

    private PlayerFullDto mapPlayerToPlayerFullDto(Player player, Team team) {
        return new PlayerFullDto(player.getId(), player.getFirstName(), player.getLastname(),
                player.getNationality(), player.getSalery(), player.getSkillLevel(), player.getTalent(),
                player.getAge(), player.getSpeed(), player.getStamina(), player.getPassing(),
                player.getShooting(), player.getDefense(), player.getDribbling(), player.getIntelligence(),
                player.getGoalkeeping(), getPreferredPositionForPlayer(player), getStatsDtoForPlayer(player),
                new ClubBasicDto(team.getId(), team.getName()));
    }

    public Positions getPreferredPositionForPlayer(Player player) {
        return Collections.max(player.getPositionStrenght().entrySet(), Map.Entry.comparingByValue()).getKey();
    }

    /*
     * public SquadDto getSquad(){
     * 
     * }
     */

}
