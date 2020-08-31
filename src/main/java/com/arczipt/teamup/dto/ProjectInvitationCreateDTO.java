package com.arczipt.teamup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.RoleInfo;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInvitationCreateDTO {
    private String projectName;
    private String username;
    private RoleDTO role;
}
