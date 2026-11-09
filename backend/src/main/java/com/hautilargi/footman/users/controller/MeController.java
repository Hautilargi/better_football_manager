package com.hautilargi.footman.users.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.clubs.dto.ClubDto;
import com.hautilargi.footman.clubs.service.TeamService;
import com.hautilargi.footman.players.dto.RosterDto;
import com.hautilargi.footman.players.service.PlayerService;
import com.hautilargi.footman.users.model.User;
import com.hautilargi.footman.users.service.UserManagementService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class MeController {

    private final TeamService teamService;
    private final PlayerService playerService;
    private final UserManagementService userManagementService;

    public MeController(TeamService teamService, PlayerService playerService,
            UserManagementService userManagementService) {
        this.teamService = teamService;
        this.playerService = playerService;
        this.userManagementService = userManagementService;
    }

    @GetMapping("/api/me/team")
    public ClubDto getMyTeam(HttpServletRequest request) {
        User user = userManagementService.getUserFromSession(request);
        return teamService.getClubOwnedBy(user);
    }

    @GetMapping("/api/me/roster")
    public RosterDto getMyRoster(HttpServletRequest request) {
        User user = userManagementService.getUserFromSession(request);
        return playerService.getRosterForTeam(user.getTeam());
    }

    @GetMapping("/api/me/league")
    public String getCurrentUserLeague(HttpServletRequest request) {
        User user = userManagementService.getUserFromSession(request);
        return teamService.getTeamOwnedBy(user).getName();
    }
}
