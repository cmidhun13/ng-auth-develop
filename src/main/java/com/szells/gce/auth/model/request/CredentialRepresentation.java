package com.szells.gce.auth.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialRepresentation {
    private Boolean temporary;
    private String type;
    private String value;
}
