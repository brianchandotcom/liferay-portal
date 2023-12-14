/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.issue.GitHubIssue;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class GitHubFactory {

	public GitHubClient getGitHubClient() {
		return _gitHubClient;
	}

	public GitHubIssue newGitHubIssue(JSONObject jsonObject) {
		return new GitHubIssue(this, jsonObject);
	}

	public GitHubPullRequest newGitHubPullRequest(JSONObject jsonObject) {
		return new GitHubPullRequest(this, jsonObject);
	}

	@Autowired
	private GitHubClient _gitHubClient;

}