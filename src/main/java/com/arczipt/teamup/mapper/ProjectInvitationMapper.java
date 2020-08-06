package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.ProjectInvitationDTO;
import com.arczipt.teamup.model.ProjectInvitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SkillMapper.class})
public interface ProjectInvitationMapper {

    ProjectInvitationMapper INSTANCE = Mappers.getMapper(ProjectInvitationMapper.class);

    @Mapping(target = "project", source = "project")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "status", source = "status")
    ProjectInvitationDTO mapToProjectInvitationDTO(ProjectInvitation invitation);
}
