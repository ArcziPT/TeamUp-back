package com.arczipt.teamup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class JobApplicationDTO {
    private Long id;
    private IdAndNameDTO posting;
    private IdAndNameDTO user;
    private String status;
}
