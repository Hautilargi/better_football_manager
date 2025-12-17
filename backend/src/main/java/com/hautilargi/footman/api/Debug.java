package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hautilargi.footman.core.MatchProcessor;
import com.hautilargi.footman.model.MatchEvent;
import com.hautilargi.footman.model.MatchResult;
import com.hautilargi.footman.model.Team;
import com.hautilargi.footman.model.TeamRepository;

@RestController
public class Debug {
        @Autowired
        TeamRepository teamRepository;


        @GetMapping("/api/debug/testmatch")
        public String testMatch(@RequestParam Long homeId, @RequestParam Long awayId) {
            Team homeTeam =teamRepository.findById(homeId).get();
            Team awayTeam =teamRepository.findById(awayId).get();
            MatchResult mr = MatchProcessor.processMatch(homeTeam, awayTeam);
            StringBuffer sb = new StringBuffer();
            sb.append(String.format("%s %s - %s %s <br/><br/>", mr.home.getName(), mr.goalsHome, mr.goalsAway, mr.away.getName()));
            sb.append("Spielbericht: <br/><br/>");
            for(MatchEvent event: mr.events){
                sb.append("<br/>");
                sb.append(event.toString());
            }
            return sb.toString();
        }

}
