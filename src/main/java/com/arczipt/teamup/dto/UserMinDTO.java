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
 * UserMinView - minimal set of user's public data
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class UserMinDTO {
    private String username;
    private List<String> skills;
    private Integer rating;
    private List<String> projects;

    @Override
    public String toString() {
        return "UserMinDTO{" +
                "username='" + username + '\'' +
                ", skills=" + skills +
                ", rating=" + rating +
                ", projects=" + projects +
                '}';
    }
}
