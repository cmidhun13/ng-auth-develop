package com.dxunited.core.auth.service;

import com.dxunited.core.auth.client.AuthClient;
import com.dxunited.core.auth.constant.AuthConstants;
import com.dxunited.core.auth.model.request.ClientTokenRequest;
import com.dxunited.core.auth.model.request.RefreshTokenRequest;
import com.dxunited.core.auth.model.request.ValidateTokenRequest;
import com.dxunited.core.auth.model.response.TokenResponse;
import com.dxunited.core.auth.model.response.ValidateTokenResponse;
import com.dxunited.core.auth.util.RequestGenerator;
import com.dxunited.core.auth.util.SuccessLogDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.net.URI;

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
        URI authTokenUri = requestGenerator.generateURI(AuthConstants.GENERATE_TOKEN_ENDPOINT, tenantId);
        HttpEntity<MultiValueMap<String, String>> request = requestGenerator.generateClientTokenRequest(tokenRequest);
        TokenResponse response = authClient.invoke(authTokenUri, POST, request, TokenResponse.class);
//        coreLogger.log("Token generated successfully", Level.INFO, successLogDetails.getLogDetails(List.of("clientSecret"), tokenRequest, response));
        return response;
    }

    public ValidateTokenResponse validateToken(ValidateTokenRequest validateTokenRequest, String tenantId) {
        URI authTokenUri = requestGenerator.generateURI(AuthConstants.VALIDATE_TOKEN_ENDPOINT, tenantId);
        HttpEntity<MultiValueMap<String, String>> request = requestGenerator.generateValidateTokenRequest(validateTokenRequest);
        ValidateTokenResponse response = authClient.invoke(authTokenUri, POST, request, ValidateTokenResponse.class);
//        coreLogger.log("Token validated successfully", Level.INFO, successLogDetails.getLogDetails(List.of("clientSecret"), validateTokenRequest, response));
        return response;
    }

    public TokenResponse generateWithRefreshToken(RefreshTokenRequest tokenRequest, String tenantId) {
        URI authTokenUri = requestGenerator.generateURI(AuthConstants.GENERATE_TOKEN_ENDPOINT, tenantId);
        HttpEntity<MultiValueMap<String, String>> request = requestGenerator.generateRefreshTokenRequest(tokenRequest);
        TokenResponse response = authClient.invoke(authTokenUri, POST, request, TokenResponse.class);
//        coreLogger.log("Token generated successfully", Level.INFO, successLogDetails.getLogDetails(List.of("clientSecret"), tokenRequest, response));
        return response;
    }
}
