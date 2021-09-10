package com.javams.hrpayroll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

// singleton para injetar em outros serviços
@Configuration
public class AppConfig {

	@Bean
	public RestTemplate getRestTemplate() { // registrando um singleton de RestTemplate
		return new RestTemplate();
	}
	
}
