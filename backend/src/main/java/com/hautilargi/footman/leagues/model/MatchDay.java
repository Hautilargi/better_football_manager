package com.hautilargi.footman.leagues.model;

import java.util.List;

import com.hautilargi.footman.matches.model.AbstractMatch;
import com.hautilargi.footman.matches.model.Match;
import com.hautilargi.footman.players.model.AbstractPlayer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class MatchDay {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "league_id")
    public League league;

    private int dayInSeason;

    public MatchDay() {
    }


}
