package com.rakers.efdsv.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Autowired
	private RestTemplateBuilder builder;
	
	@Bean("restTemplate")
	public RestTemplate restTemplate() {
		return builder.build();
	}
	
}

