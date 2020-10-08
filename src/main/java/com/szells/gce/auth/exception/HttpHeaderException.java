package com.szells.gce.auth.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpHeaderException extends AuthServiceException {

    public HttpHeaderException(String code, String... additionalParams) {
        super(code, additionalParams);
    }
}
