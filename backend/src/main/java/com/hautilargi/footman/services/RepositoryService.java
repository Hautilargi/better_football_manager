package com.hautilargi.footman.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hautilargi.footman.FootmanConstants;
import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.SquadRepository;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.players.repository.PlayerRepository;
import com.hautilargi.footman.util.Formations;
import com.hautilargi.footman.util.MatchTypes;
import com.hautilargi.footman.util.NameGenerator;

@Service
public class RepositoryService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    SquadRepository squadRepository;

    public RepositoryService() {
    }

    public Team addNewTeam(String name){
        Team newTeam = new Team();
        Random random=new Random();
        newTeam.setName(name);
        newTeam.setActive(true);
        newTeam.setBalance(FootmanConstants.INITIAL_BALANCE);
        List<Player> initialPlayers = new ArrayList<>();
        for(int i=0;i<FootmanConstants.INITIAL_ROSTER_SIZE;i++){
            Player newPlayer=new Player(NameGenerator.getLastName(), NameGenerator.getFirstName(), random.nextInt(FootmanConstants.INITIAL_PLAYERS_LOWER_BOUND,FootmanConstants.INITIAL_PLAYERS_UPPER_BOUND));
            initialPlayers.add(newPlayer);
            newPlayer.setTeam(newTeam);
        }
        newTeam.setPlayers(initialPlayers);
        Map<MatchTypes,Squad> defaultSquad=new HashMap<>();
        defaultSquad.put(MatchTypes.LEAGUE, new Squad(Formations.FOUR_FOUR_TWO, newTeam, newTeam.getPlayers().subList(0, 11)));
        newTeam.setSquads(defaultSquad);
        teamRepository.save(newTeam);
        return newTeam;
    }

    public List<Team> getAllTeams(){
        return teamRepository.findAll();
    }


    @Deprecated
    public Team addPlayerToTeam(Player player, Team team) {
        if(team.getPlayers().size()<FootmanConstants.MAX_TEAMSIZE) {
            player.setTeam(team);
            var newPlayers = team.getPlayers();
            newPlayers.add(player);
            team.setPlayers(newPlayers);
            System.out.println("Added player " + player.getFirstName() + " " + player.getLastName() + " to team " + team.getName());
            teamRepository.save(team);
            return team;
            } else {
            throw new IllegalStateException("Team has reached maximum size of " + FootmanConstants.MAX_TEAMSIZE);
         }
    }
}