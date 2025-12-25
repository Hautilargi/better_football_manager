package com.hautilargi.footman.players.dto;

import java.util.List;

public record RosterDto(
    long team_id,
    List<PlayerSmallDto> rosterMembers
) {
    

}



