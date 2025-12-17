package com.hautilargi.footman.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@jakarta.persistence.Entity
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Player> players;

    public Team() {
    }

    public Team( String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }



    /*GETTERS AND SETTERS */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {    
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
   public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public List<Player> getPlayers() {
        return this.players;
    }

    public void addPlayer(Player player) {
        this.players.add(player);
        player.setTeam(this);
    }





    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name + ", players=" + players + "]";
    }
}
