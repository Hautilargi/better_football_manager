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

    @GetMapping("/api/teams")
    public String getApiTeams() {
        return teamRepository.findAll().toString();
    }

    @GetMapping("/api/me/team")
    public String getMyTeam(HttpServletRequest request) {
    HttpSession session = request.getSession(false);
    if (session == null || session.getAttribute("USER_ID") == null) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
    Long userId = (Long) session.getAttribute("USER_ID");
    User user= userRepository.findById(userId).get();
    return teamRepository.findByUser(user).getFirst().getName();
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