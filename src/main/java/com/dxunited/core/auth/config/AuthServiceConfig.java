package com.dxunited.core.auth.config;

import com.dxunited.core.auth.constant.AuthConstants;
import com.dxunited.core.auth.constant.ErrorCode;
import com.dxunited.core.auth.exception.AuthServiceException;
import com.dxunited.core.auth.model.request.ClientTokenRequest;
import com.dxunited.core.auth.model.response.TokenResponse;
import com.dxunited.core.auth.util.RequestGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class AuthServiceConfig {

    private final RequestGenerator requestGenerator;

    private final OAuth2ClientProperties oAuth2ClientProperties;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .additionalInterceptors((request, body, execution) -> {
                    if (request.getURI().getPath().contains("admin")) {
                        request.getHeaders().setBearerAuth(getToken(request.getHeaders().getFirst(AuthConstants.TENANT_ID)));
                        request.getHeaders().setContentType(APPLICATION_JSON);
                    }
                    return execution.execute(request, body);
                }).build();
    }

    private String getToken(String tenantId) {
        URI authTokenUri = requestGenerator.generateURI(AuthConstants.GENERATE_TOKEN_ENDPOINT, tenantId);
        HttpEntity<MultiValueMap<String, String>> request = requestGenerator.generateClientTokenRequest(
                getClientTokenRequest());
        return Optional.of(restTemplate().exchange(authTokenUri, POST, request, TokenResponse.class))
                .map(HttpEntity::getBody)
                .map(TokenResponse::getAccessToken)
                .orElseThrow(() -> new AuthServiceException(ErrorCode.ACCESS_TOKEN_ACQUIRE_FAILED.getCode(),""));
    }

    private ClientTokenRequest getClientTokenRequest() {
        ClientTokenRequest clientTokenRequest = new ClientTokenRequest();
        clientTokenRequest.setClientId(oAuth2ClientProperties.getClientId());
        clientTokenRequest.setClientSecret(oAuth2ClientProperties.getClientSecret());
        return clientTokenRequest;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
