package com.hautilargi.footman.core.mapper;

import org.mapstruct.Mapper;
import com.hautilargi.footman.clubs.model.Team;
import com.hautilargi.footman.core.dto.ClubDto;

@Mapper(componentModel = "spring")
public interface ClubMapper {

    ClubDto toDto(Team team);
}