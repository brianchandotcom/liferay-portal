/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.marketplace.service;

import com.liferay.client.extension.util.spring.boot.service.BaseService;
import com.liferay.petra.string.StringBundler;

import java.time.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.util.DefaultUriBuilderFactory;

import reactor.util.retry.Retry;

/**
 * @author Keven Leone
 */
@Component
public class ConsoleService extends BaseService {

	public void deleteProject(String projectId) throws Exception {
		String projectName = _consoleProjectPrefix + "-ext" + projectId;

		delete(getAuthorization(), "", "/projects/" + projectName);
	}

	public JSONObject deployApp(
			String emailAddress, String orderId, String projectId)
		throws Exception {

		JSONObject jsonObject = new JSONObject(
			post(
				getAuthorization(),
				new JSONObject(
				).put(
					"orderId", orderId
				).put(
					"userEmail", emailAddress
				).toString(),
				"/admin/projects/" + projectId + "/apps"));

		if (_log.isInfoEnabled()) {
			_log.info("Deployed app for project " + projectId);
		}

		return jsonObject;
	}

	public String getAuthorization() throws Exception {
		if ((_authorization != null) &&
			(System.currentTimeMillis() < (_tokenExpirationMillis - 30000))) {

			return _authorization;
		}

		String json = post(
			null,
			new JSONObject(
			).put(
				"email", _consoleAuthEmailAddress
			).put(
				"password", _consoleAuthPassword
			).toString(),
			"/login");

		if (json == null) {
			throw new Exception("Unable to get authorization");
		}

		String token = new JSONObject(
			json
		).getString(
			"token"
		);

		_authorization = "Bearer " + token;

		_tokenExpirationMillis = System.currentTimeMillis() + 900000;

		return _authorization;
	}

	public String getProjectsUsage(String userEmail) throws Exception {
		return get(
			getAuthorization(),
			_defaultUriBuilderFactory.builder(
			).path(
				"/admin/user-projects-plan-usage"
			).queryParam(
				"userEmail", userEmail
			).build(
			).toString());
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

	public void uninstallApp(long orderId) throws Exception {
		delete(getAuthorization(), "", "/apps/" + orderId);
	}

	@Override
	protected String getWebClientBaseURL() {
		return _consoleAuthURL;
	}

	@Override
	protected ExchangeFilterFunction getWebClientExchangeFilterFunction() {
		return (clientRequest, exchangeFunction) -> exchangeFunction.exchange(
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

		post(
			getAuthorization(),
			new JSONObject(
			).put(
				"email", emailAddress
			).put(
				"role", "admin"
			).toString(),
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

		post(
			getAuthorization(),
			new JSONObject(
			).put(
				"dxpProjectUid", _consoleProjectUid
			).put(
				"dxpVirtualInstanceId", dxpVirtualInstanceId
			).put(
				"extensionProjectUid", extensionProjectUid
			).toString(),
			"/lxc-extension-links");

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Linked Liferay virtual instance ", dxpVirtualInstanceId,
					" with project ", extensionProjectUid));
		}
	}

	private JSONObject _postProject(String projectId) throws Exception {
		JSONObject jsonObject = new JSONObject(
			post(
				getAuthorization(),
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
				).toString(),
				"/projects"));

		if (_log.isInfoEnabled()) {
			_log.info("Created project " + jsonObject);
		}

		return jsonObject;
	}

	private static final Log _log = LogFactory.getLog(ConsoleService.class);

	private String _authorization;

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

	private final DefaultUriBuilderFactory _defaultUriBuilderFactory =
		new DefaultUriBuilderFactory();
	private long _tokenExpirationMillis;

	@Value("${liferay.marketplace.trial.admin.email.address}")
	private String _trialAdminEmailAddress;

}