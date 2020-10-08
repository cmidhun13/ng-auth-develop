package com.szells.gce.auth.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CreateNewUserRequest {

    private String username;
    private String password;
    private Long customerId;
}
