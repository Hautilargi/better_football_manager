package com.hautilargi.footman;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.config.model.GlobalConfiguration;
import com.hautilargi.footman.debug.DebugHelperService;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.players.repository.PlayerRepository;
import com.hautilargi.footman.services.ConfigurationService;
import com.hautilargi.footman.services.MatchService;
import com.hautilargi.footman.util.Formations;
import com.hautilargi.footman.util.MatchTypes;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class Footman {

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    TeamRepository teamRepository;

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
            System.out.println("Created new base config");
        }
        System.out.println("Adding Samples to Database...");

        // playerRepository.deleteAllInBatch();
       // teamRepository.deleteAllInBatch();

        if(teamRepository.count()>0){
            System.out.println("Samples already exist, skipping sample data creation.");
            return;
        }
        // Generate Team A
        Team teamA = new Team("AC Alstaden 19");
        teamA.addPlayer(new Player("Smith", "John", 75));
        teamA.addPlayer(new Player("Müller", "Max", 68));
        teamA.addPlayer(new Player("Garcia", "Luis", 72));
        teamA.addPlayer(new Player("Rossi", "Marco", 64));
        teamA.addPlayer(new Player("Schmidt", "Lukas", 70));
        teamA.addPlayer(new Player("Dubois", "Pierre", 66));
        teamA.addPlayer(new Player("Silva", "Andre", 74));
        teamA.addPlayer(new Player("Kovač", "Ivan", 62));
        teamA.addPlayer(new Player("Nowak", "Piotr", 69));
        teamA.addPlayer(new Player("Hansen", "Erik", 71));
        teamA.addPlayer(new Player("Petrov", "Alexei", 67));
        Map<MatchTypes,Squad> newSquadsA=new HashMap<>();
        newSquadsA.put(MatchTypes.LEAGUE, new Squad(Formations.FOUR_FOUR_TWO, teamA, teamA.getPlayers().subList(0, 11)));
        teamA.setSquads(newSquadsA);

        // Generate Team B

        Team teamB = new Team("Kloppertruppe AC Alstaden Ost");
        teamB.addPlayer(new Player("Brown", "Michael", 30));
        teamB.addPlayer(new Player("Lopez", "Carlos", 20));
        teamB.addPlayer(new Player("Novak", "Tomas", 30));
        teamB.addPlayer(new Player("Keller", "Jan", 30));
        teamB.addPlayer(new Player("Moreau", "Julien", 30));
        teamB.addPlayer(new Player("Ibrahim", "Omar", 30));
        teamB.addPlayer(new Player("Santos", "Rafael", 71));
        teamB.addPlayer(new Player("Berg", "Anders", 69));
        teamB.addPlayer(new Player("Klein", "Daniel", 66));
        teamB.addPlayer(new Player("Popescu", "Andrei", 74));
        teamB.addPlayer(new Player("Conti", "Alessio", 61));
        Map<MatchTypes,Squad> newSquadsB=new HashMap<>();
        newSquadsB.put(MatchTypes.LEAGUE, new Squad(Formations.FOUR_FOUR_TWO, teamB, teamB.getPlayers().subList(0, 11)));
        teamB.setSquads(newSquadsB);

        teamRepository.save(teamA);
        teamRepository.save(teamB);



        System.out.println("Sample Teams created with IDs: " + teamA.getId() + " and " + teamB.getId());

        Match testMatch = matchService.playMatch(teamA, teamB, MatchTypes.LEAGUE, true);
  
        System.out.println("Single Test Match Result:"+testMatch.getGoalsAway()+" - "+testMatch.getGoalsHome());

         System.out.println("Bulk Match Result:"+debugHelperService.evaluateMatch(teamA, teamB, MatchTypes.LEAGUE, 1000));



    }
}