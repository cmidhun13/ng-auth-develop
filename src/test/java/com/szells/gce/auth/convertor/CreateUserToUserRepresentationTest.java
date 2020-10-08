package com.szells.gce.auth.convertor;

import com.szells.gce.auth.converter.CreateUserToUserRepresentation;
import com.szells.gce.auth.model.request.CreateUserRequest;
import com.szells.gce.auth.model.spi.Credential;
import com.szells.gce.auth.model.spi.UserRepresentation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.convert.ConversionService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class CreateUserToUserRepresentationTest {

    @InjectMocks
    CreateUserToUserRepresentation createUserToUserRepresentation;

    @Mock
    CreateUserRequest createUserRequest;

    @Mock
    private ConversionService conversionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    private Credential createCredentials() {
        Credential credential = new Credential();
        List<Credential> credentialList = new ArrayList<>();
        credential.setTemporary(false);
        credential.setType("username");
        credential.setValue("username");
        credential.setType("password");
        credential.setValue("password");
        credentialList.add(credential);
        return credential;

    }


    @Test
    public void testForConvert() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setMemberId("memberid");
        createUserRequest.setPassword("password");
        createUserRequest.setUsername("username");
        when(conversionService.convert(Mockito.any(), Mockito.any())).thenReturn(createCredentials());
        UserRepresentation userRepresentation = createUserToUserRepresentation.convert(createUserRequest);
        assertEquals("username", userRepresentation.getUsername());
        assertEquals("password", userRepresentation.getCredentials().get(0).getValue());
    }
}
