package com.szells.gce.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.szells.gce.auth.model.request.ClientTokenRequest;
import com.szells.gce.auth.model.request.CreateUserRequest;
import com.szells.gce.auth.model.request.UserTokenRequest;
import com.szells.gce.auth.model.request.ValidateTokenRequest;
import com.szells.gce.auth.model.spi.Credential;
import com.szells.gce.auth.model.spi.UserRepresentation;

import java.util.List;

public class TestObjectFactory {

    public static ValidateTokenRequest getValidateTokenRequest() throws JsonProcessingException {
        ValidateTokenRequest validateTokenRequest = ValidateTokenRequest.builder()
                .clientId("employee-service")
                .clientSecret("232fb857-1c26-49dc-a32d-1f9d3f69d800")
                .token("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJ3ZXpZVWZaLTl6NHg4YjljYkNsbnJJODdnNTUtbnR6RjhieXdhOVNpOVUwIn0.eyJqdGkiOiIwYjg5Mzk1Yi00YWI0LTRkYmMtOGM2OS1hMzZmNDZkNDc2MTYiLCJleHAiOjE1ODI2NDc2NjQsIm5iZiI6MCwiaWF0IjoxNTgyNjQ3NDg0LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvZGV2IiwiYXVkIjpbInJlYWxtLW1hbmFnZW1lbnQiLCJhY2NvdW50Il0sInN1YiI6IjFjZmNlYmQzLTdmZTQtNGE3Yi1iNDA0LWE3ODI4YWVjZjk0NyIsInR5cCI6IkJlYXJlciIsImF6cCI6ImVtcGxveWVlLXNlcnZpY2UiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiJhZDI1YTZjYy1iMDU2LTQyNzUtOTI0Ni1iN2MxOGY2NzIyMWYiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iXX0sInJlc291cmNlX2FjY2VzcyI6eyJyZWFsbS1tYW5hZ2VtZW50Ijp7InJvbGVzIjpbIm1hbmFnZS11c2VycyIsInZpZXctdXNlcnMiLCJxdWVyeS1ncm91cHMiLCJxdWVyeS11c2VycyJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19LCJlbXBsb3llZS1zZXJ2aWNlIjp7InJvbGVzIjpbInVtYV9wcm90ZWN0aW9uIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJjbGllbnRIb3N0IjoiMTI3LjAuMC4xIiwiY2xpZW50SWQiOiJlbXBsb3llZS1zZXJ2aWNlIiwidXNlcl9uYW1lIjoic2VydmljZS1hY2NvdW50LWVtcGxveWVlLXNlcnZpY2UiLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJzZXJ2aWNlLWFjY291bnQtZW1wbG95ZWUtc2VydmljZSIsImNsaWVudEFkZHJlc3MiOiIxMjcuMC4wLjEifQ.dDdHcpNwkhrxxwf3UdJLBRyykMe3bdjPfWlbl3M9IQxBFjeO_J95hNiJEfC4gvLjLUpsbHFm8fN_8RL4wB6MuuZXzB_xboy-2gC5X7v5hFfJYOwfh9w8_UzcBd1zSk8X7suIlfdWv8753rvFC2DYszGQ5jU99UqxYiw0Dzukr_fRJWHPPrOjrdfCGLT5g6UQpmRUl0sbYvs5uBuF84p0WQ-Ny6-h8TGlIO3o21QFVFQh484gV8fF1QePzZXVuvH3-LbLIAa1NN7tU0lpexe5fPj1uwxB696XpoW4uN3cL1yTHcmDqWpKA6kWQksu1N_u9AN0JRYMcAUUe4G9325k7w")
                .build();
        return validateTokenRequest;
    }

    public static ClientTokenRequest getClientTokenRequest() {
        ClientTokenRequest tokenRequest = new ClientTokenRequest();
        tokenRequest.setClientId("signin-service");
        tokenRequest.setClientSecret("d214c815-7bcc-4a14-b58f-b26220553911");
        return tokenRequest;
    }

    public static UserTokenRequest getUserTokenRequest() {
        UserTokenRequest userTokenRequest = new UserTokenRequest();
        userTokenRequest.setUsername("adas");
        userTokenRequest.setPassword("adas");
        userTokenRequest.setClientId("signin-service");
        userTokenRequest.setClientSecret("d214c815-7bcc-4a14-b58f-b26220553911");
        return userTokenRequest;
    }

    public static CreateUserRequest createUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("xyz@gmail.com");
        createUserRequest.setMemberId("123456");
        createUserRequest.setPassword("12345678");
        return createUserRequest;
    }

    public static Credential credential() {
        return Credential.builder()
                .type("password")
                .value("12345678")
                .build();
    }

    public static UserRepresentation userRepresentation() {
        return UserRepresentation.builder()
                .username("xyz@gmail.com")
                .credentials(List.of(credential()))
                .build();
    }


}
