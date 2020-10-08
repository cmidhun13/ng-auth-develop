package com.szells.gce.auth.controller;

import com.szells.gce.auth.model.request.LogoutRequest;
import com.szells.gce.auth.service.LogoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class LogoutControllerTest {
    @Mock
    LogoutService logoutService;

    @Spy
    @InjectMocks
    LogoutController logoutController;

    @BeforeEach
    public void setUp() {
        initMocks(this);
    }

    @Test
    public void testLogoutSuccess() throws Exception {
        /*final ResponseEntity responseExpected = new ResponseEntity(getResponse(), HttpStatus.OK);
        when(logoutService.logout(Mockito.any(), Mockito.any())).thenReturn(true);

        final ResponseEntity result = logoutController.logout(getLogoutRequest(), "a252d390-61e6-4256-bd29-824cfe1ca92f");
        // Verify the results
        assertEquals(responseExpected, result);*/
    }

    private String getResponse() {
        return "User successfully logged out.";
    }

    private LogoutRequest getLogoutRequest() {
        LogoutRequest logoutRequest = new LogoutRequest();
        logoutRequest.setRefreshToken("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJiSFJ1bFg4R3ZWeWZEVjlrU0ZNc1AzMFoydE1td2NCRXpUVElpUGRkZDFzIn0.eyJqdGkiOiJiYzViMGJkZi1lZTRjLTQyYjgtYTlkMi03MjAxYjExZmVhZjMiLCJleHAiOjE1ODI1NDgxMjcsIm5iZiI6MCwiaWF0IjoxNTgyNTQ3ODI3LCJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODAvYXV0aC9yZWFsbXMvZ2NlIiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjJkZmY3MzA5LTEwZjAtNGRkMi1iY2I4LTc2NjBiODIyNzU4NyIsInR5cCI6IkJlYXJlciIsImF6cCI6ImdjZS1hdXRoLXNlcnZpY2UiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiIyMjhlMWE1NC01Y2I1LTQwNDMtYmQ3NC03YmE1MmI2Mzc4MDAiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwidW1hX2F1dGhvcml6YXRpb24iLCJST0xFX1NPVVJDRV9TWVNURU0iXX0sInJlc291cmNlX2FjY2VzcyI6eyJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJtZW1iZXJfaWQiOiIxMjM0NTYiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwibmFtZSI6IldlYmxveWFsdHkgVXNlciIsInByZWZlcnJlZF91c2VybmFtZSI6IndlYmxveWFsdHkiLCJnaXZlbl9uYW1lIjoiV2VibG95YWx0eSIsImZhbWlseV9uYW1lIjoiVXNlciIsImVtYWlsIjoiYnJlZGR5QHRhdmlzY2EuY29tIn0.Cl5nN4ssCXo8Fnpd5wKpEElUgqThP26-4KpB05QR-nEtJHKwiC5yVajv7EwL9BH6okOsFSznKYxM3ZYqo1cydOJ_0GtnMGA6ppsm0lz0gdOvehCC8z45dCQu5T6RNN_KPsdVvgBG7SwDxqQOPIaU-9DC-TYBFFeT7HpT2IfGoIsAXzxmEPE0T5vc3CadZd4R05g1MKYjXvY3X9aZTqSMXYUcCex5Y458y6p_CiniRYtz8VfBdr8-_fXhHlTnVhE3Syeqsi7PlcoaMvgAVZ2lW8aE4uVIYc37PjyWGfTmPNMGqLYylDOYxQBxE3bjMwqlvV_d3g4jjO8L1nSBAqLIRQ");
        return logoutRequest;
    }
}
