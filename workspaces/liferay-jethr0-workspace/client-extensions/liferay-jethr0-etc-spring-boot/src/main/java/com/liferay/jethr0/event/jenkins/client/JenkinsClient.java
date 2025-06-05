/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.jenkins.client;

import com.liferay.client.extension.util.spring.boot3.BaseRestController;
import com.liferay.client.extension.util.spring.boot3.client.LiferayOAuth2AccessTokenManager;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;
import com.liferay.jethr0.util.StringUtil;
import com.liferay.petra.function.RetryableUnsafeSupplier;
import com.liferay.petra.function.UnsafeSupplier;

import java.io.IOException;

import java.net.URI;
import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JenkinsClient extends BaseRestController {

	public String requestGet(URL jenkinsURL) {
		UnsafeSupplier<String, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to post to ", jenkinsURL,
								". Retry attempt ", retryCount, " of ",
								maxRetries));
					}
				},
				() -> {
					try {
						String response = get(
							_getAuthorization(),
							_getRemoteJenkinsURI(jenkinsURL));

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
				});

		return unsafeSupplier.get();
	}

	public String requestPatch(URL jenkinsURL, JSONObject requestJSONObject) {
		UnsafeSupplier<String, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to post to ", jenkinsURL,
								". Retry attempt ", retryCount, " of ",
								maxRetries));
					}
				},
				() -> {
					try {
						String response = patch(
							_getAuthorization(), requestJSONObject.toString(),
							_getRemoteJenkinsURI(jenkinsURL));

						if (response == null) {
							throw new RuntimeException("No response");
						}

						return response;
					}
					catch (IOException ioException) {
						_refresh();

						throw new RuntimeException(ioException);
					}
				});

		return unsafeSupplier.get();
	}

	public String requestPost(URL url) {
		return requestPost(url, null);
	}

	public String requestPost(URL jenkinsURL, JSONObject requestJSONObject) {
		UnsafeSupplier<String, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to post to ", jenkinsURL,
								". Retry attempt ", retryCount, " of ",
								maxRetries));
					}
				},
				() -> {
					try {
						String response = post(
							_getAuthorization(), requestJSONObject.toString(),
							_getRemoteJenkinsURI(jenkinsURL));

						if (response == null) {
							throw new RuntimeException("No response");
						}

						return response;
					}
					catch (IOException ioException) {
						_refresh();

						throw new RuntimeException(ioException);
					}
				});

		return unsafeSupplier.get();
	}

	public String requestPut(URL jenkinsURL, JSONObject requestJSONObject) {
		URI remoteJenkinsURI = _getRemoteJenkinsURI(jenkinsURL);

		UnsafeSupplier<String, RuntimeException> unsafeSupplier =
			new RetryableUnsafeSupplier<>(
				(exception, maxRetries, retryCount) -> {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringUtil.combine(
								"Unable to post to ", remoteJenkinsURI,
								". Retry attempt ", retryCount, " of ",
								maxRetries));
					}
				},
				() -> {
					try {
						String response = put(
							_getAuthorization(), requestJSONObject.toString(),
							remoteJenkinsURI);

						if (response == null) {
							throw new RuntimeException("No response");
						}

						return response;
					}
					catch (IOException ioException) {
						_refresh();

						throw new RuntimeException(ioException);
					}
				});

		return unsafeSupplier.get();
	}

	private String _getAuthorization() throws IOException {
		return _liferayOAuth2AccessTokenManager.getAuthorization("extra");
	}

	private URI _getRemoteJenkinsURI(URL jenkinsURL) {
		if (jenkinsURL == null) {
			throw new NullPointerException("Please set 'jenkinsURL'");
		}

		Matcher jenkinsURLMatcher = _jenkinsURLPattern.matcher(
			String.valueOf(jenkinsURL));

		if (!jenkinsURLMatcher.find()) {
			throw new RuntimeException("Invalid jenkinsURL " + jenkinsURL);
		}

		return UriComponentsBuilder.fromPath(
			jenkinsURLMatcher.group("urlPath")
		).scheme(
			"https"
		).host(
			jenkinsURLMatcher.group("masterHostname") + ".jethr0.liferay.com"
		).build(
		).toUri();
	}

	private void _refresh() {
		_liferayOAuth2AccessTokenManager.refresh("extra");
	}

	private static final Log _log = LogFactory.getLog(JenkinsClient.class);

	private static final Pattern _jenkinsURLPattern = Pattern.compile(
		"https?://(?<masterHostname>test-\\d+-\\d+)(\\.jethr0)?" +
			"(\\.liferay\\.com)?/+?(?<urlPath>.+)?");

	@Autowired
	private GitBranchEntityRepository _gitBranchEntityRepository;

	@Autowired
	private LiferayOAuth2AccessTokenManager _liferayOAuth2AccessTokenManager;

}