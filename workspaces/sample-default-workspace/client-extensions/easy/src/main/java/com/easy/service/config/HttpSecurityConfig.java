/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.easy.service.config;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import java.net.URL;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Brian Wing Shun Chan
 */
@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {

	@Bean
	public List<String> allowedOrigins() {
		return Stream.of(
			_lxcDXPDomains.split("\\s*[,\n]\\s*")
		).map(
			String::trim
		).map(
			"https://"::concat
		).collect(
			Collectors.toList()
		);
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();

		configuration.setAllowedOrigins(allowedOrigins());
		configuration.setAllowedMethods(
			Arrays.asList(
				"DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"));
		configuration.setAllowedHeaders(
			Arrays.asList("Authorization", "Content-Type"));

		UrlBasedCorsConfigurationSource source =
			new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", configuration);

		return source;
	}

	@Bean
	public JwtDecoder jwtDecoder(
			@Value("${oauth.application.externalReferenceCode}") String externalReferenceCode,
			@Value("${oauth.application.uri}") String applicationUri,
			@Value("${oauth.jwks.uri}") String jwkSetUri)
		throws Exception {

		DefaultJWTProcessor<SecurityContext> jwtProcessor =
			new DefaultJWTProcessor<>();

		jwtProcessor.setJWSKeySelector(
			JWSAlgorithmFamilyJWSKeySelector.fromJWKSetURL(new URL(jwkSetUri)));
		jwtProcessor.setJWSTypeVerifier(
			new DefaultJOSEObjectTypeVerifier<>(new JOSEObjectType("at+jwt")));

		NimbusJwtDecoder nimbusJwtDecoder = new NimbusJwtDecoder(jwtProcessor);

		String clientId = _getClientId(applicationUri, externalReferenceCode);

		System.out.println("Using clientId: " + clientId + " to decode Jwt");

		nimbusJwtDecoder.setJwtValidator(
			new DelegatingOAuth2TokenValidator<>(
				new ClientIdValidator(clientId)));

		return nimbusJwtDecoder;
	}

	private static class ApplicationInfo {
		public String client_id;
	}

	private String _getClientId(String applicationUri, String externalReferenceCode) throws Exception {
		while (true) {
			try {
				return WebClient.create(
					applicationUri
				).get(
				).uri(
					uriBuilder -> uriBuilder.queryParam(
						"externalReferenceCode", externalReferenceCode
					).build()
				).retrieve(
				).bodyToMono(
					ApplicationInfo.class
				).block(
				).client_id;
			} catch (Throwable throwable) {
				System.err.println("Unable to get client_id: " + throwable);
				Thread.sleep(1000);
			}	
		}
	}

	@Bean
	public SecurityFilterChain securityFilterChain(
			HttpSecurity http, Collection<Consumer<HttpSecurity>> adapters)
		throws Exception {

		return http.cors(
		).and(
		).csrf(
		).disable(
		).sessionManagement(
		).sessionCreationPolicy(
			SessionCreationPolicy.STATELESS
		).and(
		).authorizeHttpRequests(
			customizer -> customizer.antMatchers(
				"/"
			).permitAll(
			).anyRequest(
			).authenticated()
		).oauth2ResourceServer(
			OAuth2ResourceServerConfigurer::jwt
		).build();
	}

	@Value("${dxp.domains}")
	private String _lxcDXPDomains;

}