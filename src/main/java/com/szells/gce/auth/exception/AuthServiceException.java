package com.szells.gce.auth.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthServiceException extends RuntimeException {
    private String code;
    private String additionalParams;

    public AuthServiceException(String code, String... additionalParams) {
        this.code = code;
        if (additionalParams.length > 0)
            this.additionalParams = String.join(",", additionalParams);
    }
}
