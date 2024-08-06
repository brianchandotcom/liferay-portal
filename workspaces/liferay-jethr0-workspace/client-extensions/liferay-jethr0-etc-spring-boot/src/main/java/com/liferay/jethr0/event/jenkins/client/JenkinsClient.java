/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins.client;

import com.liferay.client.extension.util.spring.boot.LiferayOAuth2AccessTokenManager;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;
import com.liferay.jethr0.util.BaseRetryable;
import com.liferay.jethr0.util.Retryable;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsClient {

	public String requestGet(URL jenkinsURL) {
		final String remoteJenkinsURL = _getRemoteJenkinsURL(jenkinsURL);

		Retryable<String> retryable = new BaseRetryable<String>() {

			@Override
			public String execute() {
				try {
					String response = WebClient.create(
						remoteJenkinsURL
					).get(
					).accept(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization", _getAuthorization()
					).retrieve(
					).bodyToMono(
						String.class
					).block();

					if (response == null) {
						throw new RuntimeException(
							"Unable to get authorization");
					}

					return response;
				}
				catch (IOException ioException) {
					_refresh();

					throw new RuntimeException(ioException);
				}
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to post to ", jenkinsURL, ". Retry attempt ",
					retryCount, " of ", maxRetries);
			}

		};

		return retryable.executeWithRetries();
	}

	public String requestPatch(URL jenkinsURL, JSONObject requestJSONObject) {
		final String remoteJenkinsURL = _getRemoteJenkinsURL(jenkinsURL);

		Retryable<String> retryable = new BaseRetryable<String>() {

			@Override
			public String execute() {
				try {
					String response = WebClient.create(
						remoteJenkinsURL
					).patch(
					).accept(
						MediaType.APPLICATION_JSON
					).contentType(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization", _getAuthorization()
					).body(
						BodyInserters.fromValue(requestJSONObject.toString())
					).retrieve(
					).bodyToMono(
						String.class
					).block();

					if (response == null) {
						throw new RuntimeException("No response");
					}

					return response;
				}
				catch (IOException ioException) {
					_refresh();

					throw new RuntimeException(ioException);
				}
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to post to ", jenkinsURL, ". Retry attempt ",
					retryCount, " of ", maxRetries);
			}

		};

		return retryable.executeWithRetries();
	}

	public String requestPost(URL url) {
		return requestPost(url, null);
	}

	public String requestPost(URL jenkinsURL, JSONObject requestJSONObject) {
		final String remoteJenkinsURL = _getRemoteJenkinsURL(jenkinsURL);

		Retryable<String> retryable = new BaseRetryable<String>() {

			@Override
			public String execute() {
				try {
					String response = WebClient.create(
						remoteJenkinsURL
					).post(
					).accept(
						MediaType.APPLICATION_JSON
					).contentType(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization", _getAuthorization()
					).body(
						BodyInserters.fromValue(requestJSONObject.toString())
					).retrieve(
					).bodyToMono(
						String.class
					).block();

					if (response == null) {
						throw new RuntimeException("No response");
					}

					return response;
				}
				catch (IOException ioException) {
					_refresh();

					throw new RuntimeException(ioException);
				}
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to post to ", jenkinsURL, ". Retry attempt ",
					retryCount, " of ", maxRetries);
			}

		};

		return retryable.executeWithRetries();
	}

	public String requestPut(URL jenkinsURL, JSONObject requestJSONObject) {
		final String remoteJenkinsURL = _getRemoteJenkinsURL(jenkinsURL);

		Retryable<String> retryable = new BaseRetryable<String>() {

			@Override
			public String execute() {
				try {
					String response = WebClient.create(
						remoteJenkinsURL
					).put(
					).accept(
						MediaType.APPLICATION_JSON
					).contentType(
						MediaType.APPLICATION_JSON
					).header(
						"Authorization", _getAuthorization()
					).body(
						BodyInserters.fromValue(requestJSONObject.toString())
					).retrieve(
					).bodyToMono(
						String.class
					).block();

					if (response == null) {
						throw new RuntimeException("No response");
					}

					return response;
				}
				catch (IOException ioException) {
					_refresh();

					throw new RuntimeException(ioException);
				}
			}

			@Override
			protected String getRetryMessage(int retryCount) {
				return StringUtil.combine(
					"Unable to post to ", remoteJenkinsURL, ". Retry attempt ",
					retryCount, " of ", maxRetries);
			}

		};

		return retryable.executeWithRetries();
	}

	private String _getAuthorization() throws IOException {
		return _liferayOAuth2AccessTokenManager.getAuthorization("extra");
	}

	private String _getRemoteJenkinsURL(URL jenkinsURL) {
		if (jenkinsURL == null) {
			throw new NullPointerException("Please set 'jenkinsURL'");
		}

		Matcher jenkinsURLMatcher = _jenkinsURLPattern.matcher(
			String.valueOf(jenkinsURL));

		if (!jenkinsURLMatcher.find()) {
			throw new RuntimeException("Invalid jenkinsURL " + jenkinsURL);
		}

		return StringUtil.combine(
			"https://", jenkinsURLMatcher.group("masterHostname"),
			".jethr0.liferay.com/", jenkinsURLMatcher.group("urlPath"));
	}

	private void _refresh() {
		_liferayOAuth2AccessTokenManager.refresh("extra");
	}

	private static final Pattern _jenkinsURLPattern = Pattern.compile(
		"https?://(?<masterHostname>test-\\d+-\\d+)(\\.jethr0)?" +
			"(\\.liferay\\.com)?/+?(?<urlPath>.+)?");

	@Autowired
	private GitBranchEntityRepository _gitBranchEntityRepository;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}