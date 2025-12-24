package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.leagues.repository.LeagueRepository;

//import com.hautilargi.footman.model.MatchRepository;

@RestController
public class Leagues {

   @Autowired
   LeagueRepository leagueRepository;

    @GetMapping("/api/leagues")
    public String getApiTeams(@RequestParam Long season, @RequestParam int tier) {
        return leagueRepository.findBySeasonNoAndTier(season,tier).toString();
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