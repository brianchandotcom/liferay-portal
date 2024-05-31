/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

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
 * @author Gregory Amerson
 */
@Import(ClientExtensionUtilSpringBootComponentScan.class)
@SpringBootApplication
public class SampleSpringBootApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(
			SampleSpringBootApplication.class
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