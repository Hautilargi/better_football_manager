package com.hautilargi.footman.matches.model;

import com.hautilargi.footman.clubs.model.Stadium;
import com.hautilargi.footman.clubs.model.Team;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class PlaceHolderMatch extends AbstractMatch {

   

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_id_home")
    public Team home;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "team_id_away")
    public Team away;

    @ManyToOne
    public Stadium venue;


    public PlaceHolderMatch() {
    }

    public PlaceHolderMatch(Team h, Team a) {
        home = h;
        away = a;
    }

}