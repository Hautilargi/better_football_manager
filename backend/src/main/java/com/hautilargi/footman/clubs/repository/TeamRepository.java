package com.hautilargi.footman.clubs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.users.model.User;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    List<Team> findByNameIgnoreCase(String name);
    List<Team> findByActive(boolean active);
    List<Team> findByTierAndActive(int tier, boolean active);
    List<Team> findByOwner(User user);
    Team findByPlayers_id(long id);


    @Deprecated
    List<Team> findByTier(int tier);

}