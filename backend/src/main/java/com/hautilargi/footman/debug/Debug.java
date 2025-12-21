package com.hautilargi.footman.debug;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.HistorySquadRepository;
import com.hautilargi.footman.clubs.repository.SquadRepository;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.services.MatchService;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Debug {
        @Autowired
        TeamRepository teamRepository;
        @Autowired
        SquadRepository sr;
        @Autowired
        HistorySquadRepository hsRepo ;
        @Autowired
        MatchService matchService;

        @GetMapping("/api/debug/testmatch")
        public String testMatch(@RequestParam Long homeId, @RequestParam Long awayId) {
            Team homeTeam =teamRepository.findById(homeId).get();
            Team awayTeam =teamRepository.findById(awayId).get();
            Match mr = matchService.playMatch(homeTeam, awayTeam, null,false);
            return mr.toString();
        }

        @GetMapping("/api/debug/squads")
        public String squads() {
            List<Squad> sqaids = sr.findAll();
            for (Squad s : sqaids) {
                System.out.println("Squad id="+s.getId()+" has "+s.getSquadMembers().size()+" players.");
            }
            return sqaids.toString();
        }


}
