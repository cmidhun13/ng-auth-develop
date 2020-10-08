package com.szells.gce.auth.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class CreateUpdateCustomerRequest {
    @NotBlank(message = "{requestBody.field-missing}")
    private Long customerId;
    @NotBlank(message = "{requestBody.field-missing}")
    private String userId;
}
