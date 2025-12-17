package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.core.MatchProcessor;
import com.hautilargi.footman.model.TeamRepository;

@RestController
public class Debug {
        @Autowired
        TeamRepository teamRepository;


        @GetMapping("/api/debug/testmatch")
        public String testMatch(@RequestParam Long team1, @RequestParam Long team2) {
            int[] result= MatchProcessor.processMatch(teamRepository.findById(team1).get(), teamRepository.findById(team2).get(), true);
            return String.format("Result: %d - %d", result[0], result[1]);
        }

}
