package com.dxunited.core.auth.util;

import com.dxunited.core.auth.config.OAuth2ClientProperties;
import com.dxunited.core.auth.constant.AuthConstants;
import com.dxunited.core.auth.model.request.ClientTokenRequest;
import com.dxunited.core.auth.model.request.RefreshTokenRequest;
import com.dxunited.core.auth.model.request.UserTokenRequest;
import com.dxunited.core.auth.model.request.ValidateTokenRequest;
import com.dxunited.core.auth.model.spi.UserRepresentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED;

public class RequestGeneratorTest {

    @InjectMocks
    RequestGenerator requestGenerator;

    @Mock
    private ValidateTokenRequest validateTokenRequest;

    @Mock
    private RefreshTokenRequest refreshTokenRequest;

    @Mock
    private ClientTokenRequest clientTokenRequest;

    @Mock
    private UserTokenRequest userTokenRequest;

    @Mock
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Mock
    private TenantRealmResolver tenantRealmResolver;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(tenantRealmResolver.resolveRealm(anyString())).thenReturn("test");
    }

    @Test
    public void testForGenerateUri() {
        String endPoint = "/admin/realms/%s/user";
        String baseUri = "http://172.16.3.98:5002/";
        String realm = "test";
        when(oAuth2ClientProperties.getAuthServerBaseUrl()).thenReturn("http://172.16.3.98:5002/");
        assertEquals(getUriFromString(baseUri + "/admin/realms/test/user"), requestGenerator.generateURI(endPoint, realm));
    }

    @Test
    public void testForGenerateValidateTokenRequest() {
        String[] requiredFormData = {"clientid", "secret", "token"};
        MultiValueMap<String, String> formParameters = getFormData(requiredFormData);
        when(validateTokenRequest.getToken()).thenReturn("token");
        when(validateTokenRequest.getClientId()).thenReturn("clientid");
        when(validateTokenRequest.getClientSecret()).thenReturn("secret");
        HttpEntity<MultiValueMap<String, String>> entity = requestGenerator.generateValidateTokenRequest(new ValidateTokenRequest("clientid", "secret", "token"));
        assertEquals(formParameters.size(), entity.getBody().size());
        assertEquals(getHeader(), entity.getHeaders());
    }

    @Test
    public void testForGenerateClientTokenRequest() {
        String[] requiredFormData = {"clientid", "secret", "granttype"};
        MultiValueMap<String, String> formParameters = getFormData(requiredFormData);
        when(clientTokenRequest.getClientId()).thenReturn("clientid");
        when(clientTokenRequest.getClientSecret()).thenReturn("secret");
        ClientTokenRequest request = new ClientTokenRequest();
        request.setClientId("clientid");
        request.setClientSecret("secret");
        HttpEntity<MultiValueMap<String, String>> entity = requestGenerator.generateClientTokenRequest(request);
        assertEquals(formParameters.size(), entity.getBody().size());
        assertEquals(getHeader(), entity.getHeaders());
    }

    @Test
    public void testForGenerateUserTokenRequest() {
        String[] requiredFormData = {"clientid", "secret", "granttype", "username", "password"};
        MultiValueMap<String, String> formParameters = getFormData(requiredFormData);
        when(userTokenRequest.getClientId()).thenReturn("clientid");
        when(userTokenRequest.getClientSecret()).thenReturn("secret");
        when(userTokenRequest.getPassword()).thenReturn("password");
        when(userTokenRequest.getUsername()).thenReturn("username");
        UserTokenRequest request = new UserTokenRequest();
        request.setClientId("clientid");
        request.setClientSecret("secret");
        request.setPassword("password");
        request.setUsername("username");
        HttpEntity<MultiValueMap<String, String>> entity = requestGenerator.generateUserTokenRequest(request);
        assertEquals(formParameters.size(), entity.getBody().size());
        assertEquals(getHeader(), entity.getHeaders());
    }

    @Test
    public void testForGenerateRefreshTokenRequest() {
        String[] requiredFormData = {"clientid", "granttype", "refreshtoken"};
        MultiValueMap<String, String> formParameters = getFormData(requiredFormData);
        when(refreshTokenRequest.getClientId()).thenReturn("clientid");
        when(refreshTokenRequest.getRefreshToken()).thenReturn("refreshtoken");
        RefreshTokenRequest request = new RefreshTokenRequest();
        request.setClientId("clientid");
        request.setRefreshToken("refreshtoken");
        HttpEntity<MultiValueMap<String, String>> entity = requestGenerator.generateRefreshTokenRequest(request);
        assertEquals(formParameters.size(), entity.getBody().size());
        assertEquals(getHeader(), entity.getHeaders());
    }

    @Test
    public void testForGenerateUserRequest() {
        UserRepresentation user = new UserRepresentation();
        user.setFirstName("xyz");
        user.setLastName("pqr");
        String clientId = "clientid";
        List<String> expectedHeaderValue = new ArrayList<>();
        expectedHeaderValue.add("clientid");

        HttpEntity<UserRepresentation> entity = requestGenerator.generateUserRequest(user, clientId);
        assertEquals(expectedHeaderValue, entity.getHeaders().get(AuthConstants.TENANT_ID));
        assertEquals("xyz", entity.getBody().getFirstName());
        assertEquals("application/json", entity.getHeaders().getContentType().toString());
    }


    private MultiValueMap<String, String> getFormData(String[] requiredFormData) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        for (String var : requiredFormData) {
            formData.add(var, var);
        }
        return formData;
    }

    private HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private URI getUriFromString(String string) {
        return URI.create(string);
    }
}
