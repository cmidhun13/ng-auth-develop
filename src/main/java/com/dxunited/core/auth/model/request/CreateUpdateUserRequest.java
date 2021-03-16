package com.dxunited.core.auth.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateUpdateUserRequest {
    @JsonProperty("member_id")
    private Long memberId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("address_line1")
    private String addressLine1;
    @JsonProperty("address_line2")
    private String addressLine2;
    @JsonProperty("address_line3")
    private String addressLine3;
    private String city;
    private String country;
    private String state;
    @JsonProperty("postal_code")
    private String postalCode;
}
