package com.hautilargi.footman;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.TeamRepository;
import com.hautilargi.footman.config.model.GlobalConfiguration;
import com.hautilargi.footman.debug.DebugHelperService;
import com.hautilargi.footman.services.ConfigurationService;
import com.hautilargi.footman.services.MatchService;
import com.hautilargi.footman.services.RepositoryService;
import com.hautilargi.footman.users.model.User;
import com.hautilargi.footman.users.repository.UserRepository;
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(Footman.class, args);
    }

    @PostConstruct
    public void initializeApp() {
        System.out.println("Application started...");
        if (cs.getGlobalConfiguration() == null) {
            cs.setGlobalConfiguration(new GlobalConfiguration());
            cs.setCurrentDay(40);
            System.out.println("Created new base config");
            System.out.println("Adding Samples to Database...");
            Team teamA = rs.addNewTeam("AC Alstaden 19",10);
            Team teamB = rs.addNewTeam("Kloppertruppe AC Alstaden Ost",15);
            debugHelperService.generateSomeTeams(14);
            System.out.println("Sample Teams created with IDs: " + teamA.getId() + " and " + teamB.getId());
            System.out.println(
                    "Bulk Match Result:" + debugHelperService.evaluateMatch(teamA, teamB, MatchTypes.LEAGUE, 1000));
            User user= new User("admin","admin@hautilargi.de",passwordEncoder.encode("admin"),null);
            User user2= new User("papa","test@hautilargi.de",passwordEncoder.encode("papa"),null);

            userRepository.save(user);
            userRepository.save(user2);
            teamA.setOwner(user);
            teamB.setOwner(user2);
            teamRepository.save(teamA);
            teamRepository.save(teamB);
            user.setTeam(teamA);
            user2.setTeam(teamB);
            userRepository.save(user);
            userRepository.save(user2);
        }
        else{
            System.out.println(String.format("App config found. Starting as usual on season %s and day %s",cs.getGlobalConfiguration().getCurrentSeason(),cs.getGlobalConfiguration().getCurrentDay()));
        }
    }
}