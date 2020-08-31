package com.arczipt.teamup.dto;

import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class UserMinDTO {
    private IdAndNameDTO user;
    private List<String> skills;
    private Integer rating;
    private String briefDescription;
}
