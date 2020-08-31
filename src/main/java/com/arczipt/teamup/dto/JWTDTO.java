package com.arczipt.teamup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Named;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JWTDTO {
    private Long id;
    private String jwt;
}
