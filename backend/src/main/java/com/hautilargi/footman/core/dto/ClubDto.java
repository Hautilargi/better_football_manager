package com.hautilargi.footman.core.dto;

public record ClubDto(
    Long id,
    String name,
    long balance,
    int tier
) {}
