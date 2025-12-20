package com.hautilargi.footman.debug;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.leagues.model.League;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.services.MatchService;
import com.hautilargi.footman.util.Formations;

@Service
public class DebugHelperService {

    @Autowired
    MatchService ms;


    public DebugHelperService() {
    }
    


    public String evaluateMatch(Team home, Team away, int repetitions){
    long startTime = System.currentTimeMillis();

        int countHome=0;
        int countDraw=0;
        int countAway=0;
        Map<String, Integer> distribution=new HashMap<>();
        
        for(int i = 0 ; i<repetitions; i++){
                 Match mr= ms.playMatch(home, away, null, false);
                 if(mr.goalsHome>mr.goalsAway) countHome++;
                 if(mr.goalsHome==mr.goalsAway) countDraw++;
                 if(mr.goalsHome<mr.goalsAway) countAway++;
                 String result=mr.goalsHome+":"+mr.goalsAway;
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
        teamA.setSquad(new Squad(Formations.FOUR_FOUR_TWO, teamA, teamA.getPlayers().subList(0, 11)));
        return teamA;

    }

    public League generateLeague(){
        League newLeague = new League();
        return newLeague;
    }
}
    
