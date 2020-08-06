package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.JobApplication;
import com.arczipt.teamup.model.JobPosting;
import com.arczipt.teamup.model.Project;
import com.arczipt.teamup.model.ProjectRole;
import lombok.Generated;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JobPostingDTO {
    private String title;
    private NameAndLinkDTO project;
    private RoleDTO role;
    private Integer applicationsCount;
}
