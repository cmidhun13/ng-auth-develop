package com.dxunited.core.auth.model.request;

import com.dxunited.core.auth.util.Password;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @Password
    private String password;

    private Long memberId;

    @JsonProperty("hash_cd")
    private String hashCd;
}

