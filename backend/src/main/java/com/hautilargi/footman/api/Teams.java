package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.TeamRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Teams {

    @Autowired
    TeamRepository teamRepository;

    @GetMapping("/api/teams")
    public String getApiTeams() {
        return teamRepository.findAll().toString();
    }

    @GetMapping("/api/teams/{id}")
    public String getTeamById(@PathVariable Long id) {
        var team = teamRepository.findById(id);
        if (team.isPresent()) {
            return team.get().toString();
        } else {
            return "team not found";
        }
    }

    @GetMapping("/api/teams/{id}/players")
    public String getTeamPlayers(@PathVariable Long id) {
        var team = teamRepository.findById(id);
        if (team.isPresent()) {
            return team.get().getPlayers().toString();
        } else {
            return "team not found";
        }
    }

    @GetMapping("/api/teams/html")
    public String getTeamsAsHtml() {
        var teams = teamRepository.findAll();
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html>\n");
        sb.append("<html lang=\"de\">\n<head>\n<meta charset=\"utf-8\">\n<title>Teams</title>\n<style>");
        sb.append("body{font-family:Arial,Helvetica,sans-serif;padding:20px;background:#f7f7fb;} .team{margin-bottom:18px;padding:12px;border:1px solid #e1e4ea;border-radius:6px;} ul{list-style:none;padding-left:0;margin:8px 0 0 0;} li{padding:4px 0;border-bottom:1px solid #eee;}");
        sb.append("</style>\n</head>\n<body>\n<h1>Teams</h1>\n");

        for (Team t : teams) {
            sb.append(t.toHtmlString());
        }

        sb.append("</body>\n</html>");
        return sb.toString();
    }
}