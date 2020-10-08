package com.szells.gce.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@ComponentScan(basePackages = {"com.szells"})
@EnableAsync
public class NgAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(NgAuthApplication.class);
    }
}
