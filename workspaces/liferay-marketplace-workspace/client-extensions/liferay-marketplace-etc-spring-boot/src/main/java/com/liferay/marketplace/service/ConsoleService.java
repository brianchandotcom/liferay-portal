/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.petra.string.StringBundler;

import java.time.Duration;

import java.util.Objects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import reactor.core.publisher.Mono;

import reactor.util.retry.Retry;

/**
 * @author Keven Leone
 */
@Component
public class ConsoleService {

	public void deleteProject(String projectId) throws Exception {
		String projectName = _consoleProjectPrefix + "-ext" + projectId;

		getWebClient(
		).delete(
		).uri(
			"/projects/" + projectName
		).retrieve(
		).bodyToMono(
			Void.class
		).block();

		if (_log.isInfoEnabled()) {
			_log.info("Deleted project " + projectName);
		}
	}

	public JSONObject deployApp(
			String emailAddress, String orderId, String projectId)
		throws Exception {

		JSONObject jsonObject = _post(
			new JSONObject(
			).put(
				"orderId", orderId
			).put(
				"userEmail", emailAddress
			),
			"/admin/projects/" + projectId + "/apps");

		if (_log.isInfoEnabled()) {
			_log.info("Deployed app for project " + projectId);
		}

		return jsonObject;
	}

	public String getAccessToken() throws Exception {
		if ((_accessToken != null) &&
			(System.currentTimeMillis() < (_tokenExpirationMillis - 30000))) {

			return _accessToken;
		}

		String json = WebClient.builder(
		).baseUrl(
			_consoleAuthURL
		).filter(
			_getRetryExchangeFilterFunction()
		).build(
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

		if (json == null) {
			throw new Exception("Unable to get authorization");
		}

		_accessToken = new JSONObject(
			json
		).getString(
			"token"
		);

		_tokenExpirationMillis = System.currentTimeMillis() + 900000;

		return _accessToken;
	}

	public WebClient getWebClient() throws Exception {
		return WebClient.builder(
		).baseUrl(
			_consoleAuthURL
		).defaultHeader(
			HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken()
		).filter(
			_getRetryExchangeFilterFunction()
		).build();
	}

	public void setUpProject(String dxpVirtualInstanceId, long orderId)
		throws Exception {

		JSONObject jsonObject = _postProject(
			_consoleProjectPrefix + "-ext" + orderId);

		_inviteProject(
			_trialAdminEmailAddress, jsonObject.getString("projectId"));

		_linkDXPWithProject(dxpVirtualInstanceId, jsonObject.getString("id"));

		deployApp(
			_consoleAuthEmailAddress, String.valueOf(orderId),
			jsonObject.getString("projectId"));
	}

	public void uninstallApp(long orderId) {
		getWebClient(
		).delete(
		).uri(
			"/apps/" + orderId
		).retrieve(
		).bodyToMono(
			Void.class
		).block();
	}

	private ExchangeFilterFunction _getRetryExchangeFilterFunction() {
		return (clientRequest, next) -> next.exchange(
			clientRequest
		).retryWhen(
			Retry.fixedDelay(
				3, Duration.ofSeconds(5)
			).doBeforeRetry(
				retrySignal -> {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Retry attempt " + retrySignal.totalRetries() + 1);
					}
				}
			)
		);
	}

	private void _inviteProject(String emailAddress, String projectId)
		throws Exception {

		_post(
			new JSONObject(
			).put(
				"email", emailAddress
			).put(
				"role", "admin"
			),
			"/projects/" + projectId + "/invite");

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Invited ", emailAddress, " to project ", projectId));
		}
	}

	private void _linkDXPWithProject(
			String dxpVirtualInstanceId, String extensionProjectUid)
		throws Exception {

		_post(
			new JSONObject(
			).put(
				"dxpProjectUid", _consoleProjectUid
			).put(
				"dxpVirtualInstanceId", dxpVirtualInstanceId
			).put(
				"extensionProjectUid", extensionProjectUid
			),
			"/lxc-extension-links");

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Linked Liferay virtual instance ", dxpVirtualInstanceId,
					" with project ", extensionProjectUid));
		}
	}

	private JSONObject _post(JSONObject jsonObject, String path)
		throws Exception {

		return new JSONObject(
			getWebClient(
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

	private JSONObject _postProject(String projectId) throws Exception {
		JSONObject jsonObject = _post(
			new JSONObject(
			).put(
				"cluster", _consoleCluster
			).put(
				"environment", true
			).put(
				"metadata",
				new JSONObject(
				).put(
					"skipCloudProviderIamConfiguration", true
				)
			).put(
				"projectId", projectId
			),
			"/projects");

		if (_log.isInfoEnabled()) {
			_log.info("Created project " + jsonObject);
		}

		return jsonObject;
	}

	private static final Log _log = LogFactory.getLog(ConsoleService.class);

	private String _accessToken;

	@Value("${liferay.marketplace.console.auth.email.address}")
	private String _consoleAuthEmailAddress;

	@Value("${liferay.marketplace.console.auth.password}")
	private String _consoleAuthPassword;

	@Value("${liferay.marketplace.console.auth.url}")
	private String _consoleAuthURL;

	@Value("${liferay.marketplace.console.cluster}")
	private String _consoleCluster;

	@Value("${liferay.marketplace.console.project.prefix}")
	private String _consoleProjectPrefix;

	@Value("${liferay.marketplace.console.project.uid}")
	private String _consoleProjectUid;

	private long _tokenExpirationMillis;

	@Value("${liferay.marketplace.trial.admin.email.address}")
	private String _trialAdminEmailAddress;

}