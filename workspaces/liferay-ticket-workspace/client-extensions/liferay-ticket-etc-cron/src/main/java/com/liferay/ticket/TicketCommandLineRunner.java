/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ticket;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
@Component
public class TicketCommandLineRunner implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		JSONObject responseJSONObject = new JSONObject(
			WebClient.create(
				_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
			).get(
			).uri(
				"/o/c/j3y7tickets"
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION,
				"Bearer " + _oAuth2AccessToken.getTokenValue()
			).retrieve(
			).bodyToMono(
				String.class
			).block());

		if (_log.isInfoEnabled()) {
			_log.info(responseJSONObject.toString(4));
		}

		JSONArray itemsJSONArray = responseJSONObject.getJSONArray("items");

		for (int i = 0; i < itemsJSONArray.length(); i++) {
			JSONObject itemJSONObject = itemsJSONArray.getJSONObject(i);

			JSONObject resolutionJSONObject = itemJSONObject.optJSONObject(
				"resolution");

			if (resolutionJSONObject == null) {
				continue;
			}

			String resolution = resolutionJSONObject.optString("key");

			if (!Objects.equals(resolution, "duplicate") &&
				!Objects.equals(resolution, "done")) {

				continue;
			}

			String id = itemJSONObject.optString("id");

			if (_log.isInfoEnabled()) {
				_log.info("Deleting ticket " + id);
			}

			WebClient.create(
				_lxcDXPServerProtocol + "://" + _lxcDXPMainDomain
			).delete(
			).uri(
				"/o/c/j3y7tickets/" + id
			).accept(
				MediaType.APPLICATION_JSON
			).header(
				HttpHeaders.AUTHORIZATION,
				"Bearer " + _oAuth2AccessToken.getTokenValue()
			).retrieve(
			).toEntity(
				Void.class
			).block();
		}
	}

	private static final Log _log = LogFactory.getLog(
		TicketCommandLineRunner.class);

	@Value("${com.liferay.lxc.dxp.mainDomain}")
	private String _lxcDXPMainDomain;

	@Value("${com.liferay.lxc.dxp.server.protocol}")
	private String _lxcDXPServerProtocol;

	@Autowired
	private OAuth2AccessToken _oAuth2AccessToken;

}