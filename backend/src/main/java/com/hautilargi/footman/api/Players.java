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

    @GetMapping("/api/players/html")
    public String getPlayersAsHtml() {
        var players = playerRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"de\">\n<head>\n<meta charset=\"utf-8\">\n<title>Players</title>\n<style>");
        sb.append("body{font-family:Arial,Helvetica,sans-serif;padding:20px;background:#fafafc;} .player{margin-bottom:12px;padding:10px;border:1px solid #e6e9ef;border-radius:6px;} h3{margin:0 0 6px 0;} p{margin:2px 0;}" );
        sb.append("</style>\n</head>\n<body>\n<h1>Players</h1>\n");

        for (var p : players) {
            // use fragment method
            try {
                sb.append(p.toHtmlFragment());
            } catch (Exception ex) {
                sb.append("<section class=\"player\"><p>Error rendering player id=").append(p.getId()).append("</p></section>\n");
            }
        }

        sb.append("</body>\n</html>");
        return sb.toString();
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