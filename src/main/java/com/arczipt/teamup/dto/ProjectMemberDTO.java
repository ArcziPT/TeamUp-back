package com.arczipt.teamup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectMemberDTO {
    private String user;
    private RoleDTO role;
}
