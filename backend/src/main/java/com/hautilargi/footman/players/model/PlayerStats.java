package com.hautilargi.footman.players.model;

import java.util.HashSet;
import java.util.Set;

import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.util.PlayerStatus;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@jakarta.persistence.Entity
public class PlayerStats {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    private PlayerStatus playerStatus;
    private int remainingDaysForStatus=0;
    private int goals;
    private int red;
    private int yellow;
    private int yellowred;
    private int gamesPlayed;

    public PlayerStats() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public int getRemainingDaysForStatus() {
        return remainingDaysForStatus;
    }

    public void setRemainingDaysForStatus(int remainingDaysForStatus) {
        this.remainingDaysForStatus = remainingDaysForStatus;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public int getYellow() {
        return yellow;
    }

    public void setYellow(int yellow) {
        this.yellow = yellow;
    }

    public int getYellowred() {
        return yellowred;
    }

    public void setYellowred(int yellowred) {
        this.yellowred = yellowred;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }


    
}
