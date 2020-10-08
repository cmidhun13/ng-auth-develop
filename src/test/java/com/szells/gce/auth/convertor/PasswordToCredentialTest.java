package com.szells.gce.auth.convertor;

import com.szells.gce.auth.converter.PasswordToCredential;
import com.szells.gce.auth.model.spi.Credential;
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
