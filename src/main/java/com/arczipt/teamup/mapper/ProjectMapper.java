package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.ProjectDTO;
import com.arczipt.teamup.dto.ProjectMinDTO;
import com.arczipt.teamup.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {ProjectMemberMapper.class, JobPostingMapper.class})
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "briefDescription", source = "briefDescription")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "urls", source = "urls")
    @Mapping(target = "members", source = "members", qualifiedByName = "listProjectMemberToUsername")
    @Mapping(target = "postings", source = "jobPostings")
    ProjectDTO mapToProjectDTO(Project project);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "briefDescription", source = "briefDescription")
    ProjectMinDTO mapToProjectMinDTO(Project project);
}
