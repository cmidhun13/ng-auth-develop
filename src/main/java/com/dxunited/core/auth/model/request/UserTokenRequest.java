package com.dxunited.core.auth.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserTokenRequest {
    //@NotBlank(message = "{requestBody.field-missing}")
    private String clientId;
    //@NotBlank(message = "{requestBody.field-missing}")
    private String clientSecret;
    private String username;
    private String password;


}
