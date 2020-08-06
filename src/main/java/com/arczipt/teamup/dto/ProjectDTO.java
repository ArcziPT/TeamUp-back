package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.Project;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectDTO {
    private String name;
    private String briefDescription;
    private String description;
    private List<String> urls;
    private List<NameAndLinkDTO> members;
    private List<NameAndLinkDTO> postings;
}
