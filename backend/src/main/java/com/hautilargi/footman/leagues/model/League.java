package com.hautilargi.footman.leagues.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "season_id")
    public Season season;

    /* 
    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Team> teams; 
    */

    private int tier;
    private int index;

    public League() {        
    }




}
