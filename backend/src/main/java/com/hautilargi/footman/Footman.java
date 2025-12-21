package com.hautilargi.footman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.config.model.GlobalConfiguration;
import com.hautilargi.footman.debug.DebugHelperService;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.services.ConfigurationService;
import com.hautilargi.footman.services.MatchService;
import com.hautilargi.footman.services.RepositoryService;
import com.hautilargi.footman.util.MatchTypes;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class Footman {

    @Autowired
    RepositoryService rs;

    @Autowired
    ConfigurationService cs;

    @Autowired
    MatchService matchService;
    
    @Autowired
    DebugHelperService debugHelperService;

    public static void main(String[] args) {
        SpringApplication.run(Footman.class, args);
    }

    @PostConstruct
    public void initializeApp() {
        System.out.println("Application started...");
        if(cs.getGlobalConfiguration()==null){
            cs.setGlobalConfiguration(new GlobalConfiguration());
            cs.setCurrentDay(40);
            System.out.println("Created new base config");
        }
        System.out.println("Adding Samples to Database...");
        if(rs.getAllTeams(false,0).size()>0){
            System.out.println("Samples already exist, skipping sample data creation.");
            return;
        }
        Team teamA = rs.addNewTeam("AC Alstaden 19");
        Team teamB = rs.addNewTeam("Kloppertruppe AC Alstaden Ost");
        debugHelperService.generateSomeTeams(22);
        System.out.println("Sample Teams created with IDs: " + teamA.getId() + " and " + teamB.getId());
        System.out.println("Bulk Match Result:"+debugHelperService.evaluateMatch(teamA, teamB, MatchTypes.LEAGUE, 1000));
        
        //debugHelperService.generateAndSimulateTestSeasonAndLeage();
        //debugHelperService.generateSeasonAndLeague();


    }
}