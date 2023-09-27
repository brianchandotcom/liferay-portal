/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

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
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;

/**
 * @author Gregory Amerson
 */
@Component
public class SampleCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		SiteResource siteResource = SiteResource.builder(
		).bearerToken(
			_oAuth2AccessToken.getTokenValue()
		).endpoint(
			_lxcDXPMainDomain, _lxcDXPServerProtocol
		).build();

		Site site = siteResource.getSiteByFriendlyUrlPath("guest");

		MessageBoardThreadResource messageBoardThreadResource =
			MessageBoardThreadResource.builder(
			).bearerToken(
				_oAuth2AccessToken.getTokenValue()
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
		SampleCommandLineRunner.class);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

}