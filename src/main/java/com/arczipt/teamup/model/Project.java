package com.arczipt.teamup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<ProjectMember> members;

    @ElementCollection
    private List<String> urls;

    private String briefDescription;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<JobPosting> jobPostings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "project")
    private List<ProjectInvitation> invitations;
}
