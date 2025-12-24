package com.hautilargi.footman.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.leagues.model.League;
import com.hautilargi.footman.leagues.repository.LeagueRepository;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.matches.repository.MatchRepository;
import com.hautilargi.footman.util.LeagueTable;
import com.hautilargi.footman.util.LeagueTableEntry;
import com.hautilargi.footman.util.MatchResults;
import com.hautilargi.footman.util.MatchTypes;

@Service
public class LeagueService {

    @Autowired
    LeagueRepository leagueRepository;

    @Autowired
    MatchRepository matchRepository;


    @Autowired
    RepositoryService rs;

    @Autowired
    ConfigurationService cs;

    @Autowired
    MatchService ms;


    public LeagueService() {
    }

    public void processSeasonChange(){
        
    }

    public League addLeague(long season, List<Team> teams, int tier, int index ){
        League newLeague = new League();
        newLeague.setSeason(season);
        newLeague.setTier(tier);
        newLeague.setIndex(index);
        while(teams.size()<18){
                teams.add(rs.addNewTeam("Placeholder "+ (teams.size()+1))); 
                System.out.println("Filling Team with Placeholder "+teams.size());
            }
        System.out.println(teams.size());
        //Generate Matches
        newLeague=leagueRepository.save(newLeague);
        System.out.println(String.format("Created new league %s in tier %s",index, tier)); 
        List<Match> leagueMatches = generateLeagueSchedule(teams, newLeague, season);
        for(Match match:leagueMatches){
            matchRepository.save(match);
        }
        System.out.println(String.format("Match schedule created for league %s/%s",tier, index)); 
       return newLeague;
    }

       private List<Match> generateLeagueSchedule(List<Team> teams, League league, long season) {
        if (teams.size() != 18) {
            throw new IllegalArgumentException("Exactly 18 Teams are needed. Something went wrong filling up with placeholders");
        }

        List<Match> matches = new ArrayList<>();
        int numberOfTeams = teams.size();
        int rounds = numberOfTeams - 1;
        int matchesPerRound = numberOfTeams / 2;

        for (int round = 0; round < rounds; round++) {
            for (int i = 0; i < matchesPerRound; i++) {
                Team home = teams.get(i);
                Team away = teams.get(numberOfTeams - 1 - i);
                Match newMatchFirstBracket=new Match(home,away,league,season,MatchTypes.LEAGUE,round+1);
                newMatchFirstBracket.setPlayed(false);
                Match newMatchSecondBracket=new Match(away,home,league,season,MatchTypes.LEAGUE,round+18);
                matches.add(newMatchFirstBracket);
                newMatchFirstBracket.setPlayed(false);
                matches.add(newMatchSecondBracket);
            }
            // Teams rotieren (erstes Team bleibt fix)
            teams.add(1, teams.remove(teams.size() - 1));
        }
        return matches;

    }

    public void playMatchDay(int matchday){
        //get current day
        System.out.println("Playing Matchday "+matchday +" in season "+cs.getGlobalConfiguration().getCurrentSeason() );
        //get all current leagues
        for(League league: leagueRepository.findBySeasonNo(cs.getGlobalConfiguration().getCurrentSeason())){
            System.out.println("Processing league "+league.getTier()+"/"+league.getIndex());
            for(Match match : getMatchesForLegueAndMatchday(league, matchday)){
                Match updatedMatch= ms.updateMatch(match);
                System.out.println(updatedMatch.getHomeTeam().getName()+ " "+updatedMatch.getGoalsHome()+" : "+updatedMatch.getGoalsHome()+ " "+updatedMatch.getAwayTeam().getName());
            }
            generateTableForLeague(league, cs.getGlobalConfiguration().getCurrentSeason(), matchday);
        }   
    }

    public void generateTableForLeague(League league, long season, int matchDay){
        LeagueTable table= new LeagueTable();
        Map<Long,LeagueTableEntry> teamToTables=new HashMap<>();
    
        for(int i=1;i<=matchDay;i++){
        List<Match> matchesOnDay= matchRepository.findByLeagueIdAndMatchDay(league.getId(), i);
        for(Match match:matchesOnDay){
             if(match.getHomeTeam()==null || match.getAwayTeam()==null){
                System.err.println("ERROR");
                break;
             }
             if(!teamToTables.containsKey(match.getHomeTeam().getId())){
                teamToTables.put(match.getHomeTeam().getId(), new LeagueTableEntry(match.getHomeTeam()));
                table.teamHinzufuegen(teamToTables.get(match.getHomeTeam().getId()));
             }
             if(!teamToTables.containsKey(match.getAwayTeam().getId())){
                teamToTables.put(match.getAwayTeam().getId(), new LeagueTableEntry(match.getAwayTeam()));
                table.teamHinzufuegen(teamToTables.get(match.getAwayTeam().getId()));
             }
             table.spielEintragen(teamToTables.get(match.getHomeTeam().getId()), teamToTables.get(match.getAwayTeam().getId()), match.getGoalsHome(),match.getGoalsAway());
        }
        }
        System.out.println("Table for league "+league.getTier()+"/"+league.getIndex());
       table.anzeigen();
    }

    public List<Match> getMatchesForLegueAndMatchday(League league, int matchday){
        return matchRepository.findByLeagueIdAndMatchDay(league.getId(), matchday);
    }

    public void replacePlaceHolderTeam(Team placeholder, Team realTeam){
        //Find
    }

    private MatchResults evaluateMatchForTeam(Match match, Team team){
                 MatchResults mr=MatchResults.UNDEFINED;
                 if(match.getGoalsHome()==match.getGoalsAway()) return MatchResults.DRAW;
                 if(match.getGoalsHome()>match.getGoalsAway()){
                    if (team.getId()==match.getHomeTeam().getId()){
                        return MatchResults.WIN;
                    }
                    else{
                        return MatchResults.LOSS;
                    }
                 }
                 if(match.getGoalsHome()<match.getGoalsAway()){
                    if (team.getId()==match.getHomeTeam().getId()){
                        return MatchResults.LOSS;
                    }
                    else{
                        return MatchResults.WIN;
                    }
                 } 
                 return mr;

    }
}