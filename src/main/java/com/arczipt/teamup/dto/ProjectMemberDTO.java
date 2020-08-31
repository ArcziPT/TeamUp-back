package com.arczipt.teamup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectMemberDTO {
    private IdAndNameDTO user;
    private IdAndNameDTO project;
    private RoleDTO role;
    private boolean admin;
}
