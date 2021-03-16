package com.dxunited.core.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@ComponentScan(basePackages = {"com.szells"})
@EnableAsync
public class NgAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(NgAuthApplication.class);
    }
}
