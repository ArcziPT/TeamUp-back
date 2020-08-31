package com.arczipt.teamup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobPostingDTO {
    private Long id;
    private IdAndNameDTO posting;
    private IdAndNameDTO project;
    private RoleDTO role;
    private Long applicationsCount;
    private Boolean hasApplied;
}
