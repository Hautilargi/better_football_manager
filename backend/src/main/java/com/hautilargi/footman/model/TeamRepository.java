package com.hautilargi.footman.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByNameIgnoreCase(String name);
}