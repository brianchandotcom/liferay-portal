/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ReopenGitHubCommentEventHandler
	extends BaseGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest == null) {
			return null;
		}

		gitHubPullRequest.open();

		return gitHubPullRequest.toString();
	}

	protected ReopenGitHubCommentEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

}