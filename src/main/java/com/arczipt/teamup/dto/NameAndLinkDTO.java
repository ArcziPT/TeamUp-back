package com.arczipt.teamup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * DTO to represent resources by their name and link.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NameAndLinkDTO extends RepresentationModel<NameAndLinkDTO> {
    private String name;
}
