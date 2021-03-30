//package com.szells.gce.auth.service;
//
//import com.szells.common.model.ErrorCodesProperties;
//import AuthClient;
//import AuthConstants;
//import LogoutException;
//import LogoutRequest;
//import TenantRealmResolver;
//import RequestGenerator;
//import SuccessLogDetails;
//import com.szells.gce.core.utilities.loggingService.CoreLogger;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.Spy;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.HttpClientErrorException;
//
//import java.net.URI;
//
//import static AuthConstants.*;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.when;
//import static org.mockito.MockitoAnnotations.initMocks;
//import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;
//
//public class LogoutServiceTest {
//
//    @Spy
//    @InjectMocks
//    private LogoutService logoutService;
//
//    @Mock
//    private ErrorCodesProperties errorCodesProperties;
//
//    @Mock
//    private RequestGenerator requestGenerator;
//
//    @Mock
//    private TenantRealmResolver tenantRealmResolver;
//
//    @Mock
//    private AuthClient authClient;
//
//    @Mock
//    private CoreLogger coreLogger;
//
//    @Mock
//    private SuccessLogDetails successLogDetails;
//
//    @BeforeEach
//    public void setUp() {
//        initMocks(this);
//    }
//
//    @Test
//    public void testLogoutSuccess() {
//        Assertions.assertNotNull(getLogoutRequest().getRefreshToken(), "Refresh Token should not be null!");
//        when(tenantRealmResolver.resolveRealm(Mockito.any())).thenReturn("gce");
//        when(requestGenerator.generateURI(Mockito.any(), Mockito.any())).thenReturn(generateURI(AuthConstants.INVALIDATE_TOKEN_ENDPOINT, "gce"));
//        when(requestGenerator.generateInvalidateRequest(Mockito.any())).thenReturn(generateInvalidateRequest(getLogoutRequest()));
//        when(authClient.invokeRest(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(getSuccessResponse());
//        boolean result = logoutService.logout(getLogoutRequest(), "1");
//        assertTrue(result, "logout should be successful");
//    }
//
//    private URI generateURI(String endpoint, String realm) {
//        String hostEndPoint = "http://localhost:8080/auth" + endpoint;
//        return URI.create(String.format(hostEndPoint, realm));
//    }
//
//    private HttpEntity<MultiValueMap<String, String>> generateInvalidateRequest(LogoutRequest logoutRequest) {
//        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
//        formParameters.add(REFRESH_TOKEN, logoutRequest.getRefreshToken());
//        formParameters.add(CLIENT_ID, logoutRequest.getClientId());
//        formParameters.add(CLIENT_SECRET, logoutRequest.getClientSecret());
//        return new HttpEntity(formParameters, getHeader());
//    }
//
//    private HttpHeaders getHeader() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(APPLICATION_FORM_URLENCODED);
//        return headers;
//    }
//
//    @Test
//    public void testLogoutFailure() {
//        Assertions.assertNotNull(getLogoutRequest().getRefreshToken(), "Refresh Token should not be null!");
//        when(tenantRealmResolver.resolveRealm(Mockito.any())).thenReturn("gce");
//        when(requestGenerator.generateURI(Mockito.any(), Mockito.any())).thenReturn(generateURI(AuthConstants.INVALIDATE_TOKEN_ENDPOINT, "gce"));
//        when(requestGenerator.generateInvalidateRequest(Mockito.any())).thenReturn(generateInvalidateRequest(getLogoutRequest()));
//        when(authClient.invokeRest(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any())).thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request!", "{\"error_description\": \"Failed to invalidate user session!\"}".getBytes(), null));
//        assertThrows(LogoutException.class,
//                () -> logoutService.logout(getLogoutRequest(), "1"),
//                "Failed to invalidate user session!");
//    }
//
//    private LogoutRequest getLogoutRequest() {
//        LogoutRequest logoutRequest = new LogoutRequest();
//        logoutRequest.setClientId("gce-auth-service");
//        logoutRequest.setClientSecret("22245555-f719-4adc-a8da-2128a6da4c1f");
//        logoutRequest.setRefreshToken("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJiSFJ1bFg4R3ZWeWZEVjlrU0ZNc1AzMFoydE1td2NCRXpUVElpUGRkZDFzIn0.eyJqdGkiOiJiYzViMGJkZi1lZTRjLTQyYjgtYTlkMi03MjAxYjExZmVhZjMiLCJleHAiOjE1ODI1NDgxMjcsIm5iZiI6MCwiaWF0IjoxNTgyNTQ3ODI3LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvZ2NlIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjJkZmY3MzA5LTEwZjAtNGRkMi1iY2I4LTc2NjBiODIyNzU4NyIsInR5cCI6IkJlYXJlciIsImF6cCI6ImdjZS1hdXRoLXNlcnZpY2UiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiIyMjhlMWE1NC01Y2I1LTQwNDMtYmQ3NC03YmE1MmI2Mzc4MDAiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJST0xFX1NPVVJDRV9TWVNURU0iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJtZW1iZXJfaWQiOiIxMjM0NTYiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IldlYmxveWFsdHkgVXNlciIsInByZWZlcnJlZF91c2VybmFtZSI6IndlYmxveWFsdHkiLCJnaXZlbl9uYW1lIjoiV2VibG95YWx0eSIsImZhbWlseV9uYW1lIjoiVXNlciIsImVtYWlsIjoiYnJlZGR5QHRhdmlzY2EuY29tIn0.Cl5nN4ssCXo8Fnpd5wKpEElUgqThP26-4KpB05QR-nEtJHKwiC5yVajv7EwL9BH6okOsFSznKYxM3ZYqo1cydOJ_0GtnMGA6ppsm0lz0gdOvehCC8z45dCQu5T6RNN_KPsdVvgBG7SwDxqQOPIaU-9DC-TYBFFeT7HpT2IfGoIsAXzxmEPE0T5vc3CadZd4R05g1MKYjXvY3X9aZTqSMXYUcCex5Y458y6p_CiniRYtz8VfBdr8-_fXhHlTnVhE3Syeqsi7PlcoaMvgAVZ2lW8aE4uVIYc37PjyWGfTmPNMGqLYylDOYxQBxE3bjMwqlvV_d3g4jjO8L1nSBAqLIRQ");
//        return logoutRequest;
//    }
//
//    private ResponseEntity getSuccessResponse() {
//        return new ResponseEntity(HttpStatus.NO_CONTENT);
//    }
//
//
//}
