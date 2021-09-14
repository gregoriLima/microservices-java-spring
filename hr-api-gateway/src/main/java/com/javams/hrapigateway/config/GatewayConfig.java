package com.javams.hrapigateway.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.filter.CorsFilter;

import com.javams.hrapigateway.filters.JwtAuthenticationFilter;

@Configuration
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter filter;
	
    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
        		//.filters(f -> f.stripPrefix(1)
                .route("worker", r -> r.path("/hr-worker/**").and().method("GET").filters(f -> {f.stripPrefix(1);return f.filter(filter);}).uri("lb://hr-worker"))
                .route("payroll", r -> r.path("/hr-payroll/**").and().method("GET").filters(f -> {f.stripPrefix(1);return f.filter(filter);}).uri("lb://hr-payroll"))
                .route("auth",  r -> r.path("/hr-oauth/**").and().method("POST").filters(f -> f.stripPrefix(1)).uri("lb://hr-oauth"))
                .route("others", r -> r.path("/**").filters(f -> f.stripPrefix(1)).uri("http://google.com")).build();
        
    }
    
  
    
 
}