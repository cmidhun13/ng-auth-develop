package com.szells.gce.auth.converter;

import com.szells.gce.auth.model.spi.Credential;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

public class PasswordToCredential implements Converter<String, Credential> {
    @Override
    public Credential convert(@NonNull String source) {
        return Credential.builder()
                .type("password")
                .value(source)
                .build();
    }
}
