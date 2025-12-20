package com.hautilargi.footman.leagues.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.leagues.model.Season;


@Repository
public interface SeasonRepository extends JpaRepository<Season, Long> {
}