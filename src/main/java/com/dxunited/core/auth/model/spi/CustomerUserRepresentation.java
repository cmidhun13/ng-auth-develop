package com.dxunited.core.auth.model.spi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUserRepresentation {
    private String username;
    private String password;
    private Long customerId;
    @Builder.Default
    private Map<String, Object> attributes = new HashMap<>();
    private List<Credential> credentials;
    @Builder.Default
    private List<String> groups = new ArrayList<>();
}
