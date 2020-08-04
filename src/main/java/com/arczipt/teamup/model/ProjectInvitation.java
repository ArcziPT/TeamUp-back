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

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "invitation_project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "invitation_role_id")
    private ProjectRole role;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "invitation_user_id")
    private User user;

    @Enumerated
    private InvitationStatus status;
}
