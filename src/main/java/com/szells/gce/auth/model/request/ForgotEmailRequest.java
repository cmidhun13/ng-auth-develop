package com.szells.gce.auth.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ForgotEmailRequest {
    private String firstName;
    private String lastName;
    private String dob;

    private String hashCode;
}
