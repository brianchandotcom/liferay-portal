/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.console.service;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

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

	public void deployApp(String orderId, String projectId) throws Exception {
		deployApp(_consoleAuthEmail, orderId, projectId);
	}

	public void deployApp(String email, String orderId, String projectId)
		throws Exception {

		_post(
			new JSONObject(
			).put(
				"orderId", orderId
			).put(
				"userEmail", email
			),
			"/admin/projects/" + projectId + "/apps");
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
				"email", _consoleAuthEmail
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

	public void setupCloudProjectInstallation(String virtualHost, long orderId)
		throws Exception {

		JSONObject projectJSONObject = _postEnvironmentProject(
			false, _consoleProjectId + orderId);

		if (_log.isInfoEnabled()) {
			_log.info("Project created " + projectJSONObject.toString());
		}

		JSONObject environmentProjectJSONObject = _postEnvironmentProject(
			true, projectJSONObject.getString("projectId") + "-extprd");

		if (_log.isInfoEnabled()) {
			_log.info(
				"Environment Project created " +
					environmentProjectJSONObject.toString());
		}

		_inviteProject(
			_marketplaceTrialAdminEmail,
			environmentProjectJSONObject.getString("projectId"), "admin");

		if (_log.isInfoEnabled()) {
			_log.info(
				"Invited " + _marketplaceTrialAdminEmail +
					" as admin to cloud environment");
		}

		_setupLinkBetweenPortalInstanceAndExtensionEnvironment(
			virtualHost, environmentProjectJSONObject.getString("id"));

		if (_log.isInfoEnabled()) {
			_log.info(
				virtualHost + " Linked to environment: " +
					environmentProjectJSONObject.getString("id"));
		}

		deployApp(
			String.valueOf(orderId),
			environmentProjectJSONObject.getString("projectId"));

		if (_log.isInfoEnabled()) {
			_log.info(
				"Marketplace App deployed to " +
					environmentProjectJSONObject.getString("projectId"));
		}
	}

	private WebClient _getWebClient() throws Exception {
		return WebClient.builder(
		).baseUrl(
			_consoleAuthURL
		).defaultHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + getAuthorization()
		).build();
	}

	private void _inviteProject(String email, String projectId, String role)
		throws Exception {

		_post(
			new JSONObject(
			).put(
				"email", email
			).put(
				"role", role
			),
			"/projects/" + projectId + "/invite");
	}

	private JSONObject _post(JSONObject jsonObject, String path)
		throws Exception {

		return new JSONObject(
			_getWebClient(
			).post(
			).uri(
				path
			).accept(
				MediaType.APPLICATION_JSON
			).contentType(
				MediaType.APPLICATION_JSON
			).bodyValue(
				jsonObject.toString()
			).exchangeToMono(
				clientResponse -> {
					HttpStatus httpStatus = clientResponse.statusCode();

					if (Objects.equals(
							clientResponse.statusCode(),
							HttpStatus.NO_CONTENT)) {

						return Mono.just("{}");
					}
					else if (httpStatus.is2xxSuccessful()) {
						return clientResponse.bodyToMono(String.class);
					}
					else if (httpStatus.is4xxClientError()) {
						return Mono.just(httpStatus.getReasonPhrase());
					}

					Mono<WebClientResponseException> mono =
						clientResponse.createException();

					return mono.flatMap(Mono::error);
				}
			).block());
	}

	private JSONObject _postEnvironmentProject(
			boolean environment, String projectId)
		throws Exception {

		return _post(
			new JSONObject(
			).put(
				"cluster", _consoleCluster
			).put(
				"environment", environment
			).put(
				"projectId", projectId
			),
			"/projects");
	}

	private JSONObject _setupLinkBetweenPortalInstanceAndExtensionEnvironment(
			String dxpVirtualInstanceId, String extensionProjectUid)
		throws Exception {

		return _post(
			new JSONObject(
			).put(
				"dxpProjectUid", _consoleProjectUid
			).put(
				"dxpVirtualInstanceId", dxpVirtualInstanceId
			).put(
				"extensionProjectUid", extensionProjectUid
			),
			"/lxc-extension-links");
	}

	private static final Log _log = LogFactory.getLog(ConsoleService.class);

	private String _accessToken;

	@Value("${liferay.marketplace.console.auth.email}")
	private String _consoleAuthEmail;

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

	@Value("${liferay.marketplace.trial.admin.email}")
	private String _marketplaceTrialAdminEmail;

	private long _tokenExpirationMillis;

}