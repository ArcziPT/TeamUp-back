package com.arczipt.teamup.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ProjectMemberDTO {
    private String user;
    private String role;
    private String description;
    private List<String> skills;
}
