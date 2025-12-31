package com.hautilargi.footman.matches.dto;

import com.hautilargi.footman.clubs.dto.ClubBasicDto;

public record MatchDetailDto(
    Long id,
    String name,
    String email,
    ClubBasicDto team,
    ClubBasicDto away
) {}
