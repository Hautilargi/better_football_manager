package com.hautilargi.footman.matches.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.core.JsonViews;
import com.hautilargi.footman.core.util.StringUtils;
import com.hautilargi.footman.matches.service.MatchService;

@RestController
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/api/matches")
    public String getMatches(
        @RequestParam(required = true) long season,
        @RequestParam(required = true) int league,
        @RequestParam(required = true) int matchday) {

        //TODO make sure leageID gets passed correctly
        return StringUtils.toJson(matchService.getMatches(matchday, season, league), JsonViews.Matchview.class);
    }

    @GetMapping("/api/matches/{id}")
    public String getMatchById(@PathVariable Long id) {
        return matchService.findById(id)
                .map(match -> StringUtils.toJson(match, JsonViews.Matchview.class))
                .orElse("match not found");
    }
}
