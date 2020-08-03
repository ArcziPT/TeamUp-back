package com.arczipt.teamup.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TEAMUP_USER")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private String hash;

    private String firstName;
    private String surname;
    private String email;

    @ManyToMany
    @JoinTable(name = "users_skills",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<Skill> skills;

    @ElementCollection
    private List<String> urls;

    private String description;

    private Integer rating;

    @OneToMany
    private List<ProjectMember> projectMember;

    @ManyToMany
    @JoinTable(name = "user_raters",
    joinColumns = @JoinColumn(name = "rating_user_id"),
    inverseJoinColumns = @JoinColumn(name = "rated_user_id"))
    private List<User> ratedBy;

    @ManyToMany
    @JoinTable(name = "user_raters",
            inverseJoinColumns = @JoinColumn(name = "rating_user_id"),
            joinColumns = @JoinColumn(name = "rated_user_id"))
    private List<User> ratedUsers;

    @OneToMany
    private List<JobApplication> applications;

    @OneToMany
    private List<ProjectInvitation> projectInvitations;

    @ManyToMany
    private List<Privilege> privileges;
}
