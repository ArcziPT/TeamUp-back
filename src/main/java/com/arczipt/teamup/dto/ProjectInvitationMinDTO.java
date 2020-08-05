package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.ProjectInvitation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ProjectInvitationMinDTO {
    private String projectName;
    private String role;
    private String username;
    private String status;
}
