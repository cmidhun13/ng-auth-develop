package com.dxunited.core.auth.client;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class AuthClient {

    private final RestTemplate restTemplate;

    public <T, R> T invoke(URI uri, HttpMethod method, HttpEntity<R> request, Class<T> responseModel) {
        return restTemplate.exchange(uri, method, request, responseModel).getBody();
    }

    public ResponseEntity invokeRest(URI uri, HttpMethod method, HttpEntity request, Class responseModel) {
        return restTemplate.exchange(uri, method, request, responseModel);
    }
}
