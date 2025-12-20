package com.hautilargi.footman.players.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.players.model.Player;

import java.util.List;
import java.util.Optional;




@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    List<Player> findByLastnameIgnoreCase(String lastname);
    List<Player> findByFirstNameIgnoreCase(String firstName);
    List<Player> findBySkillLevelGreaterThanEqual(float skillLevel);
    Optional<Player> findByLastnameIgnoreCaseAndFirstNameIgnoreCase(String lastname, String firstName);
}