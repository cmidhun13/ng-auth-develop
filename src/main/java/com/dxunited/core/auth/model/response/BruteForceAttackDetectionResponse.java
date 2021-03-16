package com.dxunited.core.auth.model.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BruteForceAttackDetectionResponse {
    private boolean disabled;
}
