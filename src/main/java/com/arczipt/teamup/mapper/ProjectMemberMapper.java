package com.arczipt.teamup.mapper;

import com.arczipt.teamup.controller.ProjectController;
import com.arczipt.teamup.controller.UserController;
import com.arczipt.teamup.dto.IdAndNameDTO;
import com.arczipt.teamup.dto.ProjectMemberDTO;
import com.arczipt.teamup.model.ProjectMember;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Mapper(uses = {SkillMapper.class})
public interface ProjectMemberMapper {

    ProjectMemberMapper INSTANCE = Mappers.getMapper(ProjectMemberMapper.class);

    @Mapping(target = "user", source = "user")
    @Mapping(target = "role", source = "role")
    ProjectMemberDTO mapToProjectMemberDTO(ProjectMember member);

    @Named("listProjectMemberToUsername")
    @IterableMapping(qualifiedByName = "projectMemberToUsername")
    List<String> mapToUsername(List<ProjectMember> projectMembers);

    @Named("projectMemberToUsername")
    default String mapToUsername(ProjectMember projectMember){
        return projectMember.getUser().getUsername();
    }

    @Named("listProjectMemberToProjectName")
    @IterableMapping(qualifiedByName = "projectMemberToProjectName")
    List<String> mapToProjectName(List<ProjectMember> projectMembers);

    @Named("projectMemberToProjectName")
    default String mapToProjectName(ProjectMember member){
        return member.getProject().getName();
    }

    /**
     * Maps ProjectMember to DTO containing username and link.
     *
     * @param members
     * @return
     */
    @Named("listProjectMemberToUsernameAndLink")
    @IterableMapping(qualifiedByName = "projectMemberToUsernameAndLink")
    List<IdAndNameDTO> mapToUsernameAndLinkDTO(List<ProjectMember> members);

    @Named("projectMemberToUsernameAndLink")
    default IdAndNameDTO mapToUsernameAndLinkDTO(ProjectMember member){
        IdAndNameDTO dto = new IdAndNameDTO();
        dto.setName(member.getUser().getUsername());
        dto.setId(member.getUser().getId());
        return dto;
    }

    /**
     * Maps ProjectMember to DTO containing project's name and link.
     *
     * @param members
     * @return
     */
    @Named("listProjectMemberToProjectNameAndLink")
    @IterableMapping(qualifiedByName = "projectMemberToProjectNameAndLink")
    List<IdAndNameDTO> mapToProjectNameAndLinkDTO(List<ProjectMember> members);

    @Named("projectMemberToProjectNameAndLink")
    default IdAndNameDTO mapToProjectNameAndLinkDTO(ProjectMember member){
        IdAndNameDTO dto = new IdAndNameDTO();
        dto.setName(member.getProject().getName());
        dto.setId(member.getProject().getId());
        return dto;
    }
}
