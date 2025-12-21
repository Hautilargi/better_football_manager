package com.hautilargi.footman.players.model;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import com.hautilargi.footman.clubs.model.HistorySquad;

import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@jakarta.persistence.Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.TABLE_PER_CLASS)
public class HistoryPlayer extends AbstractPlayer {

    @OneToOne
    @JoinColumn(name = "squad_id")
    private HistorySquad squad;

    @ManyToOne
    @JoinColumn(name = "real_player_id")
    private Player realPlayer;

    public HistoryPlayer() {
    }

    public HistoryPlayer(String lastname, String firstName, int skillLevel) {
        this.lastname = lastname;
        this.firstName = firstName;
        this.skillLevel = skillLevel;
    }

    public void setRealPlayer(Player realPlayer) {
        this.realPlayer = realPlayer;

    }

    public Player getRealPlayer() {
        return this.realPlayer;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%s,\"firstname\":\"%s\",\"lastname\":\"%s\",\"skillLevel\":%s,\"defense\":%s}",
                id, firstName, lastname, skillLevel);
    }

    public HistorySquad getSquad() {
        return squad;
    }

    public void setSquad(HistorySquad squad) {
        this.squad =  squad;
    }
}
