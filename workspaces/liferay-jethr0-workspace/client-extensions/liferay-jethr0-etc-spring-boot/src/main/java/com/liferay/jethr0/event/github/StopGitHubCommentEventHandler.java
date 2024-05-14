/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.jenkins.client.JenkinsClient;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class StopGitHubCommentEventHandler
	extends BaseGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		GitHubComment gitHubComment = getGitHubComment();

		if (gitHubComment == null) {
			return null;
		}

		Matcher stopCommentMatcher = _stopCommentPattern.matcher(
			gitHubComment.getBody());

		String testSuite = null;

		if (stopCommentMatcher.find()) {
			testSuite = stopCommentMatcher.group("testSuite");
		}

		for (String buildURL : _getGitHubCommentBuildURLs()) {
			if (testSuite != null) {
				Map<String, String> buildParameters = _getBuildParameters(
					buildURL);

				String ciTestSuite = buildParameters.get("CI_TEST_SUITE");

				if ((ciTestSuite == null) || !ciTestSuite.equals(testSuite)) {
					continue;
				}
			}

			try {
				_stopTopLevelBuild(buildURL);
			}
			catch (IOException ioException) {
				if (_log.isWarnEnabled()) {
					_log.warn(ioException);
				}
			}
		}

		return null;
	}

	protected StopGitHubCommentEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private Map<String, String> _getBuildParameters(String buildURL) {
		GitHubClient gitHubClient = Jethr0ContextUtil.getGitHubClient();

		String response = gitHubClient.requestGet(
			StringUtil.toURL(
				buildURL + "/api/json?tree=actions[parameters[name,value]]"));

		if (response == null) {
			throw new RuntimeException("Unable to get authorization");
		}

		Map<String, String> buildParameters = new HashMap<>();

		JSONObject responseJSONObject = new JSONObject(response);

		JSONArray actionsJSONArray = responseJSONObject.getJSONArray("actions");

		for (int i = 0; i < actionsJSONArray.length(); i++) {
			JSONObject actionJSONObject = actionsJSONArray.getJSONObject(i);

			if (!Objects.equals(
					actionJSONObject.getString("_class"),
					"hudson.model.ParametersAction")) {

				continue;
			}

			JSONArray parametersJSONArray = actionJSONObject.getJSONArray(
				"parameters");

			for (int j = 0; j < parametersJSONArray.length(); j++) {
				JSONObject parameterJSONObject =
					parametersJSONArray.getJSONObject(j);

				buildParameters.put(
					parameterJSONObject.getString("name"),
					parameterJSONObject.getString("value"));
			}
		}

		return buildParameters;
	}

	private Set<String> _getDownstreamBuildURLs(String buildURL) {
		GitHubClient gitHubClient = Jethr0ContextUtil.getGitHubClient();

		String response = gitHubClient.requestGet(
			StringUtil.toURL(buildURL + "/logText/progressiveText"));

		if (StringUtil.isNullOrEmpty(response)) {
			return Collections.emptySet();
		}

		Matcher matcher = _jenkinsConsoleBuildURLPattern.matcher(response);

		Set<String> downstreamBuildURLs = new HashSet<>();

		while (matcher.find()) {
			downstreamBuildURLs.add(matcher.group("buildURL"));
		}

		return downstreamBuildURLs;
	}

	private List<String> _getGitHubCommentBuildURLs()
		throws InvalidJSONException {

		List<String> buildURLs = new ArrayList<>();

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		for (GitHubComment gitHubComment :
				gitHubPullRequest.getGitHubComments()) {

			Matcher matcher = _gitHubCommentBuildURLPattern.matcher(
				gitHubComment.getBody());

			if (matcher.find()) {
				buildURLs.add(matcher.group("buildURL"));
			}
		}

		return buildURLs;
	}

	private void _stopBuild(String buildURL) throws IOException {
		JenkinsClient jenkinsClient = Jethr0ContextUtil.getJenkinsClient();

		String response = jenkinsClient.requestGet(
			StringUtil.toURL(buildURL + "/api/json?tree=result"));

		if (StringUtil.isNullOrEmpty(response)) {
			throw new RuntimeException();
		}

		try {
			JSONObject jsonObject = new JSONObject(response);

			if (!jsonObject.has("result") || !jsonObject.isNull("result")) {
				return;
			}
		}
		catch (JSONException jsonException) {
			if (_log.isWarnEnabled()) {
				_log.warn(jsonException);
			}

			return;
		}

		jenkinsClient.requestPost(StringUtil.toURL(buildURL + "/stop"));

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Stopping Jenkins build ", buildURL, " at ",
					StringUtil.toString(new Date())));
		}
	}

	private void _stopTopLevelBuild(String buildURL) throws IOException {
		_stopBuild(buildURL);

		for (String downstreamBuildURL : _getDownstreamBuildURLs(buildURL)) {
			_stopBuild(downstreamBuildURL);
		}
	}

	private static final Log _log = LogFactory.getLog(
		StopGitHubCommentEventHandler.class);

	private static final Pattern _gitHubCommentBuildURLPattern =
		Pattern.compile(
			"Build[\\w\\s]*started.*Job Link: <a href=\"(?<buildURL>[^\"]+)\"");
	private static final Pattern _jenkinsConsoleBuildURLPattern =
		Pattern.compile(
			"Build \\'.*\\' started at (?<buildURL>https?://test-\\d+-\\d+" +
				"(\\.liferay\\.com)?/job/[^/]+/\\d+)/?\\.");
	private static final Pattern _stopCommentPattern = Pattern.compile(
		"ci:stop:(?<testSuite>[^:\\s]+).*");

}