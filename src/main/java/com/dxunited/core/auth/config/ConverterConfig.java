package com.dxunited.core.auth.config;

import com.dxunited.core.auth.converter.CreateUserToUserRepresentation;
import com.dxunited.core.auth.converter.PasswordToCredential;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;

@Configuration
public class ConverterConfig {
    @Bean
    public ConversionService conversionService() {
        DefaultConversionService conversionService = new DefaultConversionService();
        conversionService.addConverter(new PasswordToCredential());
        conversionService.addConverter(new CreateUserToUserRepresentation(conversionService));
        return conversionService;
    }
}
