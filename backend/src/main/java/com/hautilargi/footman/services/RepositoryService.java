package com.hautilargi.footman.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hautilargi.footman.FootmanConstants;
import com.hautilargi.footman.clubs.dto.ClubDto;
import com.hautilargi.footman.clubs.mapper.ClubMapper;
import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.SquadRepository;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.core.service.ConfigurationService;
import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.players.repository.PlayerRepository;
import com.hautilargi.footman.util.Formations;
import com.hautilargi.footman.util.MatchTypes;
import com.hautilargi.footman.util.NameGenerator;
import com.hautilargi.footman.util.PlayerStatus;

@Service
public class RepositoryService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    SquadRepository squadRepository;

    @Autowired
    ConfigurationService cs;

    public RepositoryService() {
    }

    public Team addNewTeam(String name, int bias){
        Team newTeam = new Team();
        Random random=new Random();
        newTeam.setName(name);
        newTeam.setActive(true);
        newTeam.setBalance(FootmanConstants.INITIAL_BALANCE);
        newTeam.setTier(cs.getGlobalConfiguration().getLowestTier());
        List<Player> initialPlayers = new ArrayList<>();
        for(int i=0;i<FootmanConstants.INITIAL_ROSTER_SIZE;i++){
            int skill = random.nextInt(FootmanConstants.INITIAL_PLAYERS_LOWER_BOUND+bias,FootmanConstants.INITIAL_PLAYERS_UPPER_BOUND+bias);
            Player newPlayer=new Player(NameGenerator.getLastName(), NameGenerator.getFirstName(), skill);
            initialPlayers.add(newPlayer);
            newPlayer.setTeam(newTeam);
        }
        newTeam.setPlayers(initialPlayers);
        Map<MatchTypes,Squad> defaultSquad=new HashMap<>();
        defaultSquad.put(MatchTypes.LEAGUE, new Squad(Formations.FOUR_FOUR_TWO, newTeam, newTeam.getPlayers().subList(0, 11)));
        newTeam.setSquads(defaultSquad);
        teamRepository.save(newTeam);
        System.out.println("Created new Tea with a bias of "+bias);
        return newTeam;
    }

    public List<Team> getAllTeams(boolean onlyActive, int tier){
        if(tier==0 && onlyActive==false ){
                return teamRepository.findAll();
        }
        if(tier!=0 && onlyActive== false){
                return teamRepository.findByTier(tier);
        }
        if(tier!=0 && onlyActive== true){
                return teamRepository.findByTierAndActive(tier,onlyActive);
        }
        if(tier!=0 && onlyActive== true){
                return teamRepository.findByActive(onlyActive);
        }
        throw new UnsupportedOperationException("Unsupported combonation of Query Parameters");
    }

    public void relegateTeam(Team team){
        if(team.getTier()<cs.getGlobalConfiguration().getLowestTier()){
            team.setTier(team.getTier()+1);
            teamRepository.save(team);
        }
    }

    public void advanceTeam(Team team){
        if(team.getTier()!=1){
            team.setTier(team.getTier()-1);
            teamRepository.save(team); 
        }
    }

    public void deactivateTeam(Team team){
        team.setActive(false);
        teamRepository.save(team);
        List<Player> teamPlayers=team.getPlayers();
        for(Player player:teamPlayers){
            player.setPlayerStatus(PlayerStatus.RETIRED);
        }
        playerRepository.saveAll(teamPlayers);
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

    public ClubDto getClubById(Long id, ClubMapper mapper) {
        return teamRepository.findById(id)
                   .map(mapper::toDto)
                   .orElseThrow(() -> 
                       new RuntimeException("Team not found: " + id)
                   );
    }
}