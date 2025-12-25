package com.hautilargi.footman.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.core.JsonViews;
import com.hautilargi.footman.leagues.dto.MatchDayTableDto;
import com.hautilargi.footman.leagues.repository.LeagueRepository;
import com.hautilargi.footman.leagues.service.LeagueService;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.util.StringUtils;

//import com.hautilargi.footman.model.MatchRepository;

@RestController
public class Leagues {

   @Autowired
   LeagueRepository leagueRepository;

   @Autowired
   LeagueService leagueService;

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

    @GetMapping("/api/leagues/table")
    public MatchDayTableDto getTableForMatchday(
        @RequestParam(required = true) long season, 
        @RequestParam(required = true) int league, 
        @RequestParam(required = true) int matchday) {  
        
        //TODO make sure leageID gets passed correctly
        return leagueService.getTableForMatchDay(season, league, matchday);
        
    }



}