/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.util.Jethr0ContextUtil;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitHubCommentEventHandler
	extends BaseGitHubIssueEventHandler {

	public boolean isCommenterLiferayGitHubUser() throws InvalidJSONException {
		GitHubComment gitHubComment = getGitHubComment();

		if (gitHubComment == null) {
			return false;
		}

		GitHubUser commenterGitHubUser = gitHubComment.getCommenterGitHubUser();

		if (!commenterGitHubUser.isLiferayUser()) {
			return false;
		}

		return true;
	}

	protected BaseGitHubCommentEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	protected GitHubComment getGitHubComment() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject commentJSONObject = messageJSONObject.optJSONObject(
			"comment");

		if (commentJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"comment\" from message JSON");
		}

		GitHubFactory gitHubFactory = Jethr0ContextUtil.getGitHubFactory();

		return gitHubFactory.newGitHubComment(commentJSONObject);
	}

	@Override
	protected boolean involvesLiferayGitHubUsersOnly()
		throws InvalidJSONException {

		if (isCommenterLiferayGitHubUser() && isReceiverLiferayGitHubUser() &&
			isSenderLiferayGitHubUser()) {

			return true;
		}

		return false;
	}

}