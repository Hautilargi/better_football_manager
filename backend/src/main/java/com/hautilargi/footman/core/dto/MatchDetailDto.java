package com.hautilargi.footman.core.dto;


public record MatchDetailDto(
    Long id,
    String name,
    String email,
    ClubDto team
) {}
