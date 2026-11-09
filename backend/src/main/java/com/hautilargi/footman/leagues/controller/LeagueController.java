package com.hautilargi.footman.leagues.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.leagues.dto.LeagueDto;
import com.hautilargi.footman.leagues.dto.MatchDayTableDto;
import com.hautilargi.footman.leagues.service.LeagueService;

@RestController
public class LeagueController {

    private final LeagueService leagueService;

    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping("/api/leagues")
    public List<LeagueDto> getLeagues(@RequestParam Long season, @RequestParam int tier) {
        return leagueService.getLeagues(season, tier);
    }

    @GetMapping("/api/leagues/{id}")
    public LeagueDto getLeagueById(@PathVariable Long id) {
        return leagueService.getLeagueById(id);
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
