package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.services.UserManagementService;
import com.hautilargi.footman.users.model.User;
import com.hautilargi.footman.users.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class Me {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private UserManagementService ums;

    @GetMapping("/api/me/team")
    public String getMyTeam(HttpServletRequest request) {
        User user = ums.getUserFromSession(request);
        return teamRepository.findByOwner(user).getFirst().getName();
    }

    @GetMapping("/api/me/squad")
    public String getMySquad(HttpServletRequest request) {
        User user = ums.getUserFromSession(request);
        return teamRepository.findByOwner(user).getFirst().getName();
    }

    @GetMapping("api/me/league")
    public String getCurrentUserLeague(HttpServletRequest request) {
        User user = ums.getUserFromSession(request);
        return teamRepository.findByOwner(user).getFirst().getName();
    }
}
