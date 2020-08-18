package com.arczipt.teamup.dto;

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
    private List<IdAndNameDTO> members;
    private List<IdAndNameDTO> postings;
}
