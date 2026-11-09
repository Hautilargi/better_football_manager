package com.hautilargi.footman.users.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.users.dto.UserDto;
import com.hautilargi.footman.users.model.LoginRequest;
import com.hautilargi.footman.users.model.RegistrationRequest;
import com.hautilargi.footman.users.model.User;
import com.hautilargi.footman.users.service.UserManagementService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserManagementService userManagementService;

    public AuthController(UserManagementService userManagementService) {
        this.userManagementService = userManagementService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {
        User user = userManagementService.authenticate(request.username(), request.password());
        userManagementService.startSession(httpRequest, user);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegistrationRequest request,
            HttpServletRequest httpRequest) {
        try {
            User user = userManagementService.registerUser(request.username(), request.email(),
                    request.password(), request.teamname());
            userManagementService.startSession(httpRequest, user);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            System.err.println(e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/me")
    public UserDto me(HttpServletRequest request) {
        return userManagementService.getUserDtoFromSession(request);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        request.getSession(false).invalidate();
    }
}
