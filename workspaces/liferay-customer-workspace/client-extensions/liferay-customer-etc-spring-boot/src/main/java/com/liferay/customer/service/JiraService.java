/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.customer.service;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.StringUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Jenny Chen
 */
@Component
public class JiraService {

	public JSONObject getIssue(String issueKey) throws Exception {
		StringBundler sb = new StringBundler(3);

		sb.append(_URL_REST_API_2);
		sb.append("/issue/");
		sb.append(issueKey);

		try {
			return new JSONObject(
				WebClient.create(
					_jiraURL
				).get(
				).uri(
					sb.toString()
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).retrieve(
				).bodyToMono(
					String.class
				).block());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch Jira issue with key " + issueKey,
					exception);
			}
		}

		return null;
	}

	public JSONArray getVersions(String project) throws Exception {
		StringBundler sb = new StringBundler(4);

		sb.append(_URL_REST_API_2);
		sb.append("/project/");
		sb.append(project);
		sb.append("/versions");

		try {
			return new JSONArray(
				WebClient.create(
					_jiraURL
				).get(
				).uri(
					sb.toString()
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).retrieve(
				).bodyToMono(
					String.class
				).block());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch Jira versions with project " + project,
					exception);
			}
		}

		return null;
	}

	public JSONObject search(String jql, String[] returnFields)
		throws Exception {

		try {
			return new JSONObject(
				WebClient.create(
					_jiraURL
				).get(
				).uri(
					uriBuilder -> uriBuilder.path(
						_URL_REST_API_2 + "/search"
					).queryParam(
						"jql", jql
					).queryParam(
						"fields", StringUtil.merge(returnFields)
					).build()
				).accept(
					MediaType.APPLICATION_JSON
				).header(
					HttpHeaders.AUTHORIZATION, _getCredentials()
				).retrieve(
				).bodyToMono(
					String.class
				).block());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to fetch Jira issues with jql " + jql, exception);
			}
		}

		return null;
	}

	private String _getCredentials() {
		String jiraUserNameAndJiraApiToken =
			_jiraAPIEmailAddress + StringPool.COLON + _jiraAPIToken;

		return "Basic " + Base64.encode(jiraUserNameAndJiraApiToken.getBytes());
	}

	private static final String _URL_REST_API_2 = "/rest/api/2";

	private static final Log _log = LogFactory.getLog(JiraService.class);

	@Value("${liferay.customer.jira.api.email.address}")
	private String _jiraAPIEmailAddress;

	@Value("${liferay.customer.jira.api.token}")
	private String _jiraAPIToken;

	@Value("${liferay.customer.jira.url}")
	private String _jiraURL;

}