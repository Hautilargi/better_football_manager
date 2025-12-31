package com.hautilargi.footman.players.dto;

import java.util.List;

import com.hautilargi.footman.core.util.Formations;

public record SquadDto(
    long id,
    long team_id,
    Formations formation,
    List<PlayerSmallDto> squadMembers
) {
    

}



