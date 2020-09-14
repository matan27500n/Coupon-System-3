package com.johnbryce.app.config;

import java.util.Collections;
import java.util.HashMap;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.johnbryce.app.security.CustomSession;

@Configuration
public class MyConfig {

	@Bean
	public HashMap<String, CustomSession> tokensInMemory() {
		return new HashMap<>();
	}

	/*
	 * @Bean public RestTemplate restTemplate() { RestTemplate restTemplate = new
	 * RestTemplate();
	 * restTemplate.setMessageConverters((Collections.singletonList(new
	 * MappingJackson2CborHttpMessageConverter()))); return restTemplate; }
	 */

	@Configuration
	public class MyConfiguration {
		@Bean
		public CorsFilter corsFilter() {
			UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
			CorsConfiguration config = new CorsConfiguration();
			config.setAllowCredentials(true);
			config.addAllowedOrigin("*");
			config.addAllowedHeader("*");
			config.addAllowedMethod("OPTIONS");
			config.addAllowedMethod("GET");
			config.addAllowedMethod("POST");
			config.addAllowedMethod("PUT");
			config.addAllowedMethod("DELETE");
			source.registerCorsConfiguration("/**", config);
			return new CorsFilter();
		}

		/*
		 * @Bean public WebMvcConfigurer corsConfigurer() { return new
		 * WebMvcConfigurer() {
		 * 
		 * @Override public void addCorsMappings(CorsRegistry registry) {
		 * registry.addMapping("/**").allowedMethods("GET", "POST", "PUT",
		 * "DELETE").allowedHeaders("*") .allowedOrigins("http://localhost:4200"); } };
		 * }
		 */
	}
}
