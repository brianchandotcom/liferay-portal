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

package com.liferay.partner;

import com.liferay.client.extension.util.spring.boot.ClientExtensionUtilSpringBootComponentScan;
import com.liferay.client.extension.util.spring.boot.LiferayOAuth2Util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

/**
 * @author Jair Medeiros
 */
@Import(ClientExtensionUtilSpringBootComponentScan.class)
@SpringBootApplication
public class PartnerSpringBootApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(
			PartnerSpringBootApplication.class
		).web(
			WebApplicationType.NONE
		).run(
			args
		);
	}

	@Bean
	public OAuth2AccessToken getOAuth2AccessToken(
		AuthorizedClientServiceOAuth2AuthorizedClientManager
			authorizedClientServiceOAuth2AuthorizedClientManager) {

		return LiferayOAuth2Util.getOAuth2AccessToken(
			authorizedClientServiceOAuth2AuthorizedClientManager,
			_liferayOAuthApplicationExternalReferenceCodes);
	}

	@Value("${liferay.oauth.application.external.reference.codes}")
	private String _liferayOAuthApplicationExternalReferenceCodes;

}