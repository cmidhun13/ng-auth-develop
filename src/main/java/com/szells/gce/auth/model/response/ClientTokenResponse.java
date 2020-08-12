package com.szells.gce.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientTokenResponse {
    @NotBlank(message = "{requestBody.field-missing}")
    private String clientId;
    @NotBlank(message = "{requestBody.field-missing}")
    private String clientSecret;
}
