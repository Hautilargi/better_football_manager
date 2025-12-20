package com.hautilargi.footman.model.clubs;

import java.util.List;

import com.hautilargi.footman.model.players.Player;
import com.hautilargi.footman.util.Formations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PseudoSquad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    Formations formation;

    @ManyToOne
    private Team team;

    @ManyToOne
    private List<Player> players;
    
}
