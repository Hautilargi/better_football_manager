package com.hautilargi.footman.clubs.model;

import java.util.List;

import com.hautilargi.footman.players.model.Player;
import com.hautilargi.footman.util.Formations;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.TABLE_PER_CLASS)

public class Squad extends AbstractSquad {

    private boolean active = true;

    @OneToOne
    private Team team;

    @OneToMany(mappedBy = "squad", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    public Squad() {
    }

    public Squad(Formations formation, Team team, List<Player> players) {
        this.formation = formation;
        this.team = team;
        this.players = players;
        for (Player player : players) {
            player.setSquad(this);
        }
    }

    /* GETTERS AND SETTERS */

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Player> getPlayers() {
        return this.players;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
