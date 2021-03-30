package com.dxunited.core.auth.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HttpHeaderException extends AuthServiceException {

    public HttpHeaderException(String code,String message, String... additionalParams) {
        super(code,message, additionalParams);
    }
}
