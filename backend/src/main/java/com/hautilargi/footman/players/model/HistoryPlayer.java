package com.hautilargi.footman.players.model;

import com.hautilargi.footman.clubs.model.AbstractSquad;
import com.hautilargi.footman.clubs.model.HistorySquad;

import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@jakarta.persistence.Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.TABLE_PER_CLASS)
public class HistoryPlayer extends AbstractPlayer {
    

    @ManyToOne
    @JoinColumn(name = "squad_id")
    public HistorySquad squad;

    public HistoryPlayer() {
    }

    public HistoryPlayer( String lastname, String firstName, int skillLevel) {
        this.lastname = lastname;
        this.firstName = firstName;
        this.skillLevel = skillLevel;
    }

    @Override
     public String toString() {
        return String.format("{\"id\":%s,\"firstname\":\"%s\",\"lastname\":\"%s\",\"skillLevel\":%s,\"defense\":%s}", id,firstName,lastname,skillLevel);
     }

    @Override
    public AbstractSquad getSquad() {
        return squad;
    }
    @Override
    public void setSquad(AbstractSquad squad) {
        this.squad = (HistorySquad) squad;
    }
}
