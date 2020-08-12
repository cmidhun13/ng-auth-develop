package com.szells.gce.auth.config;

import com.szells.gce.auth.converter.CreateUserToUserRepresentation;
import com.szells.gce.auth.converter.PasswordToCredential;
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
