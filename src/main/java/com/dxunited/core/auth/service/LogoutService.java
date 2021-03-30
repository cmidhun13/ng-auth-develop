package com.dxunited.core.auth.service;

//import com.szells.common.model.ErrorCodesProperties;

import com.dxunited.core.auth.client.AuthClient;
import com.dxunited.core.auth.config.OAuth2ClientProperties;
import com.dxunited.core.auth.constant.AuthConstants;
import com.dxunited.core.auth.exception.LogoutException;
import com.dxunited.core.auth.model.request.LogoutRequest;
import com.dxunited.core.auth.model.response.GenericResponse;
import com.dxunited.core.auth.util.RequestGenerator;
import com.dxunited.core.auth.util.SuccessLogDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;

import java.net.URI;
import java.util.Map;

//import com.szells.gce.core.utilities.loggingService.CoreLogger;

@Service
@RequiredArgsConstructor
public class LogoutService {

//    private final ErrorCodesProperties errorCodesProperties;

    private final RequestGenerator requestGenerator;

    private final AuthClient authClient;

    private final SuccessLogDetails successLogDetails;

    private final OAuth2ClientProperties oAuth2ClientProperties;

//    private final CoreLogger coreLogger;

    public GenericResponse logout(LogoutRequest logoutRequest, String tenantId) {
        URI uri = requestGenerator.generateURI(AuthConstants.INVALIDATE_TOKEN_ENDPOINT, tenantId);
        try {
            logoutRequest.setClientId(oAuth2ClientProperties.getClientId());
            logoutRequest.setClientSecret(oAuth2ClientProperties.getClientSecret());
            HttpEntity<MultiValueMap<String, String>> request = requestGenerator.generateInvalidateRequest(logoutRequest);
            ResponseEntity responseEntity = authClient.invokeRest(uri, HttpMethod.POST, request, JsonNode.class);
            //ClientTokenResponse tokenResponse  = authClient.invoke(uri, HttpMethod.POST, request,ClientTokenResponse.class);
//           coreLogger.log("Token invalidated successfully", Level.INFO, successLogDetails.getLogDetails(Arrays.asList("clientSecret"), logoutRequest, responseEntity));
            //  return responseEntity.getStatusCode() == HttpStatus.NO_CONTENT;
            //
            return new GenericResponse(true, HttpStatus.OK.value(), "Logout Successfull", "", String.valueOf(responseEntity.getStatusCode()));
//
//
        } catch (HttpClientErrorException ex) {
            try {
                Map errResponseBody = new ObjectMapper().readValue(ex.getResponseBodyAsString(), Map.class);
                String errorMessage = String.valueOf(errResponseBody.get("error_description"));
                errorMessage = "null".equals(errorMessage) ? String.valueOf(errResponseBody.get("error")) : errorMessage;
                throw new LogoutException(null, errorMessage);
            } catch (JsonProcessingException e) {

                throw new RuntimeException(e);
            }
        }
    }
}
