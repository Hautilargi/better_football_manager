package com.hautilargi.footman.leagues.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@JsonIncludeProperties({"id","tier","index","seasonNo"})
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long seasonNo;

    /* 
    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Team> teams; 
    */

    private int tier;
    
    private int index;

    public League() {        
    }

    /* Getters and Setters */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSeasonNo() {
        return seasonNo;
    }

    public void setSeason(long seasonNo) {
        this.seasonNo = seasonNo;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }




}
