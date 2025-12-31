package com.hautilargi.footman.players.dto;

import com.hautilargi.footman.core.util.PlayerStatus;

public record PlayerStatsDto(
     PlayerStatus playerStatus,
     int remainingDaysForStatus,
     int goals,
     int red,
     int yellow,
     int yellowred,
     int gamesPlayed
    )
{} 