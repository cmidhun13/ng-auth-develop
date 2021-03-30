package com.dxunited.core.auth.converter;

import com.dxunited.core.auth.model.spi.Credential;
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
