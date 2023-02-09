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

package com.liferay.jethr0;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.proc.DefaultJOSEObjectTypeVerifier;
import com.nimbusds.jose.proc.JWSAlgorithmFamilyJWSKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Michael Hashimoto
 */
@Configuration
@EnableWebSecurity
public class CE02EnableWebSecurity {

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource =
			new UrlBasedCorsConfigurationSource();

		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.setAllowedHeaders(
			Arrays.asList("Authorization", "Content-Type"));
		corsConfiguration.setAllowedMethods(
			Arrays.asList(
				"DELETE", "GET", "HEAD", "OPTIONS", "PATCH", "POST", "PUT"));
		corsConfiguration.setAllowedOrigins(_getLiferayAllowedOrigins());

		urlBasedCorsConfigurationSource.registerCorsConfiguration(
			"/**", corsConfiguration);

		return urlBasedCorsConfigurationSource;
	}

	@Bean
	public JwtDecoder jwtDecoder() throws Exception {
		DefaultJWTProcessor<SecurityContext> defaultJWTProcessor =
			new DefaultJWTProcessor<>();

		defaultJWTProcessor.setJWSKeySelector(
			JWSAlgorithmFamilyJWSKeySelector.fromJWKSetURL(
				new URL(_liferayPortalURL + "/o/oauth2/jwks")));
		defaultJWTProcessor.setJWSTypeVerifier(
			new DefaultJOSEObjectTypeVerifier<>(new JOSEObjectType("at+jwt")));

		NimbusJwtDecoder nimbusJwtDecoder = new NimbusJwtDecoder(
			defaultJWTProcessor);

		String liferayOAuthClientID = _getLiferayOAuthClientID();

		if (_log.isInfoEnabled()) {
			_log.info("Using Liferay OAuth Client ID " + liferayOAuthClientID);
		}

		nimbusJwtDecoder.setJwtValidator(
			new LiferayOAuth2TokenValidator(liferayOAuthClientID));

		return nimbusJwtDecoder;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
		throws Exception {

		return httpSecurity.cors(
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

	private List<String> _getLiferayAllowedOrigins() {
		List<String> liferayAllowedOrigins = new ArrayList<>();

		for (String dxpDomain : _liferayPortalDomains.split("\\s*[,\n]\\s*")) {
			liferayAllowedOrigins.add("http://" + dxpDomain);
			liferayAllowedOrigins.add("https://" + dxpDomain);
		}

		return liferayAllowedOrigins;
	}

	private String _getLiferayOAuthClientID() throws Exception {
		if (_liferayOAuthClientID != null) {
			return _liferayOAuthClientID;
		}

		while (true) {
			try {
				StringBuilder sb = new StringBuilder();

				sb.append(_liferayPortalURL);
				sb.append("/o/oauth2/application?externalReferenceCode=");
				sb.append(_liferayOAuthApplicationExternalReferenceCode);

				String response = WebClient.builder(
				).build(
				).get(
				).uri(
					sb.toString()
				).retrieve(
				).bodyToMono(
					String.class
				).block();

				JSONObject jsonObject = new JSONObject(response);

				_liferayOAuthClientID = jsonObject.getString("client_id");

				return _liferayOAuthClientID;
			}
			catch (Throwable throwable) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get client ID: " + throwable.getMessage());
				}

				Thread.sleep(1000);
			}
		}
	}

	private static final Log _log = LogFactory.getLog(
		CE02EnableWebSecurity.class);

	@Value("${liferay.oauth.application.external.reference.code}")
	private String _liferayOAuthApplicationExternalReferenceCode;

	private String _liferayOAuthClientID;

	@Value("${liferay.portal.domains}")
	private String _liferayPortalDomains;

	@Value("${liferay.portal.url}")
	private String _liferayPortalURL;

	private class LiferayOAuth2TokenValidator
		implements OAuth2TokenValidator<Jwt> {

		public LiferayOAuth2TokenValidator(String liferayOAuthClientID) {
			_liferayOAuthClientID = liferayOAuthClientID;
		}

		@Override
		public OAuth2TokenValidatorResult validate(Jwt jwt) {
			if (Objects.equals(
					jwt.getClaimAsString("client_id"), _liferayOAuthClientID)) {

				return OAuth2TokenValidatorResult.success();
			}

			return OAuth2TokenValidatorResult.failure(_oAuth2Error);
		}

		private final String _liferayOAuthClientID;
		private final OAuth2Error _oAuth2Error = new OAuth2Error(
			"invalid_token", "The client_id does not match", null);

	}

}