/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.issue;

import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubIssue {

	public GitHubIssue(GitHubFactory gitHubFactory, JSONObject jsonObject) {
		_gitHubFactory = gitHubFactory;
		_jsonObject = jsonObject;
	}

	public void close() {
		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("state", "closed");

		GitHubClient gitHubClient = getGitHubClient();

		gitHubClient.requestPatch(getPullRequestAPIURL(), requestJSONObject);
	}

	public GitHubComment createGitHubComment(String body) {
		JSONObject requestJSONObject = new JSONObject();

		requestJSONObject.put("body", body);

		GitHubClient gitHubClient = getGitHubClient();

		JSONObject responseJSONObject = new JSONObject(
			gitHubClient.requestPost(getCommentsURL(), requestJSONObject));

		return _gitHubFactory.newGitHubComment(responseJSONObject);
	}

	public URL getCommentsURL() {
		return StringUtil.toURL(_jsonObject.getString("comments_url"));
	}

	public GitHubClient getGitHubClient() {
		return _gitHubFactory.getGitHubClient();
	}

	public GitHubPullRequest getGitHubPullRequest() {
		if (_gitHubPullRequest != null) {
			return _gitHubPullRequest;
		}

		GitHubClient gitHubClient = getGitHubClient();

		JSONObject responseJSONObject = new JSONObject(
			gitHubClient.requestGet(getPullRequestAPIURL()));

		_gitHubPullRequest = _gitHubFactory.newGitHubPullRequest(
			responseJSONObject);

		return _gitHubPullRequest;
	}

	public URL getHTMLURL() {
		return StringUtil.toURL(_jsonObject.getString("html_url"));
	}

	public int getNumber() {
		Matcher pullRequestURLMatcher = _pullRequestURLPattern.matcher(
			String.valueOf(getHTMLURL()));

		if (!pullRequestURLMatcher.find()) {
			return 0;
		}

		return Integer.valueOf(pullRequestURLMatcher.group("number"));
	}

	public URL getPullRequestAPIURL() {
		JSONObject jsonObject = _jsonObject.getJSONObject("pull_request");

		return StringUtil.toURL(jsonObject.getString("url"));
	}

	public String getReceiverUserName() {
		Matcher pullRequestURLMatcher = _pullRequestURLPattern.matcher(
			String.valueOf(getHTMLURL()));

		if (!pullRequestURLMatcher.find()) {
			return null;
		}

		return pullRequestURLMatcher.group("receiverUserName");
	}

	public String getRepositoryName() {
		Matcher pullRequestURLMatcher = _pullRequestURLPattern.matcher(
			String.valueOf(getHTMLURL()));

		if (!pullRequestURLMatcher.find()) {
			return null;
		}

		return pullRequestURLMatcher.group("repositoryName");
	}

	private static final Pattern _pullRequestURLPattern = Pattern.compile(
		"https://github.com/(?<receiverUserName>\\d+)/(?<repositoryName>\\d+)" +
			"/pull/(?<number>\\d+)");

	private final GitHubFactory _gitHubFactory;
	private GitHubPullRequest _gitHubPullRequest;
	private final JSONObject _jsonObject;

}