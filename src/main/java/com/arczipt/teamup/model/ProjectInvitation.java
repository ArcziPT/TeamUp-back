package com.arczipt.teamup.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
public class ProjectInvitation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private ProjectRole role;

    @ManyToOne
    private User user;

    @Enumerated
    private InvitationStatus status;
}
