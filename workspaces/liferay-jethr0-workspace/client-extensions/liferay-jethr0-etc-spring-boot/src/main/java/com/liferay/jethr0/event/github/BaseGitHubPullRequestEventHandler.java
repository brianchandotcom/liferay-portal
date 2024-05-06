/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.util.Jethr0ContextUtil;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitHubPullRequestEventHandler
	extends BaseGitHubIssueEventHandler {

	protected BaseGitHubPullRequestEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	@Override
	protected GitHubPullRequest getGitHubPullRequest()
		throws InvalidJSONException {

		if (_gitHubPullRequest != null) {
			return _gitHubPullRequest;
		}

		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject pullRequestJSONObject = messageJSONObject.optJSONObject(
			"pull_request");

		if ((pullRequestJSONObject == null) ||
			!pullRequestJSONObject.has("base") ||
			!pullRequestJSONObject.has("head") ||
			!pullRequestJSONObject.has("user")) {

			throw new InvalidJSONException(
				"Missing \"pull_request\" from message JSON");
		}

		GitHubFactory gitHubFactory = Jethr0ContextUtil.getGitHubFactory();

		_gitHubPullRequest = gitHubFactory.newGitHubPullRequest(
			pullRequestJSONObject);

		return _gitHubPullRequest;
	}

	private GitHubPullRequest _gitHubPullRequest;

}