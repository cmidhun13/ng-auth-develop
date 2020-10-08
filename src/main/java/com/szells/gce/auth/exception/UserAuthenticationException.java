package com.szells.gce.auth.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAuthenticationException extends AuthServiceException {
    private Integer failCount;

    public UserAuthenticationException(String code, Integer failCount, String... additionalParams) {
        super(code, additionalParams);
        this.failCount = failCount;
    }
}
