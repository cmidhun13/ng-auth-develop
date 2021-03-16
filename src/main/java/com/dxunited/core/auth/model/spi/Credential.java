package com.dxunited.core.auth.model.spi;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Credential {
    private Boolean temporary;
    private String type;
    private String value;
}
