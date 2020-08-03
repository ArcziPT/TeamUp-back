package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.JobApplication;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JobApplicationDTO {
    public JobApplicationDTO(JobApplication app){
        title = app.getJobPosting().getTitle();
        username = app.getApplicant().getUsername();
        status = app.getStatus().name();
    }

    private String title;

    private String username;

    private String status;
}
