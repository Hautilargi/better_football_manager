package com.hautilargi.footman.leagues.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.leagues.model.Season;


@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
        Optional<Season> findTopByOrderBySeasonNoDesc();
}