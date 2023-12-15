/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer;

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
 * @author Amos Fong
 */
@Import(ClientExtensionUtilSpringBootComponentScan.class)
@SpringBootApplication
public class CustomerSpringBootApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(
			CustomerSpringBootApplication.class
		).web(
			WebApplicationType.NONE
		).run(
			args
		);
	}

	@Bean(name = "etcCronOAuth2AccessToken")
	public OAuth2AccessToken getEtcCronOAuth2AccessToken(
		AuthorizedClientServiceOAuth2AuthorizedClientManager
			authorizedClientServiceOAuth2AuthorizedClientManager) {

		return LiferayOAuth2Util.getOAuth2AccessToken(
			authorizedClientServiceOAuth2AuthorizedClientManager,
			_liferayEtcCronOAuthApplicationExternalReferenceCode);
	}

	@Bean(name = "etcSpringBootOAuth2AccessToken")
	public OAuth2AccessToken getEtcSpringBootOAuth2AccessToken(
		AuthorizedClientServiceOAuth2AuthorizedClientManager
			authorizedClientServiceOAuth2AuthorizedClientManager) {

		return LiferayOAuth2Util.getOAuth2AccessToken(
			authorizedClientServiceOAuth2AuthorizedClientManager,
			_liferayEtcSpringBootOAuthApplicationExternalReferenceCode);
	}

	@Value(
		"${liferay.customer.etc.cron.headless.server.oauth.application.external.reference.code}"
	)
	private String _liferayEtcCronOAuthApplicationExternalReferenceCode;

	@Value(
		"${liferay.customer.etc.spring.boot.headless.server.oauth.application.external.reference.code}"
	)
	private String _liferayEtcSpringBootOAuthApplicationExternalReferenceCode;

}