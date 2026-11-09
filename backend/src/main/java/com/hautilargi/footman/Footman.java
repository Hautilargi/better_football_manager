package com.hautilargi.footman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.service.TeamService;
import com.hautilargi.footman.config.model.GlobalConfiguration;
import com.hautilargi.footman.core.processing.MatchTypes;
import com.hautilargi.footman.core.service.ConfigurationService;
import com.hautilargi.footman.debug.DebugHelperService;
import com.hautilargi.footman.matches.service.MatchService;
import com.hautilargi.footman.users.model.User;
import com.hautilargi.footman.users.service.UserManagementService;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
public class Footman {

    private final TeamService teamService;
    private final ConfigurationService cs;
    private final MatchService matchService;
    private final DebugHelperService debugHelperService;
    private final UserManagementService userManagementService;
    private final PasswordEncoder passwordEncoder;

    public Footman(TeamService teamService, ConfigurationService cs, MatchService matchService,
            DebugHelperService debugHelperService, UserManagementService userManagementService,
            PasswordEncoder passwordEncoder) {
        this.teamService = teamService;
        this.cs = cs;
        this.matchService = matchService;
        this.debugHelperService = debugHelperService;
        this.userManagementService = userManagementService;
        this.passwordEncoder = passwordEncoder;
    }

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
            Team teamA = teamService.addNewTeam("AC Alstaden 19",25);
            Team teamB = teamService.addNewTeam("Kloppertruppe AC Alstaden Ost",25);
            debugHelperService.generateSomeTeams(14);
            System.out.println("Sample Teams created with IDs: " + teamA.getId() + " and " + teamB.getId());
            System.out.println(
                    "Bulk Match Result:" + debugHelperService.evaluateMatch(teamA, teamB, MatchTypes.LEAGUE, 1000));
            User user= new User("admin","admin@hautilargi.de",passwordEncoder.encode("admin"),null);
            User user2= new User("papa","test@hautilargi.de",passwordEncoder.encode("papa"),null);

            userManagementService.save(user);
            userManagementService.save(user2);
            teamA.setOwner(user);
            teamB.setOwner(user2);
            teamService.save(teamA);
            teamService.save(teamB);
            user.setTeam(teamA);
            user2.setTeam(teamB);
            userManagementService.save(user);
            userManagementService.save(user2);
        }
        else{
            System.out.println(String.format("App config found. Starting as usual on season %s and day %s",cs.getGlobalConfiguration().getCurrentSeason(),cs.getGlobalConfiguration().getCurrentDay()));
        }
    }
}