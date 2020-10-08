package com.szells.gce.auth.exception;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LogoutException extends RuntimeException {
    String code;
    String message;

    public LogoutException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}