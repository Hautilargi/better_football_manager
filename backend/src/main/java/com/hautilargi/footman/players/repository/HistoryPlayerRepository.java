package com.hautilargi.footman.players.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.players.model.HistoryPlayer;

import java.util.List;
import java.util.Optional;




@Repository
public interface HistoryPlayerRepository extends JpaRepository<HistoryPlayer, Long> {
    List<HistoryPlayer> findByLastnameIgnoreCase(String lastname);
    List<HistoryPlayer> findByFirstNameIgnoreCase(String firstName);
    List<HistoryPlayer> findBySkillLevelGreaterThanEqual(float skillLevel);
    Optional<HistoryPlayer> findByLastnameIgnoreCaseAndFirstNameIgnoreCase(String lastname, String firstName);
}