package com.hautilargi.footman.players.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.core.util.PlayerStatus;
import com.hautilargi.footman.players.model.PlayerStats;


import java.util.List;


@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {
    List<PlayerStats> findByPlayerStatus(PlayerStatus status);
}