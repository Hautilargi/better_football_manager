package com.hautilargi.footman.players.model;

import com.hautilargi.footman.clubs.model.AbstractSquad;
import com.hautilargi.footman.clubs.model.HistorySquad;
import com.hautilargi.footman.clubs.model.Squad;

import jakarta.annotation.Nullable;
import jakarta.persistence.Inheritance;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@jakarta.persistence.Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.TABLE_PER_CLASS)
public class Player extends AbstractPlayer {
    
    @ManyToOne
    @JoinColumn(name = "squad_id")
    public Squad squad;


    private int speed;
    private int stamina;
    private int passing;
    private int shooting;
    private int defense;
    private int dribbling;

    public Player() {
    }

    public Player( String lastname, String firstName, int skillLevel) {
        this.lastname = lastname;
        this.firstName = firstName;
        this.skillLevel = skillLevel;
        this.setDefense(50);
        this.setDribbling(50);
        this.setPassing(50);
        this.setShooting(50);
        this.setSpeed(50);
        this.setStamina(50);
    }

    @Override
     public String toString() {
        return String.format("{\"id\":%s,\"firstname\":\"%s\",\"lastname\":\"%s\",\"skillLevel\":%s,\"defense\":%s}", id,firstName,lastname,skillLevel,defense);
     }


    public int getSpeed() {
        return speed;
    }       
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    public int getStamina() {
        return stamina;
    }
    public void setStamina(int stamina) {
        this.stamina = stamina;
    }
    public int getPassing() {
        return passing;
    }
    public void setPassing(int passing) {
        this.passing = passing;
    }
    public int getShooting() {
        return shooting;
    }
    public void setShooting(int shooting) {
        this.shooting = shooting;
    }
    public int getDefense() {
        return defense;
    }
    public void setDefense(int defense) {
        this.defense = defense;
    }
    public int getDribbling() {
        return dribbling;
    }
    public void setDribbling(int dribbling) {
        this.dribbling = dribbling;
    }


    @Override
    public AbstractSquad getSquad() {
        return squad;
    }

    @Override
    public void setSquad(AbstractSquad squad) {
        this.squad = (Squad) squad;
    }


}
