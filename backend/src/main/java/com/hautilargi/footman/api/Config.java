package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.leagues.repository.LeagueRepository;
import com.hautilargi.footman.matches.repository.MatchRepository;
import com.hautilargi.footman.services.ConfigurationService;

//import com.hautilargi.footman.model.MatchRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Config {

   @Autowired
   ConfigurationService cs;

    @GetMapping("/api/config/status")
    public String getApiTeams(@RequestParam String status) {
        try {
            cs.setStatus(status);
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }

        @GetMapping("/api/config/sleep")
    public String getApiTeams() {
        try {
            cs.setStatus("SLEEP");
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }

    @GetMapping("/api/config/wake")
    public String getWake() {
        try {
            cs.setStatus("OK");
        } catch (Exception e) {
            return e.getMessage();
        }
        return "OK";
    }

}