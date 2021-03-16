package com.dxunited.core.auth.convertor;

import com.dxunited.core.auth.converter.PasswordToCredential;
import com.dxunited.core.auth.model.spi.Credential;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PasswordToCredentialTest {

    @InjectMocks
    PasswordToCredential passwordToCredential;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testForConvert() {
        String password = "password";
        Credential credential = passwordToCredential.convert(password);
        assertEquals("password", credential.getValue());
    }
}
