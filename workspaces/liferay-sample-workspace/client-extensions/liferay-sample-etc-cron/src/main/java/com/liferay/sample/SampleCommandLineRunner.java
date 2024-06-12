/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sample;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
import com.liferay.headless.admin.user.client.dto.v1_0.Site;
import com.liferay.headless.admin.user.client.resource.v1_0.SiteResource;
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.resource.v1_0.MessageBoardThreadResource;
import com.liferay.petra.string.StringBundler;

import java.net.URL;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Gregory Amerson
 */
@Component
public class SampleCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {

		// Call the main Liferay that this client extension belongs to

		try {
			_countMessageBoardThreads(
				"liferay-sample-etc-cron-oauth-application-headless-server",
				new URL(_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain));
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		// Call another Liferay

		try {
			_countMessageBoardThreads(
				"external-liferay", _externalLiferayHomePageURL);
		}
		catch (Exception exception) {
			_log.error(exception);
		}

		// Call another client extension (liferay-sample-etc-spring-boot)

		try {
			String dadJoke = WebClient.create(
			).get(
			).uri(
				_liferaySampleEtcSpringBootHomePageURL + "/dad/joke"
			).header(
				"Authorization",
				_liferayOAuth2AccessTokenManager.getAuthorization(
					"liferay-sample-etc-cron-oauth-application-headless-server")
			).accept(
				MediaType.TEXT_PLAIN
			).retrieve(
			).bodyToMono(
				String.class
			).block();

			if ((dadJoke != null) && _log.isInfoEnabled()) {
				_log.info("Dad joke: " + dadJoke);
			}
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private void _countMessageBoardThreads(
			String externalReferenceCode, URL url)
		throws Exception {

		String authorization =
			_liferayOAuth2AccessTokenManager.getAuthorization(
				externalReferenceCode);

		SiteResource siteResource = SiteResource.builder(
		).header(
			"Authorization", authorization
		).endpoint(
			url
		).build();

		Site site = siteResource.getSiteByFriendlyUrlPath("guest");

		MessageBoardThreadResource messageBoardThreadResource =
			MessageBoardThreadResource.builder(
			).header(
				"Authorization", authorization
			).endpoint(
				url
			).build();

		Page<MessageBoardThread> messageBoardThreadPage =
			messageBoardThreadResource.getSiteMessageBoardThreadsPage(
				site.getId(), null, null, null, null, Pagination.of(1, 2),
				null);

		Collection<MessageBoardThread> messageBoardThreads =
			messageBoardThreadPage.getItems();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"There are ", messageBoardThreads.size(),
					" message board threads in the Guest site at ", url));
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

	@Value("${external.liferay.oauth2.headless.server.home.page.url}")
	private URL _externalLiferayHomePageURL;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

	@Value("${liferay.sample.etc.spring.boot.home.page.url}")
	private URL _liferaySampleEtcSpringBootHomePageURL;

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

}