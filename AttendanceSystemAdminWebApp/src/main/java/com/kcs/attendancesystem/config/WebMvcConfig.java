package com.kcs.attendancesystem.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer  {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*")
				.allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
				.allowedHeaders("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Origin", "Accept",
						"X-Requested-With", "Content-Type", "Access-Control-Request-Method",
						"Access-Control-Request-Headers", "Content-Type", "X-Requested-With", "accept", "Origin",
						"Access-Control-Request-Method", "Authorization", "tenantid", "Access-Control-Request-Headers");		
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
