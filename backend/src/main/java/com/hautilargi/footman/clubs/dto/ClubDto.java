package com.hautilargi.footman.clubs.dto;

public record ClubDto(
    Long id,
    String name,
    long balance,
    int tier
) {}
