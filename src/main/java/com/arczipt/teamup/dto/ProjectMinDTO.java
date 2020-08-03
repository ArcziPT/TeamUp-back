package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.Project;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMinDTO {
    public ProjectMinDTO(Project project){
        name = project.getName();
        briefDescription = project.getBriefDescription();
    }

    private String name;
    private String briefDescription;
}
