package com.hautilargi.footman.core.dto;

public record UserDto(
    Long id,
    String name,
    String email,
    ClubDto team
) {}



