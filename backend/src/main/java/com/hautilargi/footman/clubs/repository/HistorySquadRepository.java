package com.hautilargi.footman.clubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.clubs.model.HistorySquad;


@Repository
public interface HistorySquadRepository extends JpaRepository<HistorySquad, Long> {
}