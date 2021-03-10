package com.szells.gce.auth.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthServiceException extends RuntimeException {
    private String code;
    private String additionalParams;
    private String message;

    public AuthServiceException(String code,String message, String... additionalParams) {
        this.code = code;
        this.message = message;
        if (additionalParams.length > 0)
            this.additionalParams = String.join(",", additionalParams);
    }
}
