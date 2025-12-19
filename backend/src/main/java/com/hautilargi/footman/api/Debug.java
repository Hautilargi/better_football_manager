package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.core.MatchProcessor;
import com.hautilargi.footman.model.matches.Match;
import com.hautilargi.footman.model.teams.Team;
import com.hautilargi.footman.model.teams.TeamRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Debug {
        @Autowired
        TeamRepository teamRepository;


        @GetMapping("/api/debug/testmatch")
        public String testMatch(@RequestParam Long homeId, @RequestParam Long awayId) {
            Team homeTeam =teamRepository.findById(homeId).get();
            Team awayTeam =teamRepository.findById(awayId).get();
            Match mr = MatchProcessor.processMatch(homeTeam, awayTeam);
            return mr.toString();
        }

}
