package com.hautilargi.footman.players.dto;

public record PlayerSmallDto(
    Long id,
    String firstName,
    String lastName,
    String nationality,
    int skillLevel,
    int age
) {}



