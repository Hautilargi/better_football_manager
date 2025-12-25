package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.core.dto.ClubDto;
import com.hautilargi.footman.core.mapper.ClubMapper;
import com.hautilargi.footman.players.dto.RosterDto;
import com.hautilargi.footman.players.service.PlayerService;
import com.hautilargi.footman.services.RepositoryService;
import com.hautilargi.footman.services.UserManagementService;
import com.hautilargi.footman.users.model.User;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class Me {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    PlayerService playerService;

    @Autowired
    private UserManagementService ums;

    @Autowired
    ClubMapper mapper;
    
    @Autowired
    private RepositoryService rs;
     
    @GetMapping("/api/me/team")
    public ClubDto getMyTeam(HttpServletRequest request) {
        User user = ums.getUserFromSession(request);
        Team team=  teamRepository.findByOwner(user).getFirst();
        return mapper.toDto(team);
    }

    @GetMapping("/api/me/roster")
    public RosterDto getMyRoster(HttpServletRequest request) {
        User user = ums.getUserFromSession(request);
        return playerService.getRosterForTeam(user.getTeam());
    }

    @GetMapping("api/me/league")
    public String getCurrentUserLeague(HttpServletRequest request) {
        User user = ums.getUserFromSession(request);
        return teamRepository.findByOwner(user).getFirst().getName();
    }
}
