package com.liferay.custom.rest.service.config;

import java.net.URI;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.introspection.SpringOpaqueTokenIntrospector;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomRestSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${OAUTH2_USERAGENTAPP_CLIENT_ID}")
	String clientId;

	@Value("${OAUTH2_INTROSPECTION_URI}")
	String introspectionUri;

	@Bean
	public static WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:8080");
			}
		};
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().authorizeRequests(
			authz -> authz.anyRequest().authenticated()
		).oauth2ResourceServer(
			oauth2 -> oauth2.opaqueToken().introspector(
				new CustomSpringOpaqueTokenIntrospector(
					introspectionUri, this.clientId)
			)
		);
	}

	private static class CustomSpringOpaqueTokenIntrospector extends SpringOpaqueTokenIntrospector {

		public CustomSpringOpaqueTokenIntrospector(String introspectionUri, String clientId) {
			super(introspectionUri, new RestTemplate());
			setRequestEntityConverter(customRequestEntityConverter(URI.create(introspectionUri), clientId));
		}

		private static Converter<String, RequestEntity<?>> customRequestEntityConverter(URI introspectionUri,
				String clientId) {
			return (token) -> {
				HttpHeaders headers = requestHeaders();
				MultiValueMap<String, String> body = requestBody(clientId, token);
				return new RequestEntity<>(body, headers, HttpMethod.POST, introspectionUri);
			};
		}

		private static HttpHeaders requestHeaders() {
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			return headers;
		}

		private static MultiValueMap<String, String> requestBody(String clientId, String token) {
			MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
			body.add("client_id", clientId);
			body.add("token", token);
			body.add("token_type_hint", "access_token");
			return body;
		}
	}

}