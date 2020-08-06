package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.JobApplicationDTO;
import com.arczipt.teamup.model.JobApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface JobApplicationMapper {

    JobApplicationMapper INSTANCE = Mappers.getMapper(JobApplicationMapper.class);

    @Mapping(target = "title", source = "jobPosting.title")
    @Mapping(target = "user", source = "applicant")
    @Mapping(target = "status", source = "status")
    JobApplicationDTO mapToJobApplicationDTO(JobApplication application);

    default Integer mapToApplicationsCount(List<JobApplication> application){
        return application.size();
    }
}
