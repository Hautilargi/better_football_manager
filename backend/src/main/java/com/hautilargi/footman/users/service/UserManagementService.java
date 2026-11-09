package com.hautilargi.footman.users.service;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.service.TeamService;
import com.hautilargi.footman.users.dto.UserDto;
import com.hautilargi.footman.users.mapper.UserMapper;
import com.hautilargi.footman.users.model.User;
import com.hautilargi.footman.users.repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class UserManagementService {

    private static final String SESSION_USER_ID = "USER_ID";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TeamService teamService;
    private final UserMapper userMapper;

    public UserManagementService(UserRepository userRepository, PasswordEncoder passwordEncoder,
            TeamService teamService, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.teamService = teamService;
        this.userMapper = userMapper;
    }

    public User registerUser(String name, String email, String password, String teamName) {

        User user = new User(name, email, passwordEncoder.encode(password), null);
        Team newTeam = teamService.addNewTeam(teamName,0);
        user.setTeam(newTeam);
        userRepository.save(user);

        newTeam.setOwner(user);
        newTeam.setActive(true);
        teamService.save(newTeam);
        return user;

    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User authenticate(String username, String password) {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED));
        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Password wrong!");
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return user;
    }

    public void startSession(HttpServletRequest request, User user) {
        request.getSession(true).setAttribute(SESSION_USER_ID, user.getId());
    }

    public UserDto getUserDtoFromSession(HttpServletRequest request) {
        return userMapper.toDto(getUserFromSession(request));
    }

    public User getUserFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SESSION_USER_ID) == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        Long userId = (Long) session.getAttribute(SESSION_USER_ID);
        Optional<User> maybeUser = userRepository.findById(userId);
        if (maybeUser.isPresent()) {
            return maybeUser.get();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }

    public long getUserIdFromSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SESSION_USER_ID) == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return (Long) session.getAttribute(SESSION_USER_ID);
    }

}
