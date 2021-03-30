package com.dxunited.core.auth.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class GenericResponse {
    private boolean success;
    private int status;
    private String message;
    private String description;
    private Object data;

    public GenericResponse(boolean success, int status, String message, String description, Object data) {
        this.success = success;
        this.status = status;
        this.description = description;
        this.message = message;
        this.data = data;
    }

}
