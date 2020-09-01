package com.arczipt.teamup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class StatusDTO {
    private Boolean status;
    private String msg;

    public StatusDTO(){
        status = false;
        msg = "";
    }

    public StatusDTO(Boolean status){
        this.status = status;
        msg = "";
    }

    public StatusDTO(Boolean status, String msg){
        this.status = status;
        this.msg = msg;
    }
}
