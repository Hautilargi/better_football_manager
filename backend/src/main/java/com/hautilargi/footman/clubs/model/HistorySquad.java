package com.hautilargi.footman.clubs.model;

import java.util.List;

import com.hautilargi.footman.core.util.emum.Formations;
import com.hautilargi.footman.players.model.HistoryPlayer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.OneToMany;

@Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.TABLE_PER_CLASS)
public class HistorySquad extends AbstractSquad {

    private boolean active = true;

    //TODO Map Player to Position
    @OneToMany(mappedBy = "squad", cascade = CascadeType.MERGE, orphanRemoval = true)
    private List<HistoryPlayer> players;

    public HistorySquad() {
    }

    public HistorySquad(Formations formation, Team team, List<HistoryPlayer> players) {
        this.formation = formation;
        this.players = players;
    }

    /* GETTERS AND SETTERS */

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
