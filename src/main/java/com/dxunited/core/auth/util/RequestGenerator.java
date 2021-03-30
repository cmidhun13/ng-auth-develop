package com.dxunited.core.auth.util;

import com.dxunited.core.auth.config.OAuth2ClientProperties;
import com.dxunited.core.auth.model.request.*;
import com.dxunited.core.auth.model.spi.UserRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.stream.Stream;

import static com.dxunited.core.auth.constant.AuthConstants.*;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@Component
@RequiredArgsConstructor
public class RequestGenerator {

    private final OAuth2ClientProperties oAuth2ClientProperties;

    private final TenantRealmResolver tenantRealmResolver;

    @Value("${member-fetch-token}")
    private String fetchTokenURI;

    @Value("${forgot-password}")
    private String forgotPsswordURI;


    @Value("${forgot-email}")
    private String forgotEmailURI;

    public URI generateURI(String endpoint, String tenantId, String... args) {
        String hostEndPoint = oAuth2ClientProperties.getAuthServerBaseUrl() + endpoint;
        String realm = tenantRealmResolver.resolveRealm(tenantId);
        return URI.create(
                String.format(hostEndPoint, Stream.concat(Stream.of(realm), Stream.of(args)).toArray())
        );
    }



    public URI generateForFetchToken() {
        return URI.create(
                String.format(fetchTokenURI)
        );
    }

    public HttpEntity<MultiValueMap<String, String>> generateValidateTokenRequest(ValidateTokenRequest validateTokenRequest) {
        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
        formParameters.add(TOKEN, validateTokenRequest.getToken());
        formParameters.addAll(clientFormData(validateTokenRequest.getClientId(), validateTokenRequest.getClientSecret()));
        return new HttpEntity<>(formParameters, getHeader());
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private MultiValueMap<String, String> clientFormData(String clientId, String clientSecret) {
        MultiValueMap<String, String> clientFormData = new LinkedMultiValueMap<>();
        clientFormData.add(CLIENT_ID, clientId);
        clientFormData.add(CLIENT_SECRET, clientSecret);
        return clientFormData;
    }

    public HttpEntity<MultiValueMap<String, String>> generateClientTokenRequest(ClientTokenRequest tokenRequest) {
        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
        formParameters.add(GRANT_TYPE, CLIENT_CREDENTIALS_GRANT_TYPE);
        formParameters.addAll(clientFormData(tokenRequest.getClientId(), tokenRequest.getClientSecret()));
        return new HttpEntity<>(formParameters, getHeader());
    }

    public HttpEntity<MultiValueMap<String, String>> generateUserTokenRequest(UserTokenRequest tokenRequest) {
        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
        formParameters.add(USERNAME, tokenRequest.getUsername());
        formParameters.add(PASSWORD, tokenRequest.getPassword());
        formParameters.add(GRANT_TYPE, PASSWORD_GRANT_TYPE);
        formParameters.addAll(clientFormData(tokenRequest.getClientId(), tokenRequest.getClientSecret()));
        return new HttpEntity<>(formParameters, getHeader());
    }

    public HttpEntity<CredentialRepresentation> resetpasswordRequest(ChangePasswordRequest changePasswordRequest, String tenantId) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(APPLICATION_JSON);
        header.add(TENANT_ID, tenantId);
        return new HttpEntity<>(new CredentialRepresentation(false, PASSWORD, changePasswordRequest.getPassword()), header);
    }


    public HttpEntity<MultiValueMap<String, String>> userInfoRequest(String accessToken) {
        HttpHeaders header = new HttpHeaders();
        header.setContentType(APPLICATION_JSON);
        header.add("Authorization", "Bearer " + accessToken);
        return new HttpEntity<>(null, header);
    }


    public HttpEntity<MultiValueMap<String, String>> generateInvalidateRequest(LogoutRequest logoutRequest) {
        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
        formParameters.add(REFRESH_TOKEN, logoutRequest.getRefreshToken());
        formParameters.addAll(clientFormData(logoutRequest.getClientId(), logoutRequest.getClientSecret()));
        return new HttpEntity<>(formParameters, getHeader());
    }

    public HttpEntity<MultiValueMap<String, String>> generateRefreshTokenRequest(RefreshTokenRequest tokenRequest) {
        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
        formParameters.add(CLIENT_ID, tokenRequest.getClientId());
        formParameters.add(REFRESH_TOKEN, tokenRequest.getRefreshToken());
        formParameters.add(GRANT_TYPE, REFRESH_TOKEN_GRANT_TYPE);
        // List<Sample> e = new ArrayList<Sample>();
        return new HttpEntity<>(formParameters, getHeader());
    }

    public HttpEntity<UserRepresentation> generateUserRequest(UserRepresentation userRepresentation, String tenantId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(TENANT_ID, tenantId);
        headers.setContentType(APPLICATION_JSON);
        return new HttpEntity<>(userRepresentation, headers);
    }

    public HttpEntity<MultiValueMap<String, String>> generateBruteForceAttackDetectionRequest(String tenantId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(TENANT_ID, tenantId);
        headers.setContentType(APPLICATION_JSON);
        return new HttpEntity<>(null, headers);
    }

}
