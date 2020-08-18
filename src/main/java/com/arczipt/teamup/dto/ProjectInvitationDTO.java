package com.arczipt.teamup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInvitationDTO {
    private IdAndNameDTO project;
    private RoleDTO role;
    private IdAndNameDTO user;
    private String status;
}
