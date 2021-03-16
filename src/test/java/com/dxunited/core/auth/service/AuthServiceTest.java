//package com.szells.gce.auth.service;
//
//import AuthClient;
//import ClientTokenRequest;
//import ValidateTokenRequest;
//import TokenResponse;
//import ValidateTokenResponse;
//import TenantRealmResolver;
//import RequestGenerator;
//import SuccessLogDetails;
//import com.szells.gce.core.utilities.loggingService.CoreLogger;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.http.*;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import java.net.URI;
//
//import static TestObjectFactory.*;
//import static AuthConstants.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//
//public class AuthServiceTest {
//
//    @InjectMocks
//    AuthService authService;
//
//    @Mock
//    TenantRealmResolver mockTenantRealmResolver;
//
//    @Mock
//    RequestGenerator mockRequestGenerator;
//
//    @Mock
//    AuthClient authClient;
//
//    @Mock
//    private  SuccessLogDetails successLogDetails;
//
//
//    @Mock
//    private CoreLogger coreLogger;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testForValidateToken() throws JsonProcessingException {
//        ValidateTokenResponse validateTokenResponse=new ValidateTokenResponse();
//        validateTokenResponse.setMemberId("memberid");
//        when(mockTenantRealmResolver.resolveRealm(anyString())).thenReturn(getRealm());
//
//        when(mockRequestGenerator.generateURI(anyString(),anyString()))
//                .thenReturn(getUriFromString("http://localhost:8080/auth/realms/dev/protocol/openid-connect/token"));
//
//        when(mockRequestGenerator.generateValidateTokenRequest(any(ValidateTokenRequest.class)))
//                .thenReturn(getGenerateValidateTokenRequest(getValidateTokenRequest()));
//
//        when(authClient.invoke(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
//                .thenReturn(validateTokenResponse);
//
//        ValidateTokenResponse actualValidateTokenResponse = authService.validateToken(getValidateTokenRequest(), "1");
//        assertEquals(validateTokenResponse.getMemberId() ,actualValidateTokenResponse.getMemberId());
//    }
//
//    @Test
//    public void testForGenerateToken() throws JsonProcessingException {
//        TokenResponse tokenResponse=new TokenResponse();
//        tokenResponse.setAccessToken("accesstoken");
//        when(mockTenantRealmResolver.resolveRealm(anyString()))
//                .thenReturn(getRealm());
//        when(mockRequestGenerator.generateURI(anyString(),anyString()))
//                .thenReturn(getUriFromString("http://localhost:8080/auth/realms/dev/protocol/openid-connect/token"));
//        when(mockRequestGenerator.generateClientTokenRequest(any(ClientTokenRequest.class)))
//                .thenReturn(getGenerateClientTokenRequest(getClientTokenRequest()));
//
//
//        when(authClient.invoke(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
//                .thenReturn(tokenResponse);
//
//        TokenResponse actualTokenResponse = authService.generateToken(getClientTokenRequest(), "1");
//        assertEquals(tokenResponse.getAccessToken() ,actualTokenResponse.getAccessToken());
//    }
//
//    private URI getUriFromString(String string){
//        return URI.create(string);
//    }
//
//    private HttpEntity<MultiValueMap<String, String>> getGenerateValidateTokenRequest(ValidateTokenRequest validateTokenRequest) {
//        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
//        formParameters.add(TOKEN, validateTokenRequest.getToken());
//        formParameters.add(CLIENT_ID, "123");
//        formParameters.add(CLIENT_SECRET, "abc123");
//        return new HttpEntity<>(formParameters, getHeader());
//    }
//
//    private HttpEntity<MultiValueMap<String, String>> getGenerateClientTokenRequest(ClientTokenRequest clientTokenRequest) {
//        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
//        formParameters.add(GRANT_TYPE, CLIENT_CREDENTIALS_GRANT_TYPE);
//        formParameters.add(CLIENT_ID, "123");
//        formParameters.add(CLIENT_SECRET, "abc123");
//        return new HttpEntity<>(formParameters, getHeader());
//    }
//
//    private HttpHeaders getHeader() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        return headers;
//    }
//
//    private String getRealm(){
//        return "Rbs";
//    }
//
//    private JsonNode getJsonNode() throws JsonProcessingException {
//            return new ObjectMapper().readTree("{\"client_id\" : \"1\",\n" +
//                    " \"access_token\" : \"avsd\"}");
//    }
//}
