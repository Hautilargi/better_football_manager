package com.hautilargi.footman.clubs.mapper;

import org.mapstruct.Mapper;

import com.hautilargi.footman.clubs.dto.ClubDto;
import com.hautilargi.footman.clubs.model.Team;

@Mapper(componentModel = "spring")
public interface ClubMapper {

    ClubDto toDto(Team team);
}