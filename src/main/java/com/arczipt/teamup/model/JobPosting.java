package com.arczipt.teamup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
public class JobPosting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "posting_project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "posting_role_id")
    private ProjectRole role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jobPosting")
    private List<JobApplication> applications;
}
