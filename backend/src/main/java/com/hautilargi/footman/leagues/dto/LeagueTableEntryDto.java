package com.hautilargi.footman.leagues.dto;

public record LeagueTableEntryDto(
    long teamId,
    int position,
    String name,
    int gamesPlayed,
    int wins,
    int draws,
    int losses,
    int goalsFor,
    int goalsAgainst,
    int diff,
    int points
) {}



