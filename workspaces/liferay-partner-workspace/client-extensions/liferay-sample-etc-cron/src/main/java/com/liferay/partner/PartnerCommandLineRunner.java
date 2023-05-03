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

import com.liferay.headless.admin.user.client.dto.v1_0.Site;
import com.liferay.headless.admin.user.client.resource.v1_0.SiteResource;
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.MessageBoardThreadResource;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * @author Jair Medeiros
 */
@Component
public class PartnerCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		OAuth2AuthorizedClient oAuth2AuthorizedClient =
			_authorizedClientServiceOAuth2AuthorizedClientManager.authorize(
				OAuth2AuthorizeRequest.withClientRegistrationId(
					"liferay-partner-etc-cron"
				).principal(
					"PartnerCommandLineRunner"
				).build());

		if (oAuth2AuthorizedClient == null) {
			_log.error("Unable to get OAuth 2 authorized client");

			return;
		}

		OAuth2AccessToken oAuth2AccessToken =
			oAuth2AuthorizedClient.getAccessToken();

		if (_log.isInfoEnabled()) {
			_log.info("Issued: " + oAuth2AccessToken.getIssuedAt());
			_log.info("Expires At: " + oAuth2AccessToken.getExpiresAt());
			_log.info("Scopes: " + oAuth2AccessToken.getScopes());
			_log.info("Token: " + oAuth2AccessToken.getTokenValue());
		}

		SiteResource siteResource = SiteResource.builder(
		).bearerToken(
			oAuth2AccessToken.getTokenValue()
		).endpoint(
			_lxcDXPMainDomain, _lxcDXPServerProtocol
		).build();

		Site site = siteResource.getSiteByFriendlyUrlPath("guest");

		MessageBoardThreadResource messageBoardThreadResource =
			MessageBoardThreadResource.builder(
			).bearerToken(
				oAuth2AccessToken.getTokenValue()
			).endpoint(
				_lxcDXPMainDomain, _lxcDXPServerProtocol
			).build();

		Page<MessageBoardThread> messageBoardThreadPage =
			messageBoardThreadResource.getSiteMessageBoardThreadsPage(
				site.getId(), null, null, null, null, Pagination.of(1, 2),
				null);

		Collection<MessageBoardThread> messageBoardThreads =
			messageBoardThreadPage.getItems();

		if (_log.isInfoEnabled()) {
			_log.info(
				"There are " + messageBoardThreads.size() +
					" message board threads in the Guest site");
		}

		for (MessageBoardThread messageBoardThread : messageBoardThreads) {

			// TODO Post a random message board message in each message board
			// thread

			if (_log.isInfoEnabled()) {
				_log.info(messageBoardThread);
			}
		}
	}

	private static final Log _log = LogFactory.getLog(
		PartnerCommandLineRunner.class);

	@Autowired
	private AuthorizedClientServiceOAuth2AuthorizedClientManager
		_authorizedClientServiceOAuth2AuthorizedClientManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}