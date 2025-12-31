package com.hautilargi.footman.matches.dto;

import java.util.List;

public record MatchListDto(
    List<MatchListDto> matches
) {}