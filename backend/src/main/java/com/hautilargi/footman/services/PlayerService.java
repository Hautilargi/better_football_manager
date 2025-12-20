package com.hautilargi.footman.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hautilargi.footman.FootmanConstants;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.players.model.Player;

@Service
public class PlayerService {

    @Autowired
    TeamRepository teamRepository;


    public PlayerService() {
    }

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