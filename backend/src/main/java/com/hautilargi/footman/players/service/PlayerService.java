package com.hautilargi.footman.players.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hautilargi.footman.clubs.dto.ClubBasicDto;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.players.dto.PlayerFullDto;
import com.hautilargi.footman.players.dto.PlayerSmallDto;
import com.hautilargi.footman.players.dto.RosterDto;
import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.players.repository.PlayerRepository;

@Service
public class PlayerService {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

    public RosterDto getRosterForTeam(Team team){
        List<PlayerSmallDto> playerList = mapPlayerListToPlayerSmallDtoList(team.getPlayers());
        RosterDto dto = new RosterDto(team.getId(),playerList);
        return dto;
    }
    public PlayerFullDto getPlayerAsDto(long id){
        var player = playerRepository.findById(id);
        var team = teamRepository.findByPlayers_id(id);
        if(player.isPresent()){
            return mapPlayerToPlayerFullDto(player.get(),team);
        }
        else return null;
    }

    /* 
    public SquadDto getSquad(){

    }
    */

    //TO be moved to mapper
    private List<PlayerSmallDto> mapPlayerListToPlayerSmallDtoList(List<Player> players){
        List<PlayerSmallDto> dtoList = new ArrayList<>();
        for(Player player:players){
            dtoList.add(mapPlayerToPlayerSmallDto(player));
        }
        return dtoList;

    }
    private PlayerSmallDto mapPlayerToPlayerSmallDto(Player player){
        return new PlayerSmallDto(player.getId(),player.getFirstName(),player.getLastname(), 
                                  player.getNationality(),player.getSkillLevel(),player.getAge());
    }

    private PlayerFullDto mapPlayerToPlayerFullDto(Player player, Team team){
        return new PlayerFullDto(player.getId(),player.getFirstName(),player.getLastname(),
                                 player.getNationality(),player.getSalery(),player.getSkillLevel(),
                                 player.getAge(), player.getSpeed(),player.getStamina(),player.getPassing(),
                                 player.getShooting(),player.getDefense(),player.getDribbling(),
                                 new ClubBasicDto(team.getId(), team.getName()));
    }


}
