package com.rakers.ezuulauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class EZuulAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(EZuulAuthApplication.class, args);
    }

}
