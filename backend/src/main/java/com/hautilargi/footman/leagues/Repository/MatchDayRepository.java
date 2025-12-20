package com.hautilargi.footman.leagues.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.leagues.model.MatchDay;


@Repository
public interface MatchDayRepository extends JpaRepository<MatchDay, Long> {
        List<MatchDay> findByLeagueAndDayInSeason(long leagueId, int dayInSeason);

}