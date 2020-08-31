package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.ProjectInvitationDTO;
import com.arczipt.teamup.model.ProjectInvitation;
import com.arczipt.teamup.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ProjectRoleMapper.class, ProjectMapper.class, UserMapper.class})
public interface ProjectInvitationMapper {

    ProjectInvitationMapper INSTANCE = Mappers.getMapper(ProjectInvitationMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "project", source = "project")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "status", source = "status")
    ProjectInvitationDTO mapToProjectInvitationDTO(ProjectInvitation invitation);
}
