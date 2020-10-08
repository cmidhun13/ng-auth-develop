package com.szells.gce.auth.config;

import com.szells.gce.auth.interceptors.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.MappedInterceptor;

@Configuration
public class RequestInterceptorConfig {
    @Bean
    public MappedInterceptor mappedInterceptor(RequestInterceptor interceptor) {
        return new MappedInterceptor(new String[]{"/auth/**"}, interceptor);
    }
}