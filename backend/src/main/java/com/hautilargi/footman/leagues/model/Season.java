package com.hautilargi.footman.leagues.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "season", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<League> leagues;

    private int seasonNo=0;

    /* Getters and Setters */
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }

    public int getSeasonNo() {
        return seasonNo;
    }

    public void setSeasonNo(int seasonNo) {
        this.seasonNo = seasonNo;
    }

}
