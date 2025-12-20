package com.hautilargi.footman.config.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class GlobalConfiguration {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    private int currentSeason=1;
    private int currentDay=1;

    public GlobalConfiguration(){
    }

    

}
