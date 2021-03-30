package com.dxunited.core.auth.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class UserInfoResponse {
    private String userId;

    private String realm;

    private Boolean creatRealm;

}
