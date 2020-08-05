package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.JobApplication;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobApplicationDTO {
    private String title;
    private String username;
    private String status;
}
