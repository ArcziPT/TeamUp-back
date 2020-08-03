package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.ProjectInvitation;
import com.arczipt.teamup.model.Skill;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInvitationDTO {

    public ProjectInvitationDTO(ProjectInvitation inv){
        projectName = inv.getProject().getName();
        role = inv.getRole().getRole();
        skills = inv.getRole().getSkills().stream().map(Skill::getName).collect(Collectors.toList());
        username = inv.getUser().getUsername();
    }

    private String projectName;

    private String role;

    private List<String> skills;

    private String username;
}
