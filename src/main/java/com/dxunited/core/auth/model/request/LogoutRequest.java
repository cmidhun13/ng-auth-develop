package com.dxunited.core.auth.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoutRequest {
    private String refreshToken;
    private String clientId;
    private String clientSecret;
}
