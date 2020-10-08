package com.szells.gce.auth.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserNotFoundException extends AuthServiceException {
    public UserNotFoundException(String code, String... additionalParams) {
        super(code, additionalParams);
    }
}
