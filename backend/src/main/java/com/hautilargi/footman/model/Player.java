package com.hautilargi.footman.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@jakarta.persistence.Entity
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastname;
    private String firstName;
    private int skillLevel;
       
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    public Player() {
    }

    public Player( String lastname, String firstName, int skillLevel) {
        this.lastname = lastname;
        this.firstName = firstName;
        this.skillLevel = skillLevel;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getTeam() {
        return this.team;
    }

    public Long getId() {
        return id;
    }

    /*GETTERS AND SETTERS */
    public void setId(Long id) {
        this.id = id;
    }
    public String getLastname() {
        return lastname;
    }
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }   
    public String getFirstName() {
        return firstName;
    }   
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }   
    public int getSkillLevel() {
        return skillLevel;
    }
    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }


    @Override
    public String toString() {
        return "Player [id=" + id + ", lastname=" + lastname + ", firstName=" + firstName + ", skillLevel=" + skillLevel + "]";
    }
}
