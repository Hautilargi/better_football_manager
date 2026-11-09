package com.hautilargi.footman.clubs.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.hautilargi.footman.clubs.dto.ClubBasicDto;
import com.hautilargi.footman.clubs.dto.ClubDto;
import com.hautilargi.footman.clubs.model.Team;

@Mapper(componentModel = "spring")
public interface ClubMapper {

    ClubDto toDto(Team team);

    ClubBasicDto toBasicDto(Team team);

    List<ClubBasicDto> toBasicDtos(List<Team> teams);
}