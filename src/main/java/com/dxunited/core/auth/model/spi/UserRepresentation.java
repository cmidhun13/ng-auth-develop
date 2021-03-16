package com.dxunited.core.auth.model.spi;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
public class UserRepresentation {
    private String id;
    @Builder.Default
    private Map<String, Object> attributes = new HashMap<>();
    private List<Credential> credentials;
    private String email;
    @Builder.Default
    private Boolean enabled = true;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String memberId;

    @Builder.Default
    private List<String> groups = new ArrayList<>();
}
