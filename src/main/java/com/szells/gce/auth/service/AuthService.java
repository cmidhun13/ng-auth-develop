package com.szells.gce.auth.service;

import com.szells.gce.auth.client.AuthClient;
import com.szells.gce.auth.model.request.ClientTokenRequest;
import com.szells.gce.auth.model.request.RefreshTokenRequest;
import com.szells.gce.auth.model.request.ValidateTokenRequest;
import com.szells.gce.auth.model.response.TokenResponse;
import com.szells.gce.auth.model.response.ValidateTokenResponse;
import com.szells.gce.auth.util.RequestGenerator;
import com.szells.gce.auth.util.SuccessLogDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.net.URI;

import static com.szells.gce.auth.constant.AuthConstants.GENERATE_TOKEN_ENDPOINT;
import static com.szells.gce.auth.constant.AuthConstants.VALIDATE_TOKEN_ENDPOINT;
import static org.springframework.http.HttpMethod.POST;

//import com.szells.gce.core.utilities.loggingService.CoreLogger;

@Service
@Log4j2
@RequiredArgsConstructor
public class AuthService {

    private final SuccessLogDetails successLogDetails;

    private final RequestGenerator requestGenerator;

    private final AuthClient authClient;

//    private final CoreLogger coreLogger;

    public TokenResponse generateToken(ClientTokenRequest tokenRequest, String tenantId) {
        URI authTokenUri = requestGenerator.generateURI(GENERATE_TOKEN_ENDPOINT, tenantId);
        HttpEntity<MultiValueMap<String, String>> request = requestGenerator.generateClientTokenRequest(tokenRequest);
        TokenResponse response = authClient.invoke(authTokenUri, POST, request, TokenResponse.class);
//        coreLogger.log("Token generated successfully", Level.INFO, successLogDetails.getLogDetails(List.of("clientSecret"), tokenRequest, response));
        return response;
    }

    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest, String tenantId) {
        URI authTokenUri = requestGenerator.generateURI(VALIDATE_TOKEN_ENDPOINT, tenantId);
        HttpEntity<MultiValueMap<String, String>> request = requestGenerator.generateValidateTokenRequest(validateTokenRequest);
        ValidateTokenResponse response = authClient.invoke(authTokenUri, POST, request, ValidateTokenResponse.class);
//        coreLogger.log("Token validated successfully", Level.INFO, successLogDetails.getLogDetails(List.of("clientSecret"), validateTokenRequest, response));
        return response;
    }

    public TokenResponse generateWithRefreshToken(RefreshTokenRequest tokenRequest, String tenantId) {
        URI authTokenUri = requestGenerator.generateURI(GENERATE_TOKEN_ENDPOINT, tenantId);
        HttpEntity<MultiValueMap<String, String>> request = requestGenerator.generateRefreshTokenRequest(tokenRequest);
        TokenResponse response = authClient.invoke(authTokenUri, POST, request, TokenResponse.class);
//        coreLogger.log("Token generated successfully", Level.INFO, successLogDetails.getLogDetails(List.of("clientSecret"), tokenRequest, response));
        return response;
    }
}
