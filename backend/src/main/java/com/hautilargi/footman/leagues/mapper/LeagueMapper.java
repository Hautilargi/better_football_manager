package com.hautilargi.footman.leagues.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hautilargi.footman.leagues.dto.LeagueDto;
import com.hautilargi.footman.leagues.model.League;

@Mapper(componentModel = "spring")
public interface LeagueMapper {

    LeagueDto toDto(League league);

    List<LeagueDto> toDtos(List<League> leagues);
}
