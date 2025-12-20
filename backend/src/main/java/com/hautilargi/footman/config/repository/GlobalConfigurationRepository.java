package com.hautilargi.footman.config.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hautilargi.footman.config.model.GlobalConfiguration;



@Repository
public interface GlobalConfigurationRepository extends JpaRepository<GlobalConfiguration, Long> {
}