package com.szells.gce.auth.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.szells.gce.auth.util.Password;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @Password
    private String password;

    private Long memberId;

    @JsonProperty("hash_cd")
    private String hashCd;
}

