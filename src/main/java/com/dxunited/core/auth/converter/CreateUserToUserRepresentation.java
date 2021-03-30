package com.dxunited.core.auth.converter;

import com.dxunited.core.auth.model.request.CreateUserRequest;
import com.dxunited.core.auth.model.spi.Credential;
import com.dxunited.core.auth.model.spi.UserRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;

import java.util.List;

@RequiredArgsConstructor
public class CreateUserToUserRepresentation implements Converter<CreateUserRequest, UserRepresentation> {

    private final ConversionService conversionService;

    @Override
    public UserRepresentation convert(@NonNull CreateUserRequest source) {
        return UserRepresentation.builder()
                .username(source.getUsername())
                .credentials(List.of(conversionService.convert(source.getPassword(), Credential.class)))
                .build();
    }
}
