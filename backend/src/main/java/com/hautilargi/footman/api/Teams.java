package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.players.dto.RosterDto;
import com.hautilargi.footman.players.service.PlayerService;
import com.hautilargi.footman.users.model.User;
import com.hautilargi.footman.users.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
public class Teams {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PlayerService playerService;

    @GetMapping("/api/teams")
    public String getApiTeams() {
        return teamRepository.findAll().toString();
    }

    @GetMapping("/api/teams/{id}")
    public String getTeamById(@PathVariable Long id) {
        var team = teamRepository.findById(id);
        if (team.isPresent()) {
            return team.get().toString();
        } else {
            return "team not found";
        }
    }

    @GetMapping("/api/teams/{id}/roster")
    public RosterDto getTeamPlayers(@PathVariable Long id) {
        var team = teamRepository.findById(id);
        if (team.isPresent()) {
            return playerService.getRosterForTeam(team.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }


}