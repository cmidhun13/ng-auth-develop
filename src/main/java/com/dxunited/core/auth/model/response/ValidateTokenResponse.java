package com.dxunited.core.auth.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)


public class ValidateTokenResponse {

    private Integer exp;

    private Integer iat;

    private String iss;

    private String typ;

    private String azp;

    @JsonProperty("preferred_username")
    private String preferredUsername;

    @JsonProperty("email_verified")
    private Boolean emailVerified;

    @JsonProperty("realm_access")
    private RealmAccess realmAccess;

    @JsonProperty("resource_access")
    private Map<String, RealmAccess> resourceAccess;

    private String scope;

    @JsonProperty("member_id")
    private String memberId;

    @JsonProperty("client_id")
    private String clientId;

    private String username;

    private Boolean active;
    private String userId;
    private String id;

}
