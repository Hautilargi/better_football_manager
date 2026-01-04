package com.hautilargi.footman.core.processing;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hautilargi.footman.core.processing.ComplexMatchProcessor.GamePhase;
import com.hautilargi.footman.core.processing.ComplexMatchProcessor.PitchSide;
import com.hautilargi.footman.core.processing.ComplexMatchProcessor.PitchZone;
import com.hautilargi.footman.players.model.HistoryPlayer;

public class MatchState {
        Set<HistoryPlayer> bookedPlayers = new HashSet<>();
        Set<HistoryPlayer> suHistoryPlayers = new HashSet<>();

        HistoryPlayer activePlayer;
        PitchZone zone = PitchZone.CENTER;
        PitchSide side = PitchSide.CENTER;
        GamePhase phase = GamePhase.FIRST;
        List<HistoryPlayer> activeTeam ;
        List<HistoryPlayer> passiveTeam ;
        long hometeamId;
        double momentum=0D;
        int goalsHome = 0;
        int goalsAway = 0;
        int goalsHomeHalfTime = 0;
        int goalsAwayHalfTime = 0;
        int playtime = 0;


        public MatchState(){
            
        }
        

        public Set<HistoryPlayer> getBookedPlayers() {
            return bookedPlayers;
        }
        public void setBookedPlayers(Set<HistoryPlayer> bookedPlayers) {
            this.bookedPlayers = bookedPlayers;
        }
        public HistoryPlayer getActivePlayer() {
            return activePlayer;
        }
        public void setActivePlayer(HistoryPlayer activePlayer) {
            this.activePlayer = activePlayer;
        }
        public PitchZone getZone() {
            return zone;
        }
        public void setZone(PitchZone zone) {
            this.zone = zone;
        }
        public PitchSide getSide() {
            return side;
        }
        public void setSide(PitchSide side) {
            this.side = side;
        }
        public GamePhase getPhase() {
            return phase;
        }
        public void setPhase(GamePhase phase) {
            this.phase = phase;
        }
        public List<HistoryPlayer> getActiveTeam() {
            return activeTeam;
        }
        public void setActiveTeam(List<HistoryPlayer> activeTeam) {
            this.activeTeam = activeTeam;
        }
        public List<HistoryPlayer> getPassiveTeam() {
            return passiveTeam;
        }
        public void setPassiveTeam(List<HistoryPlayer> passiveTeam) {
            this.passiveTeam = passiveTeam;
        }
        public long getHometeamId() {
            return hometeamId;
        }
        public void setHometeamId(long hometeamId) {
            this.hometeamId = hometeamId;
        }
        public int getGoalsHome() {
            return goalsHome;
        }
        public void setGoalsHome(int goalsHome) {
            this.goalsHome = goalsHome;
        }
        public int getGoalsAway() {
            return goalsAway;
        }
        public void setGoalsAway(int goalsAway) {
            this.goalsAway = goalsAway;
        }
        public int getGoalsHomeHalfTime() {
            return goalsHomeHalfTime;
        }
        public void setGoalsHomeHalfTime(int goalsHomeHalfTime) {
            this.goalsHomeHalfTime = goalsHomeHalfTime;
        }
        public int getGoalsAwayHalfTime() {
            return goalsAwayHalfTime;
        }
        public void setGoalsAwayHalfTime(int goalsAwayHalfTime) {
            this.goalsAwayHalfTime = goalsAwayHalfTime;
        }
        public int getPlaytime() {
            return playtime;
        }
        public void setPlaytime(int playtime) {
            this.playtime = playtime;
        }

    

        
    }


    


 
