package com.hautilargi.footman.clubs.model;

import com.hautilargi.footman.core.util.emum.Formations;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractSquad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    protected Formations formation;

    /* GETTERS AND SETTERS */

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Formations getFormation() {
        return formation;
    }

    public void setFormation(Formations formation) {
        this.formation = formation;
    }

}
