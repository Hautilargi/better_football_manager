package com.hautilargi.footman.matches.model;

import com.hautilargi.footman.clubs.model.Stadium;
import com.hautilargi.footman.leagues.model.League;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class AbstractMatch {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    @ManyToOne
    @JoinColumn(name = "league_id")
    public League league;

    @ManyToOne
    public Stadium venue;


    public AbstractMatch() {
    }

    public long getId() {
        return id;
    }
    public Stadium getVenue() {
        return venue;
    }
    public void setId(long id) {
        this.id = id;
    }
    public void setVenue(Stadium venue) {
        this.venue = venue;
    }

}