package com.arczipt.teamup.mapper;

import com.arczipt.teamup.controller.ProjectController;
import com.arczipt.teamup.dto.JobPostingCreateDTO;
import com.arczipt.teamup.dto.JobPostingDTO;
import com.arczipt.teamup.dto.NameAndLinkDTO;
import com.arczipt.teamup.model.JobPosting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@Mapper(uses = {SkillMapper.class, JobApplicationMapper.class, ProjectRoleMappper.class})
public interface JobPostingMapper {

    JobPostingMapper INSTANCE = Mappers.getMapper(JobPostingMapper.class);

    @Mapping(target = "title", source = "title")
    @Mapping(target = "applicationsCount", source = "applications")
    @Mapping(target = "role", source = "role")
    @Mapping(target = "project", source = "project")
    JobPostingDTO mapToJobPostingDTO(JobPosting posting);

    List<String> mapToTitle(List<JobPosting> jobPostings);

    default String mapToTitle(JobPosting jobPosting){
        return jobPosting.getTitle();
    }

    List<NameAndLinkDTO> mapToNameAndLinkDTO(List<JobPosting> postings);

    default NameAndLinkDTO mapToNameAndLinkDTO(JobPosting posting){
        NameAndLinkDTO dto = new NameAndLinkDTO();
        dto.setName(posting.getTitle());
        dto.add(linkTo(methodOn(ProjectController.class).getJobPosting(posting.getProject().getId(), posting.getId())).withSelfRel());
        return dto;
    }
}
