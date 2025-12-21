package com.hautilargi.footman.players.model;

import java.util.HashSet;
import java.util.Set;

import com.hautilargi.footman.clubs.model.Squad;
import com.hautilargi.footman.util.PlayerStatus;

import jakarta.persistence.Inheritance;
import jakarta.persistence.ManyToMany;

@jakarta.persistence.Entity
@Inheritance(strategy = jakarta.persistence.InheritanceType.TABLE_PER_CLASS)
public class Player extends AbstractPlayer {

    @ManyToMany(mappedBy = "squadMembers")
    private Set<Squad> squads;
    private long salery;
    private PlayerStatus playerStatus;

    private int speed;
    private int stamina;
    private int passing;
    private int shooting;
    private int defense;
    private int dribbling;

    public Player() {
        this.squads=new HashSet<>();
    }

    public Player(String lastname, String firstName, int skillLevel) {     
        this.squads=new HashSet<>();  
        this.lastname = lastname;
        this.firstName = firstName;
        this.skillLevel = skillLevel;
        this.setDefense(50);
        this.setDribbling(50);
        this.setPassing(50);
        this.setShooting(50);
        this.setSpeed(50);
        this.setStamina(50);
        this.age=25;
        this.salery=100;
        this.nationality="de";
        this.playerStatus=PlayerStatus.ACTIVE;
    }

    @Override
    public String toString() {
        return String.format("{\"id\":%s,\"firstname\":\"%s\",\"lastname\":\"%s\",\"skillLevel\":%s,\"defense\":%s}",
                id, firstName, lastname, skillLevel, defense);
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

    public long getSalery() {
        return this.salery;
    }

    public void setSalery(long salery) {
        this.salery = salery;
    }

    public void setSquads(Set<Squad> squads) {
        this.squads = squads;
    }

    public Set<Squad> getSquads(){
        return this.squads;
    }

    public void setPlayerStatus(PlayerStatus newStatus){
        this.playerStatus=newStatus;
    }

    public PlayerStatus getPlayerStatus(){
        return this.playerStatus;
    } 

}
