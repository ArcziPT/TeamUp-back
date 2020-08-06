package com.arczipt.teamup.dto;

import com.arczipt.teamup.model.Project;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProjectMinDTO {
    private NameAndLinkDTO name;
    private String briefDescription;
}
