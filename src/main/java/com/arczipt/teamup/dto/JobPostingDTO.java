package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.JobApplication;
import com.arczipt.teamup.model.JobPosting;
import com.arczipt.teamup.model.Project;
import com.arczipt.teamup.model.ProjectRole;
import lombok.Generated;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
public class JobPostingDTO {

    public JobPostingDTO(JobPosting jobPosting){
        this.projectName = jobPosting.getProject().getName();
        this.title = jobPosting.getTitle();
        this.projectRole = jobPosting.getRole().getRole();

        this.applicationsCount = jobPosting.getApplications().size();
    }

    private String title;
    private String projectName;
    private String projectRole;
    private Integer applicationsCount;
}
