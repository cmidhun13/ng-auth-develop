package com.szells.gce.auth.model.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CreateUserRequest {
    private String id;
    private String email;
    private Boolean enabled = true;
    private String firstName;
    private String lastName;
    private String username;
    private String memberId;
    private String password;
    private String address_line1;
    private String address_line2;
    private String address_line3;
    private String city;
    private String state;
    private String country;
    private String postal_code;
    private String member_id;
//    private List<String> groups = new ArrayList<>();

}
