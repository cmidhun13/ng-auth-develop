package com.dxunited.core.auth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2")
@Getter
@Setter
public class OAuth2ClientProperties {
    private String clientId;
    private String clientSecret;
    private String authServerBaseUrl;
}
