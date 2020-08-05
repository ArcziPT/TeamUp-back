package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.ProjectMemberDTO;
import com.arczipt.teamup.model.ProjectMember;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {SkillMapper.class})
public interface ProjectMemberMapper {

    ProjectMemberMapper INSTANCE = Mappers.getMapper(ProjectMemberMapper.class);

    @Mapping(target = "user", source = "user.username")
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
}
