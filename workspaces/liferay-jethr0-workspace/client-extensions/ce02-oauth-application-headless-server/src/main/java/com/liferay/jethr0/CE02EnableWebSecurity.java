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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

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

	@Value("${liferay.portal.domains}")
	private String _liferayPortalDomains;

}