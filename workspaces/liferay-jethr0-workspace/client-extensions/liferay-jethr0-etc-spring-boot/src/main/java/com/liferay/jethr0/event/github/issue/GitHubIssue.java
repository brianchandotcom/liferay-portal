/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github.issue;

import com.liferay.jethr0.event.github.GitHubFactory;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GitHubIssue {

	public GitHubIssue(GitHubFactory gitHubFactory, JSONObject jsonObject) {
		_gitHubFactory = gitHubFactory;
		_jsonObject = jsonObject;
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

		JSONObject jsonObject = new JSONObject(
			gitHubClient.requestGet(getPullRequestAPIURL()));

		_gitHubPullRequest = _gitHubFactory.newGitHubPullRequest(jsonObject);

		return _gitHubPullRequest;
	}

	public URL getHTMLURL() {
		return StringUtil.toURL(_jsonObject.getString("html_url"));
	}

	public URL getPullRequestAPIURL() {
		JSONObject jsonObject = _jsonObject.getJSONObject("pull_request");

		return StringUtil.toURL(jsonObject.getString("url"));
	}

	private final GitHubFactory _gitHubFactory;
	private GitHubPullRequest _gitHubPullRequest;
	private final JSONObject _jsonObject;

}