package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import com.hautilargi.footman.model.TeamRepository;

@RestController
public class Teams {

    @Autowired
    TeamRepository teamRepository;

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

    @GetMapping("/api/teams/{id}/players")
    public String getTeamPlayers(@PathVariable Long id) {
        var team = teamRepository.findById(id);
        if (team.isPresent()) {
            return team.get().getPlayers().toString();
        } else {
            return "team not found";
        }
    }
}