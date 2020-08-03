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

    @ManyToOne
    private Project project;

    @ManyToOne
    private ProjectRole role;

    @OneToMany
    private List<JobApplication> applications;
}
