package com.hautilargi.footman;


import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.hautilargi.footman.model.Player;
import com.hautilargi.footman.model.PlayerRepository;

import jakarta.annotation.PostConstruct;


@SpringBootApplication
public class Footman {

    @Autowired
    PlayerRepository playerRepository;

public static void main(String[] args) {
SpringApplication.run(Footman.class, args);
}


@PostConstruct
public void started() {
    System.out.println("Application started...");
    System.out.println("Adding Samples to Database...");
        //playerRepository.deleteAllInBatch();
        Player player1 = new Player("Smith", "John", 7.5f);
        Player player2 = new Player("Doe", "Jane", 8.0f);

    
        // Save Parent Reference (which will save the child as well)
        playerRepository.save(player1);
        playerRepository.save(player2);

    }
}