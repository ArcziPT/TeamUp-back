package com.arczipt.teamup.mapper;

import com.arczipt.teamup.dto.JobApplicationDTO;
import com.arczipt.teamup.model.JobApplication;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(uses = {UserMapper.class, JobPostingMapper.class})
public interface JobApplicationMapper {

    JobApplicationMapper INSTANCE = Mappers.getMapper(JobApplicationMapper.class);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "posting", source = "jobPosting")
    @Mapping(target = "user", source = "applicant")
    @Mapping(target = "status", source = "status")
    JobApplicationDTO mapToJobApplicationDTO(JobApplication application);
}
