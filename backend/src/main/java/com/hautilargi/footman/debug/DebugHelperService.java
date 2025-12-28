package com.hautilargi.footman.debug;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.leagues.model.League;
import com.hautilargi.footman.leagues.service.LeagueService;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.services.ConfigurationService;
import com.hautilargi.footman.services.MatchService;
import com.hautilargi.footman.services.RepositoryService;
import com.hautilargi.footman.util.Formations;
import com.hautilargi.footman.util.MatchTypes;

@Service
public class DebugHelperService {

    @Autowired
    MatchService ms;

    @Autowired 
    RepositoryService rs;

    @Autowired
    LeagueService ls;

        @Autowired
    ConfigurationService cs;


    public DebugHelperService() {
    }

       public void generateSomeTeams(int no){
        for(int i =0; i<no;i++){
            rs.addNewTeam("TestTeam_"+i,0);
        }
   }
    
   public void generateSeasonAndLeague(){
        //18 Teams generieren
        List<Team> testTeams = new ArrayList<>();
        for(int i =0; i<16;i++){
            testTeams.add(rs.addNewTeam("TestTeam_"+i,0));
        }
        //Saison generieren
       long season= cs.increaseCurrentSeason();
       //Teams in Liga sortieren
       ls.addLeague(season, testTeams, 1,1);
   }

    public void generateAndSimulateTestSeasonAndLeage(){
        //18 Teams generieren
        List<Team> testTeams = new ArrayList<>();
        for(int i =0; i<16;i++){
            testTeams.add(rs.addNewTeam("TestTeam_"+i,0));
        }
        //Saison generieren
        long testSeason= cs.increaseCurrentSeason();
       //Teams in Liga sortieren
        League testLeague = ls.addLeague(testSeason, testTeams, 1,1);
        //Saison durchspielen
        for(int i =1;i<=34;i++){
            for(Match match : ls.getMatchesForLegueAndMatchday(testLeague, i)){
                Match updatedMatch= ms.updateMatch(match);
                System.out.println(updatedMatch.getHomeTeam().getName()+ " "+updatedMatch.getGoalsHome()+" : "+updatedMatch.getGoalsHome()+ " "+updatedMatch.getAwayTeam().getName());
            }
            //ls.playMatchDay();
        }
        ls.generateTableForLeague(testLeague,testSeason,34);
    }

    public String evaluateMatch(Team home, Team away, MatchTypes matchtype, int repetitions){
    long startTime = System.currentTimeMillis();

        int countHome=0;
        int countDraw=0;
        int countAway=0;
        Map<String, Integer> distribution=new HashMap<>();
        
        for(int i = 0 ; i<repetitions; i++){
                 Match mr= ms.playMatch(home, away, matchtype, false);
                 if(mr.getGoalsHome()>mr.getGoalsAway()) countHome++;
                 if(mr.getGoalsHome()==mr.getGoalsAway()) countDraw++;
                 if(mr.getGoalsHome()<mr.getGoalsAway()) countAway++;
                 String result=mr.getGoalsHome()+":"+mr.getGoalsAway();
                Integer current = distribution.get(result);
                if(current == null){
                    distribution.put(result, 1);
                } 
                else{
                    distribution.put(result, current + 1);
                }
        }
        long endTime = System.currentTimeMillis();
        StringBuilder sb= new StringBuilder();
        sb.append(String.format("Calculated %s games in %s ms \n",repetitions,endTime-startTime));
        sb.append(String.format("Siege Heim %s \n", countHome));
        sb.append(String.format("Unentschieden %s \n", countDraw));
        sb.append(String.format("Siege Auswärts %s \n", countAway));
        sb.append("Ergebnisverteilung: \n");
        Map<String, Integer> sortedMap = distribution.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,  // Merge function
                        LinkedHashMap::new));

        sortedMap.forEach((key, value) -> sb.append(key + " -> " + value + "\n"));
        return sb.toString();
    }

    public Team generateTestTeam(){
        Random random= new Random();    
        Team teamA = new Team("AC Alstaden "+random.nextInt(2025)); 
        for(int i=0; i<20;i++){
            teamA.addPlayer(new Player("Smith", "John", random.nextInt(40,80)));
        }
        Map<MatchTypes,Squad> newSquads=new HashMap<>();
        newSquads.put(MatchTypes.LEAGUE, new Squad(Formations.FOUR_FOUR_TWO, teamA, teamA.getPlayers().subList(0, 11)));
        teamA.setSquads(newSquads);
        return teamA;

    }
}
    
