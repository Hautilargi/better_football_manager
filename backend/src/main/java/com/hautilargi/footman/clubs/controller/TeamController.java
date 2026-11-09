package com.hautilargi.footman.clubs.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hautilargi.footman.clubs.dto.ClubBasicDto;
import com.hautilargi.footman.clubs.service.TeamService;
import com.hautilargi.footman.players.dto.RosterDto;
import com.hautilargi.footman.players.service.PlayerService;

@RestController
public class TeamController {

    private final TeamService teamService;
    private final PlayerService playerService;

    public TeamController(TeamService teamService, PlayerService playerService) {
        this.teamService = teamService;
        this.playerService = playerService;
    }

    @GetMapping("/api/teams")
    public List<ClubBasicDto> getTeams() {
        return teamService.getAllClubs();
    }

    @GetMapping("/api/teams/{id}")
    public ClubBasicDto getTeamById(@PathVariable Long id) {
        return teamService.getClubBasicById(id);
    }

    @GetMapping("/api/teams/{id}/roster")
    public RosterDto getTeamPlayers(@PathVariable Long id) {
        return teamService.findById(id)
                .map(playerService::getRosterForTeam)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }
}
