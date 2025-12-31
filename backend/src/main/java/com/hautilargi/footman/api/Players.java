package com.hautilargi.footman.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.players.dto.PlayerFullDto;
import com.hautilargi.footman.players.dto.PlayerSmallDto;
import com.hautilargi.footman.players.service.PlayerService;

@RestController
public class Players {


    @Autowired
    PlayerService playerService;

    @GetMapping("/api/players")
    public List<PlayerSmallDto> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping("/api/players/{id}")
    public PlayerFullDto getPlayerById(@PathVariable Long id) {
        var player = playerService.getPlayerAsDto(id);
        return player;
    }
}