package com.arczipt.teamup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobPostingDTO {
    private String title;
    private IdAndNameDTO project;
    private RoleDTO role;
    private Integer applicationsCount;
}
