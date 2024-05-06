/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SynchronizeGitHubPullRequestEventHandler
	extends BaseGitHubPullRequestEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest == null) {
			return null;
		}

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		if ((receiverGitHubUser == null) ||
			!Objects.equals(receiverGitHubUser.getName(), "brianchandotcom")) {

			return null;
		}

		GitHubComment gitHubComment = gitHubPullRequest.comment(
			StringUtil.combine(
				"Closing and locking pull request because pull requests sent ",
				"to this user may not be updated. Please resend this pull ",
				"request."));

		gitHubPullRequest.close();

		gitHubPullRequest.lock();

		return gitHubComment.getBody();
	}

	protected SynchronizeGitHubPullRequestEventHandler(
		JSONObject messageJSONObject) {

		super(messageJSONObject);
	}

}