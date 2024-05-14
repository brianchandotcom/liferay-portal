/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
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

import java.net.URL;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Gregory Amerson
 */
@RequestMapping("/external/oauth/client")
@RestController
public class ExternalOauthClientRestController extends BaseRestController {

	@GetMapping
	public ResponseEntity<String> get(@AuthenticationPrincipal Jwt jwt)
		throws Exception {

		log(jwt, _log);

		String authorization =
			_liferayOAuth2AccessTokenManager.getAuthorization(
				"external-liferay");

		SiteResource siteResource = SiteResource.builder(
		).header(
			"Authorization", authorization
		).endpoint(
			_externalLiferayHomePageURI
		).build();

		Site site = siteResource.getSiteByFriendlyUrlPath("guest");

		MessageBoardThreadResource messageBoardThreadResource =
			MessageBoardThreadResource.builder(
			).header(
				"Authorization", authorization
			).endpoint(
				_externalLiferayHomePageURI
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

		return new ResponseEntity<>(
			String.valueOf(messageBoardThreads.size()), HttpStatus.OK);
	}

	private static final Log _log = LogFactory.getLog(
		ExternalOauthClientRestController.class);

	@Value("${external.liferay.oauth2.headless.server.home.page.uri}")
	private URL _externalLiferayHomePageURI;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}