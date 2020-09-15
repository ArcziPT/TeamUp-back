package com.arczipt.teamup.mapper;

import com.arczipt.teamup.controller.ProjectController;
import com.arczipt.teamup.dto.IdAndNameDTO;
import com.arczipt.teamup.dto.JobPostingDTO;
import com.arczipt.teamup.dto.JobPostingMinDTO;
import com.arczipt.teamup.model.ApplicationStatus;
import com.arczipt.teamup.model.JobApplication;
import com.arczipt.teamup.model.JobPosting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;

@Mapper(uses = {SkillMapper.class, ProjectRoleMapper.class})
public interface JobPostingMapper {

    JobPostingMapper INSTANCE = Mappers.getMapper(JobPostingMapper.class);

    @Mapping(target = "id", source = "posting.id")
    @Mapping(target = "posting", source = "posting")
    @Mapping(target = "applicationsCount", source = "posting.applications")
    @Mapping(target = "role", source = "posting.role")
    @Mapping(target = "project", source = "posting.project")
    @Mapping(target = "hasApplied", source = "hasApplied")
    JobPostingDTO mapToJobPostingDTO(JobPosting posting, Boolean hasApplied);

    @Mapping(target = "title", source = "title")
    @Mapping(target = "project", source = "project")
    @Mapping(target = "role", source = "role")
    JobPostingMinDTO mapToJobPostingMinDTO(JobPosting jobPosting);

    List<String> mapToTitle(List<JobPosting> jobPostings);

    default String mapToTitle(JobPosting jobPosting){
        return jobPosting.getTitle();
    }

    List<IdAndNameDTO> mapToNameAndLinkDTO(List<JobPosting> postings);

    default IdAndNameDTO mapToNameAndLinkDTO(JobPosting posting){
        IdAndNameDTO dto = new IdAndNameDTO();
        dto.setName(posting.getTitle());
        dto.setId(posting.getId());
        return dto;
    }

    default Long mapToApplicationsCount(List<JobApplication> application){
        return application.stream().filter(app -> app.getStatus() == ApplicationStatus.WAITING).count();
    }
}
