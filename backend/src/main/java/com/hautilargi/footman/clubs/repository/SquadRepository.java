package com.hautilargi.footman.clubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.clubs.model.Squad;

@Repository
public interface SquadRepository extends JpaRepository<Squad, Long> {
}