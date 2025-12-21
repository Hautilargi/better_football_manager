package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.leagues.repository.LeagueRepository;
import com.hautilargi.footman.matches.repository.MatchRepository;

//import com.hautilargi.footman.model.MatchRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Leagues {

   @Autowired
   LeagueRepository leagueRepository;

    @GetMapping("/api/leagues")
    public String getApiTeams(@RequestParam Long season, @RequestParam int tier) {
        return leagueRepository.findBySeasonIdAndTier(season,tier).toString();
    }

    @GetMapping("/api/leagues/{id}")
    public String getTeamById(@PathVariable Long id) {
        
        var league = leagueRepository.findById(id);
        if (league.isPresent()) {
            return league.get().toString();
        } else {
            return "match not found";
        }
    }
}