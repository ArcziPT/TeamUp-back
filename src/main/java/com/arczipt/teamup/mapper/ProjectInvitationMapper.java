package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.ProjectInvitationDTO;
import com.arczipt.teamup.dto.ProjectInvitationMinDTO;
import com.arczipt.teamup.model.Project;
import com.arczipt.teamup.model.ProjectInvitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {SkillMapper.class})
public interface ProjectInvitationMapper {

    ProjectInvitationMapper INSTANCE = Mappers.getMapper(ProjectInvitationMapper.class);

    @Mapping(target = "projectName", source = "project.name")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "status", source = "status")
    ProjectInvitationDTO mapToProjectInvitationDTO(ProjectInvitation invitation);

    @Mapping(target = "projectName", source = "project.name")
    @Mapping(target = "role", source = "role.name")
    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "status", source = "status")
    ProjectInvitationMinDTO mapToProjectInvitationMinDTO(ProjectInvitation invitation);
}
