package com.szells.gce.auth.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidateTokenRequest {
    @NotBlank(message = "{requestBody.field-missing}")
    private String clientId;
    @NotBlank(message = "{requestBody.field-missing}")
    private String clientSecret;
    @NotBlank(message = "{requestBody.field-missing}")
    private String token;
}
