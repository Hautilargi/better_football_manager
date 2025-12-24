package com.hautilargi.footman.config.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GlobalConfiguration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private long currentSeason=0;
    private int currentDay=0;
    private int lowestTier=1;
    private String serverStatus="OK";
    private String mode="dev";

    public GlobalConfiguration(){
        
    }

    /* Getters and Setters */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public long getCurrentSeason() {
        return currentSeason;
    }

    public void setCurrentSeason(long currentSeason) {
        this.currentSeason = currentSeason;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(int currentDay) {
        this.currentDay = currentDay;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public int getLowestTier() {
        return this.lowestTier;
    }
    
    public void setLowestTier(int tier) {
        this.lowestTier =tier;
    }

    public void setServerStatus(String status) {
        this.serverStatus =status;
    }

    public String getServerStatus() {
        return this.serverStatus;
    }

    

}
