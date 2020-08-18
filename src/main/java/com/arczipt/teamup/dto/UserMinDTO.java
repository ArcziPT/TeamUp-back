package com.arczipt.teamup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private List<IdAndNameDTO> projects;

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
