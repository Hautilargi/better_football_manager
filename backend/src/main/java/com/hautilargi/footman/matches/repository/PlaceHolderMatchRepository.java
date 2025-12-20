package com.hautilargi.footman.matches.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.matches.model.PlaceHolderMatch;


@Repository
public interface PlaceHolderMatchRepository extends JpaRepository<PlaceHolderMatch, Long> {
}