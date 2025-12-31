package com.hautilargi.footman.users.dto;

import com.hautilargi.footman.clubs.dto.ClubBasicDto;

public record UserDto(
    Long id,
    String name,
    String email,
    ClubBasicDto team
) {}



