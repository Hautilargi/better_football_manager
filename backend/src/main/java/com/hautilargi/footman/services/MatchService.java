package com.hautilargi.footman.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hautilargi.footman.clubs.model.HistorySquad;
import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.clubs.repository.HistorySquadRepository;
import com.hautilargi.footman.core.MatchProcessor;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.matches.model.MatchEvent;
import com.hautilargi.footman.matches.repository.MatchRepository;
import com.hautilargi.footman.players.model.HistoryPlayer;
import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.util.MatchTypes;

@Service
public class MatchService {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    HistorySquadRepository hsRepo ;


    public MatchService() {
    }

    public Match playMatch(Team home, Team away, MatchTypes matchType, boolean persist) {

        HistorySquad homeHistorySquad = createHistorySquad(home.getSquads().get(matchType));
        HistorySquad awayHistorySquad = createHistorySquad(away.getSquads().get(matchType));

        Match match = MatchProcessor.processMatch(home, away, homeHistorySquad, awayHistorySquad);
    
        if(persist){
            matchRepository.save(match);
            System.out.println("Saved match with id: "+match.getId());
        }
        return match;
    }

        public Match updateMatch(Match match) {

        HistorySquad homeHistorySquad = createHistorySquad(match.getHomeTeam().getSquads().get(match.getMatchtype()));
        HistorySquad awayHistorySquad = createHistorySquad(match.getAwayTeam().getSquads().get(match.getMatchtype()));
        Match processedMatch = MatchProcessor.processMatch(match.getHomeTeam(), match.getAwayTeam(), homeHistorySquad, awayHistorySquad);
        List<MatchEvent> events= processedMatch.getEvents();
        for(MatchEvent event:events){
            event.setMatch(match);
        }
        match.setEvents(processedMatch.getEvents());
        match.setGoalsAway(processedMatch.getGoalsAway());
        match.setGoalsHome(processedMatch.getGoalsHome());
        match.setPlayed(true);
        matchRepository.save(match);   
        return match;
    }

    private HistorySquad createHistorySquad(Squad squad) {
        HistorySquad hs = new HistorySquad();  
        ArrayList<HistoryPlayer> players = new ArrayList<>(); 
        hs.setFormation(squad.getFormation());
        for (Player p : squad.getSquadMembers()){ 
            HistoryPlayer hp = new HistoryPlayer(p.getLastName(), p.getFirstName(), getAvarageForPosition(p));
            hp.setTeam(squad.getTeam());
            hp.setRealPlayer(p);
            players.add(hp);
        }
        hs.setPlayers(players);
        return hs;
    }

    private int getAvarageForPosition(Player p) {
            return p.getSkillLevel();
    }
}