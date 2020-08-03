package com.arczipt.teamup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

/**
 * Information about user's role/responsibilities/permissions at the project.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProjectRole {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String role;

    private String description;

    //list of skills a member is "using" in the project
    @ManyToMany
    private List<Skill> skills;
}