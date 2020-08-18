package com.arczipt.teamup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    private List<IdAndNameDTO> projects;
}
