package com.hautilargi.footman.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@jakarta.persistence.Entity
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String lastname;
    private String firstName;

    private float skillLevel;


    
    public Player() {
    }

    public Player( String lastname, String firstName, float skillLevel) {
        this.lastname = lastname;
        this.firstName = firstName;
        this.skillLevel = skillLevel;
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
    public float getSkillLevel() {
        return skillLevel;
    }
    public void setSkillLevel(float skillLevel) {
        this.skillLevel = skillLevel;
    }


    @Override
    public String toString() {
        return "Player [id=" + id + ", lastname=" + lastname + ", firstName=" + firstName + ", skillLevel=" + skillLevel + "]";
    }
}
