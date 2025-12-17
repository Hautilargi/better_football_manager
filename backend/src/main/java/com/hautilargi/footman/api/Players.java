package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.model.Player;
import com.hautilargi.footman.model.PlayerRepository;

@RestController
public class Players {

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/api/players")
    public String getApiPlayers() {
        return playerRepository.findAll().toString();
    }

    @GetMapping("/api/players/{id}")
    public String getEmployeesById(@PathVariable Long id) {
        var player = playerRepository.findById(id);
        if (player.isPresent()) {
            return player.get().toString();
        } else {
            return "Player not found";
        }
    }
}