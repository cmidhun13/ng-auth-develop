package com.dxunited.core.auth.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthenticationException extends AuthServiceException {
    private Integer failCount;

    public UserAuthenticationException(String code, String message,Integer failCount, String... additionalParams) {
        super(code,message, additionalParams);
        this.failCount = failCount;
    }
}
