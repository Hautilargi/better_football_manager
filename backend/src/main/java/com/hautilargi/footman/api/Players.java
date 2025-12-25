package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.players.dto.PlayerFullDto;
import com.hautilargi.footman.players.repository.PlayerRepository;
import com.hautilargi.footman.players.service.PlayerService;

@RestController
public class Players {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerService playerService;

    @GetMapping("/api/players")
    public String getApiPlayers() {
        return playerRepository.findAll().toString();
    }

    @GetMapping("/api/players/{id}")
    public PlayerFullDto getPlayerById(@PathVariable Long id) {
        var player = playerService.getPlayerAsDto(id);
        return player;
    }
}