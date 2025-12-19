package com.hautilargi.footman.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

//import com.hautilargi.footman.model.MatchRepository;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class Matches {

   // @Autowired
   // MatchRepository matchRepository;

    @GetMapping("/api/matches")
    public String getApiTeams() {
        //return matchRepository.findAll().toString();
        return null;
    }

    @GetMapping("/api/matches/{id}")
    public String getTeamById(@PathVariable Long id) {
        /* 
        var match = matchRepository.findById(id);
        if (match.isPresent()) {
            return match.get().toString();
        } else {
            return "match not found";
        }
        */
       return null;
    }
}