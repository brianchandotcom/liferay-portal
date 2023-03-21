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

package com.sample.liferay;

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
 * @author Gregory Amerson
 */
@Component
public class SampleJobRunner implements CommandLineRunner {

	public void run(String... args) throws Exception {
		OAuth2AuthorizedClient oAuth2AuthorizedClient =
			_authorizedClientServiceOAuth2AuthorizedClientManager.authorize(
				OAuth2AuthorizeRequest.withClientRegistrationId(
					"dxp"
				).principal(
					"Sample"
				).build());

		// Get the token from the authorized client object

		OAuth2AccessToken oAuth2AccessToken =
			oAuth2AuthorizedClient.getAccessToken();

		System.out.println(
			"Issued: " + oAuth2AccessToken.getIssuedAt() + ", Expires:" +
				oAuth2AccessToken.getExpiresAt());
		System.out.println("Scopes: " + oAuth2AccessToken.getScopes());
		System.out.println("Token: " + oAuth2AccessToken.getTokenValue());

		String host = _mainDomain;

		if (host.contains(":")) {
			host = host.substring(0, host.indexOf(":"));
		}

		int serverPort = 443;

		String[] mainDomainParts = _mainDomain.split(":");

		if (mainDomainParts.length > 1) {
			try {
				serverPort = Integer.parseInt(mainDomainParts[1]);
			}
			catch (NumberFormatException numberFormatException) {
				_log.error(
					"Unable to parse server port from " + _mainDomain,
					numberFormatException);
			}
		}

		SiteResource siteResource = SiteResource.builder(
		).header(
			"Authorization", "Bearer " + oAuth2AccessToken.getTokenValue()
		).endpoint(
			host, serverPort, _serverProtocol
		).build();

		Site site = siteResource.getSiteByFriendlyUrlPath("guest");

		Long siteId = site.getId();

		MessageBoardThreadResource messageBoardThreadResource =
			MessageBoardThreadResource.builder(
			).header(
				"Authorization", "Bearer " + oAuth2AccessToken.getTokenValue()
			).endpoint(
				host, serverPort, _serverProtocol
			).build();

		Page<MessageBoardThread> messageBoardThreadPage =
			messageBoardThreadResource.getSiteMessageBoardThreadsPage(
				siteId, null, null, null, null, Pagination.of(1, 2), null);

		Collection<MessageBoardThread> messageBoardThreads =
			messageBoardThreadPage.getItems();

		_log.info(
			"Searching for unanswered questions among " +
				messageBoardThreads.size() + " threads");

		messageBoardThreads.forEach(
			messageBoardThread -> {
				if (messageBoardThread.getShowAsQuestion()) {
					Long threadId = messageBoardThread.getId();
					String currentHeadline = messageBoardThread.getHeadline();

					if (messageBoardThread.getHasValidAnswer()) {
						_log.info(
							"Found answered question: " + threadId + " - " +
								currentHeadline);

						if (currentHeadline.startsWith("[Unanswered] ")) {
							messageBoardThread.setHeadline(
								currentHeadline.substring(
									"[Unanswered] ".length()));

							try {
								messageBoardThreadResource.
									putMessageBoardThread(
										threadId, messageBoardThread);

								_log.info(
									"Marked thread as answered: " + threadId);
							}
							catch (Exception exception) {
								_log.error(
									"Unable to update message board thread",
									exception);
							}
						}
					}
					else {
						_log.info(
							"Found unanswered question: " + threadId + " - " +
								currentHeadline);

						if (!currentHeadline.startsWith("[Unanswered] ")) {
							messageBoardThread.setHeadline(
								"[Unanswered] " + currentHeadline);

							try {
								messageBoardThreadResource.
									putMessageBoardThread(
										threadId, messageBoardThread);

								_log.info(
									"Marked thread as unanswered: " + threadId);
							}
							catch (Exception exception) {
								_log.error(
									"Unable to update message board thread",
									exception);
							}
						}
					}
				}
			});
	}

	private static final Log _log = LogFactory.getLog(SampleJobRunner.class);

	@Autowired
	private AuthorizedClientServiceOAuth2AuthorizedClientManager
		_authorizedClientServiceOAuth2AuthorizedClientManager;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _mainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _serverProtocol;

}