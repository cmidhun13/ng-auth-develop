//package com.szells.gce.auth.service;
//
//import AuthClient;
//import LoginFailureAttempt;
//import AuthServiceException;
//import UserAuthenticationException;
//import UserTokenRequest;
//import ValidateTokenRequest;
//import BruteForceAttackDetectionResponse;
//import TokenResponse;
//import ValidateTokenResponse;
//import UserRepresentation;
//import TenantRealmResolver;
//import RequestGenerator;
//import SuccessLogDetails;
//import com.szells.gce.core.utilities.loggingService.CoreLogger;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.*;
//import org.springframework.http.*;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.HttpClientErrorException;
//
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Optional;
//
//import static AuthConstants.*;
//import static AuthConstants.PASSWORD_GRANT_TYPE;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.when;
//import static TestObjectFactory.getUserTokenRequest;
//import static org.springframework.http.MediaType.APPLICATION_JSON;
//
//public class UserAuthenticationServiceTest {
//
//    @InjectMocks
//    private UserAuthenticationService userAuthenticationService;
//    @Mock
//    private RequestGenerator requestGenerator;
//    @Mock
//    private TenantRealmResolver tenantRealmResolver;
//    @Mock
//    private AuthClient authClient;
//    @Mock
//    private AuthService authService;
//    @Mock
//    private UserService userService;
//    @Mock
//    private LoginFailureAttemptService loginFailureAttemptService;
//    @Mock
//    private CoreLogger coreLogger;
//    @Mock
//    private SuccessLogDetails successLogDetails;
//
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testForGenerateTokenForUser() throws JsonProcessingException {
//        when(tenantRealmResolver.resolveRealm(anyString()))
//                .thenReturn(getRealm());
//        when(requestGenerator.generateURI(anyString(),anyString()))
//                .thenReturn(getUriFromString("http://localhost:8080/auth/realms/dev/protocol/openid-connect/token"));
//        when(requestGenerator.generateUserTokenRequest(any(UserTokenRequest.class)))
//                .thenReturn(getGenerateUserTokenRequest(getUserTokenRequest()));
//        when(authClient.invoke(any(URI.class), any(HttpMethod.class), any(HttpEntity.class), any(Class.class)))
//                .thenReturn(getTokenResponse());
//        when(authService.validateToken( any(ValidateTokenRequest.class), anyString()))
//                .thenReturn(getValidateTokenResponse());
//        when(loginFailureAttemptService.getLoginAttempt(anyString(), anyString()))
//                .thenReturn(Optional.of(getLoginFailureAttempt()));
//        Mockito.doNothing().when(loginFailureAttemptService).deleteLoginAttempt(getLoginFailureAttempt());
//        TokenResponse tokenResponse = userAuthenticationService.generateTokenForUser(getUserTokenRequest(), "1");
//        assertEquals("2323sd", tokenResponse.getAccessToken());
//    }
//
//    @Test
//    public void testForAccountLock(){
//        when(tenantRealmResolver.resolveRealm(anyString()))
//                .thenReturn(getRealm());
//        URI tokenURI = getUriFromString("http://localhost:8080/auth/realms/dev/protocol/openid-connect/token");
//        when(requestGenerator.generateURI(anyString(),anyString()))
//                .thenReturn(tokenURI);
//
//        HttpEntity<MultiValueMap<String, String>> userRequest = getGenerateUserTokenRequest(getUserTokenRequest());
//        when(requestGenerator.generateUserTokenRequest(any(UserTokenRequest.class)))
//                .thenReturn(userRequest);
//
//        when(authClient.invoke(tokenURI, HttpMethod.POST, userRequest, TokenResponse.class))
//                .thenThrow(HttpClientErrorException.class);
//
//        when(userService.getUser(Mockito.any(),Mockito.any()))
//                .thenReturn(getUserRepresentation());
//
//        URI bruteForURI = getUriFromString("http://localhost:8080/auth/admin/realms/dev/attack-detection/brute-force/users/userId");
//        when(requestGenerator.generateURI(anyString(),anyString(),anyString()))
//                .thenReturn(bruteForURI);
//
//        HttpEntity<MultiValueMap<String, String>> bruteForceReq = generateBruteForceAttackDetectionRequest();
//        when(requestGenerator.generateBruteForceAttackDetectionRequest(anyString()))
//                .thenReturn(bruteForceReq);
//
//        BruteForceAttackDetectionResponse response = new BruteForceAttackDetectionResponse();
//        response.setDisabled(true);
//        when(authClient.invoke(bruteForURI, HttpMethod.GET, bruteForceReq, BruteForceAttackDetectionResponse.class))
//                .thenReturn(response);
//        assertThrows(AuthServiceException.class, () -> {
//            userAuthenticationService.generateTokenForUser(getUserTokenRequest(), "1");
//        });
//    }
//
//    @Test
//    public void testForCreateLoginAttempt() throws JsonProcessingException {
//        when(tenantRealmResolver.resolveRealm(anyString()))
//                .thenReturn(getRealm());
//        URI tokenURI = getUriFromString("http://localhost:8080/auth/realms/dev/protocol/openid-connect/token");
//        when(requestGenerator.generateURI(anyString(),anyString()))
//                .thenReturn(tokenURI);
//
//        HttpEntity<MultiValueMap<String, String>> userRequest = getGenerateUserTokenRequest(getUserTokenRequest());
//        when(requestGenerator.generateUserTokenRequest(any(UserTokenRequest.class)))
//                .thenReturn(userRequest);
//
//        when(authClient.invoke(tokenURI, HttpMethod.POST, userRequest, TokenResponse.class))
//                .thenThrow(HttpClientErrorException.class);
//
//        when(userService.getUser(Mockito.any(),Mockito.any()))
//                .thenReturn(getUserRepresentation());
//
//        URI bruteForURI = getUriFromString("http://localhost:8080/auth/admin/realms/dev/attack-detection/brute-force/users/userId");
//        when(requestGenerator.generateURI(anyString(),anyString(),anyString()))
//                .thenReturn(bruteForURI);
//
//        HttpEntity<MultiValueMap<String, String>> bruteForceReq = generateBruteForceAttackDetectionRequest();
//        when(requestGenerator.generateBruteForceAttackDetectionRequest(anyString()))
//                .thenReturn(bruteForceReq);
//
//        when(authClient.invoke(bruteForURI, HttpMethod.GET, bruteForceReq, BruteForceAttackDetectionResponse.class))
//        .thenReturn(new BruteForceAttackDetectionResponse());
//
//        when(loginFailureAttemptService.getLoginAttempt(anyString(), anyString()))
//                .thenReturn(Optional.of(getLoginFailureAttempt()));
//        Mockito.doNothing().when(loginFailureAttemptService).saveLoginAttempt(getLoginFailureAttempt());
//
//        Mockito.doNothing().when(loginFailureAttemptService).deleteLoginAttempt(any(LoginFailureAttempt.class));
//
//        assertThrows(UserAuthenticationException.class, () -> {
//            userAuthenticationService.generateTokenForUser(getUserTokenRequest(), "1");
//        });
//    }
//
//    private UserRepresentation getUserRepresentation() {
//        HashMap<String,Object> attributes=new HashMap<>();
//        List<String> list = new ArrayList<>();
//        list.add("123");
//        attributes.put("member_id", list);
//        return UserRepresentation.builder()
//                .id("userId")
//                .attributes(attributes)
//                .build();
//    }
//
//    private LoginFailureAttempt getLoginFailureAttempt() {
//        LoginFailureAttempt loginFailureAttempt = new LoginFailureAttempt();
//        loginFailureAttempt.setTenantId("123");
//        loginFailureAttempt.setMemberId("abc123");
//        return loginFailureAttempt;
//    }
//
//    private HttpEntity<MultiValueMap<String, String>> getGenerateUserTokenRequest(UserTokenRequest tokenRequest) {
//        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
//        formParameters.add(USERNAME, tokenRequest.getUsername());
//        formParameters.add(PASSWORD, tokenRequest.getPassword());
//        formParameters.add(GRANT_TYPE, PASSWORD_GRANT_TYPE);
//        formParameters.add(CLIENT_ID, tokenRequest.getClientId());
//        formParameters.add(CLIENT_SECRET, tokenRequest.getClientSecret());
//        return new HttpEntity<>(formParameters, getHeader());
//    }
//
//    private HttpEntity<MultiValueMap<String, String>> getGenerateUserTokenRequestWithoutGrantType(UserTokenRequest tokenRequest) {
//        MultiValueMap<String, String> formParameters = new LinkedMultiValueMap<>();
//        formParameters.add(USERNAME, tokenRequest.getUsername());
//        formParameters.add(PASSWORD, tokenRequest.getPassword());
//
//        return new HttpEntity<>(formParameters, getHeader());
//    }
//
//    private HttpEntity<MultiValueMap<String, String>> generateBruteForceAttackDetectionRequest() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(TENANT_ID, "tenantId");
//        headers.setContentType(APPLICATION_JSON);
//        return new HttpEntity<>(null, headers);
//    }
//
//    private HttpHeaders getHeader() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        return headers;
//    }
//
//    private JsonNode getJsonNode() throws JsonProcessingException {
//        return new ObjectMapper().readTree(
//                "{  \"access_token\" : \"abc123\",\n" +
//                        "\"member_id\" : [\"123\"]  }");
//    }
//
//    private JsonNode getJsonNodeWithoutMemId() throws JsonProcessingException {
//        return new ObjectMapper().readTree(
//                "{  \"access_token\" : \"abc123\" }");
//    }
//
//    private URI getUriFromString(String uri){
//        return URI.create(uri);
//    }
//
//    private String getRealm(){
//        return "Rbs";
//    }
//
//    private TokenResponse getTokenResponse(){
//        TokenResponse tokenResponse=new TokenResponse();
//        tokenResponse.setAccessToken("2323sd");
//        return tokenResponse;
//    }
//    private ValidateTokenResponse getValidateTokenResponse() {
//        ValidateTokenResponse response = new ValidateTokenResponse();
//        response.setMemberId("123");
//        return response;
//    }
//}
