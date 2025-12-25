package com.hautilargi.footman.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.core.JsonViews;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.matches.repository.MatchRepository;
import com.hautilargi.footman.services.ConfigurationService;
import com.hautilargi.footman.util.StringUtils;


//import com.hautilargi.footman.model.MatchRepository;

@RestController
public class Matches {

   @Autowired
   MatchRepository matchRepository;

   @Autowired
   ConfigurationService cs;

    @GetMapping("/api/matches")
    public String getApiTeams(
        @RequestParam(required = true) long season, 
        @RequestParam(required = true) int league, 
        @RequestParam(required = true) int matchday) {  
        
        //TODO make sure leageID gets passed correctly
        List<Match> matches= matchRepository.findByMatchDayAndSeasonAndLeague_Index(matchday,season,league);
        return StringUtils.toJson(matches,JsonViews.Matchview.class);
        
    }




    @GetMapping("/api/matches/{id}")
    public String getTeamById(@PathVariable Long id) {
        
        var match = matchRepository.findById(id);
        if (match.isPresent()) {
            return StringUtils.toJson(match.get(),JsonViews.Matchview.class);
        } else {
            return "match not found";
        }
    }
}