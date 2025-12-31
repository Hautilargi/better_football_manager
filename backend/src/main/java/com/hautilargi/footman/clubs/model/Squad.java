package com.hautilargi.footman.clubs.model;

import java.util.List;
import java.util.Set;

import com.hautilargi.footman.core.util.Formations;
import com.hautilargi.footman.players.model.Player;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;


@Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.TABLE_PER_CLASS)

public class Squad extends AbstractSquad {

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToMany( fetch = FetchType.EAGER)
    @JoinTable(
        name = "players_to_squads", 
        joinColumns = @JoinColumn(name = "squad_id"), 
        inverseJoinColumns = @JoinColumn(name = "player_id"))
    private List<Player> squadMembers;

    public Squad() {
    }

    public Squad(Formations formation, Team team, List<Player> squadMembers) {
        this.formation = formation;
        this.team = team;
        this.squadMembers = squadMembers;
        for (Player player : squadMembers) {
            Set<Squad> playerSquads = player.getSquads();
            playerSquads.add(this);
            player.setSquads(playerSquads);
        }
    }

    /* GETTERS AND SETTERS */

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setSquadMembers(List<Player> squadMembers) {
        this.squadMembers = squadMembers;
    }

    public List<Player> getSquadMembers() {
        return this.squadMembers;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}
