package com.hautilargi.footman.players.dto;

import com.hautilargi.footman.clubs.dto.ClubBasicDto;

public record PlayerFullDto(
    Long id,
    String firstName,
    String lastName,
    String nationality,
    long salery,
    int skillLevel,
    int age,
    int speed,
    int stamina,
    int passing,
    int shooting,
    int defense,
    int dribbling,
    ClubBasicDto team
) {}