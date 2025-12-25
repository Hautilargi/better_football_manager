package com.hautilargi.footman.leagues.dto;

import java.util.List;

public record MatchDayTableDto( 
    long season,
    int leagueIndex,
    int matchDay,
    List<LeagueTableEntryDto> table
)
{}
