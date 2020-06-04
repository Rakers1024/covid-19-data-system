package com.rakers.covid19datasystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@SpringBootApplication
@EnableOAuth2Client
public class Covid19DataSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(Covid19DataSystemApplication.class, args);
    }

}
