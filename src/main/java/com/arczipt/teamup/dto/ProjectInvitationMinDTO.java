package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.ProjectInvitation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectInvitationMinDTO {

    public ProjectInvitationMinDTO(ProjectInvitation invitation){
        projectName = invitation.getProject().getName();
        role = invitation.getRole().getRole();
        username = invitation.getUser().getUsername();
    }

    private String projectName;

    private String role;

    private String username;
}
