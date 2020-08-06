package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.Project;
import com.arczipt.teamup.model.ProjectMember;
import com.arczipt.teamup.model.Skill;
import com.arczipt.teamup.model.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * UserView - set of all of user's public data
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
    private String username;
    private List<String> skills;
    private List<String> urls;
    private String description;
    private Integer rating;
    private List<NameAndLinkDTO> projects;
}
