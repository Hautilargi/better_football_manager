package com.hautilargi.footman.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.TeamRepository;

import com.hautilargi.footman.users.model.User;
import com.hautilargi.footman.users.repository.UserRepository;


@Service
public class UserManagementService {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

     @Autowired
     PasswordEncoder passwordEncoder;

    @Autowired
    RepositoryService rs;

    public UserManagementService() {
    }

    public User registerUser(String name, String email, String password, String teamName){

        User user = new User(name, email, passwordEncoder.encode(password), null);
        Team newTeam = rs.addNewTeam(teamName);
        user.setTeam(newTeam);
        userRepository.save(user);

        newTeam.setOwner(user);
        newTeam.setActive(true);
        teamRepository.save(newTeam);
        return user;
        
        
    }
}