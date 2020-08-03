package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.Project;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectDTO {
    public ProjectDTO(Project project){
        name = project.getName();
        briefDescription = project.getBriefDescription();
        description = project.getDescription();
        urls = project.getUrls();
    }

    private String name;
    private String briefDescription;
    private String description;
    private List<String> urls;
}
