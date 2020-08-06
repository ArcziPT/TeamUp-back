package com.arczipt.teamup.mapper;

import com.arczipt.teamup.controller.ProjectController;
import com.arczipt.teamup.dto.NameAndLinkDTO;
import com.arczipt.teamup.dto.ProjectDTO;
import com.arczipt.teamup.dto.ProjectMinDTO;
import com.arczipt.teamup.model.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@Mapper(uses = {ProjectMemberMapper.class, JobPostingMapper.class})
public interface ProjectMapper {

    ProjectMapper INSTANCE = Mappers.getMapper(ProjectMapper.class);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "briefDescription", source = "briefDescription")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "urls", source = "urls")
    @Mapping(target = "members", source = "members", qualifiedByName = "listProjectMemberToUsernameAndLink")
    @Mapping(target = "postings", source = "jobPostings")
    ProjectDTO mapToProjectDTO(Project project);

    @Mapping(target = "name", source = "project")
    @Mapping(target = "briefDescription", source = "project.briefDescription")
    ProjectMinDTO mapToProjectMinDTO(Project project);

    List<NameAndLinkDTO> mapToNameAndLinkDTO(List<Project> projects);

    default NameAndLinkDTO mapToNameAndLinkDTO(Project project){
        NameAndLinkDTO dto = new NameAndLinkDTO();
        dto.setName(project.getName());
        dto.add(linkTo(methodOn(ProjectController.class).getProject(project.getId())).withSelfRel());
        return dto;
    }
}
