package com.szells.gce.auth.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.szells.gce.auth.model.request.ClientTokenRequest;
import com.szells.gce.auth.model.request.ValidateTokenRequest;
import com.szells.gce.auth.model.response.TokenResponse;
import com.szells.gce.auth.model.response.ValidateTokenResponse;
import com.szells.gce.auth.service.AuthService;
import com.szells.gce.auth.service.UserAuthenticationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.szells.gce.auth.TestObjectFactory.getClientTokenRequest;
import static com.szells.gce.auth.TestObjectFactory.getValidateTokenRequest;
import static com.szells.gce.auth.constant.AuthConstants.TENANT_ID;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private UserAuthenticationService userAuthenticationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @Test
    public void testForValidateToken() throws Exception {
        ValidateTokenResponse expectedValidateTokenesponse = new ValidateTokenResponse();
        expectedValidateTokenesponse.setMemberId("dfdsfdsfds");
        when(authService.validateToken(any(ValidateTokenRequest.class), anyString()))
                .thenReturn(expectedValidateTokenesponse);

        mvc.perform(MockMvcRequestBuilders.post("/auth/introspect")
                .content(new ObjectMapper().writeValueAsString(getValidateTokenRequest()))
                .contentType(MediaType.APPLICATION_JSON)
                .header(TENANT_ID, 1))
                .andExpect(status().isOk());
    }

    @Test
    public void testForGenerateToken() throws Exception {
        TokenResponse tokenResponse = new TokenResponse();
        tokenResponse.setAccessToken("fdjgkgjds");
        when(authService.generateToken(any(ClientTokenRequest.class), anyString()))
                .thenReturn(tokenResponse);
        mvc.perform(MockMvcRequestBuilders.post("/auth/token")
                .content(new ObjectMapper().writeValueAsString(getClientTokenRequest()))
                .header(TENANT_ID, 1)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private JsonNode getJsonNode() throws JsonProcessingException {
        return new ObjectMapper().readTree("{\"clientId\": \"employee-service\"}");
    }

}