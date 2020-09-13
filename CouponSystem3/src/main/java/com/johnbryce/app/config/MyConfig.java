package com.johnbryce.app.config;

import java.util.Collections;
import java.util.HashMap;

import org.apache.catalina.filters.CorsFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.cbor.MappingJackson2CborHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.johnbryce.app.security.CustomSession;

@Configuration
public class MyConfig {

	@Bean
	public HashMap<String, CustomSession> tokensInMemory() {
		return new HashMap<>();
	}

	/*@Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setMessageConverters((Collections.singletonList(new MappingJackson2CborHttpMessageConverter())));
		return restTemplate;
	}*/

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
	}
}
