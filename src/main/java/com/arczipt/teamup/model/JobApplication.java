package com.arczipt.teamup.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class JobApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "app_posting_id")
    private JobPosting jobPosting;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "app_user_id")
    private User applicant;

    @Enumerated
    @Column(columnDefinition = "smallint")
    private ApplicationStatus status;
}
