package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.Project;
import com.arczipt.teamup.model.ProjectMember;
import com.arczipt.teamup.model.Skill;
import com.arczipt.teamup.model.User;
import lombok.Getter;
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
public class UserDTO {

    public UserDTO(User user){
        username = user.getUsername();
        skills = user.getSkills().stream().map(Skill::getName).collect(Collectors.toList());
        urls = user.getUrls();
        description = user.getDescription();
        rating = user.getRating();
        projects = user.getProjectMember().stream().map(ProjectMember::getProject).map(Project::getName).collect(Collectors.toList());
    }

    private String username;
    private List<String> skills;
    private List<String> urls;
    private String description;
    private Integer rating;
    private List<String> projects;
}
