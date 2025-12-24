package com.hautilargi.footman.leagues.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.leagues.model.League;


@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {
    List<League> findBySeasonNo(Long seasonId);
    List<League> findBySeasonNoAndTier(Long seasonId,int tier);
}