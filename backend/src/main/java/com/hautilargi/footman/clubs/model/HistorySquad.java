package com.hautilargi.footman.clubs.model;

import java.util.List;

import com.hautilargi.footman.players.model.HistoryPlayer;
import com.hautilargi.footman.util.Formations;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;


@Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.TABLE_PER_CLASS)
public class HistorySquad extends AbstractSquad {

    boolean active=true;
    
    @OneToMany(mappedBy = "squad", cascade = CascadeType.MERGE, orphanRemoval = true)
    protected List<HistoryPlayer> players;


    public HistorySquad() {
    }

    public HistorySquad(Formations formation, Team team, List<HistoryPlayer> players) {
        this.formation = formation;
        this.players = players;
    }


    /*GETTERS AND SETTERS */

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }
    
     public void setPlayers(List<HistoryPlayer> players) {
        this.players = players;
    }   


    public List<HistoryPlayer> getPlayers() {
        return this.players;
    }
    

}
