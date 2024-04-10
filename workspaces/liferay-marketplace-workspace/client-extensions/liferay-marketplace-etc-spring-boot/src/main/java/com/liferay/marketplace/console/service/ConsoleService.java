/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.console.service;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Keven Leone
 */
@Component
public class ConsoleService {

	public void deleteProject(String projectId) throws Exception {
		String projectName = _consoleProjectId + "-" + projectId;

		_getWebClient(
		).delete(
		).uri(
			"/projects" + projectName
		);
	}

	public String deployApp(String orderId, String projectId) throws Exception {
		return deployApp(_consoleAuthEmailAddress, orderId, projectId);
	}

	public String deployApp(String email, String orderId, String projectId)
		throws Exception {

		return _getWebClient(
		).post(
		).uri(
			"/admin/projects/" + projectId + "/apps"
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).bodyValue(
			new JSONObject(
			).put(
				"orderId", orderId
			).put(
				"userEmail", email
			).toString()
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	public String getAuthorization() throws Exception {
		if ((_accessToken != null) &&
			(System.currentTimeMillis() < (_tokenExpirationMillis - 30000))) {

			return _accessToken;
		}

		String response = WebClient.create(
			_consoleAuthURL
		).post(
		).uri(
			"/login"
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).bodyValue(
			new JSONObject(
			).put(
				"email", _consoleAuthEmailAddress
			).put(
				"password", _consoleAuthPassword
			).toString()
		).retrieve(
		).bodyToMono(
			String.class
		).block();

		if (response == null) {
			throw new Exception("Unable to get authorization");
		}

		_accessToken = new JSONObject(
			response
		).getString(
			"token"
		);

		_tokenExpirationMillis = System.currentTimeMillis() + 900000;

		return _accessToken;
	}

	public void inviteProject(String email, String projectId, String role)
		throws Exception {

		_getWebClient(
		).post(
		).uri(
			"/projects/" + projectId + "/invite"
		).accept(
			MediaType.APPLICATION_JSON
		).contentType(
			MediaType.APPLICATION_JSON
		).bodyValue(
			new JSONObject(
			).put(
				"email", email
			).put(
				"role", role
			).toString()
		).retrieve(
		).bodyToMono(
			String.class
		).block();
	}

	public JSONObject postEnvironmentProject(String projectId)
		throws Exception {

		return new JSONObject(
			_getWebClient(
			).post(
			).uri(
				"/projects"
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_JSON
			).bodyValue(
				new JSONObject(
				).put(
					"cluster", _consoleCluster
				).put(
					"environment", true
				).put(
					"projectId", _consoleProjectId + "-" + projectId
				).toString()
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	public JSONObject setupLinkBetweenPortalInstanceAndExtensionEnvironment(
			String dxpVirtualInstanceId, String extensionProjectUid)
		throws Exception {

		return new JSONObject(
			_getWebClient(
			).post(
			).uri(
				"/lxc-extension-links"
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_JSON
			).bodyValue(
				new JSONObject(
				).put(
					"dxpProjectUid", _consoleProjectUid
				).put(
					"dxpVirtualInstanceId", dxpVirtualInstanceId
				).put(
					"extensionProjectUid", extensionProjectUid
				).toString()
			).retrieve(
			).bodyToMono(
				String.class
			).block());
	}

	private WebClient _getWebClient() throws Exception {
		return WebClient.builder(
		).baseUrl(
			_consoleAuthURL
		).defaultHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + getAuthorization()
		).build();
	}

	private String _accessToken;

	@Value("${liferay.marketplace.console.auth.email.address}")
	private String _consoleAuthEmailAddress;

	@Value("${liferay.marketplace.console.auth.password}")
	private String _consoleAuthPassword;

	@Value("${liferay.marketplace.console.auth.url}")
	private String _consoleAuthURL;

	@Value("${liferay.marketplace.console.cluster}")
	private String _consoleCluster;

	@Value("${liferay.marketplace.console.project.id}")
	private String _consoleProjectId;

	@Value("${liferay.marketplace.console.project.uid}")
	private String _consoleProjectUid;

	private long _tokenExpirationMillis;

}