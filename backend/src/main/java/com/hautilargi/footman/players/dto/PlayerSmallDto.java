package com.hautilargi.footman.players.dto;

import com.hautilargi.footman.core.util.emum.Positions;

public record PlayerSmallDto(
    Long id,
    String firstName,
    String lastName,
    String nationality,
    int skillLevel,
    int age,
    Positions preferredPosition
) {}



