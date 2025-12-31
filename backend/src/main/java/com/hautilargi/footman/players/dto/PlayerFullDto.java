package com.hautilargi.footman.players.dto;

import com.hautilargi.footman.clubs.dto.ClubBasicDto;
import com.hautilargi.footman.core.util.Positions;

public record PlayerFullDto(
    Long id,
    String firstName,
    String lastName,
    String nationality,
    long salery,
    int skillLevel,
    int talent,
    int age,
    int speed,
    int stamina,
    int passing,
    int shooting,
    int defense,
    int dribbling,
    int intelligence,
    int goalkeeping,
    Positions preferredPosition,
    PlayerStatsDto playerStats,
    ClubBasicDto team
) {}