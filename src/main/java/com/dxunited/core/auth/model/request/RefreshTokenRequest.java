package com.dxunited.core.auth.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RefreshTokenRequest {
    @NotBlank(message = "{requestBody.field-missing}")
    private String clientId;

    @NotBlank(message = "{requestBody.field-missing}")
    private String refreshToken;
}
