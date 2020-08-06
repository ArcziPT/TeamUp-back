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
    private NameAndLinkDTO project;
    private RoleDTO role;
    private NameAndLinkDTO user;
    private String status;
}
