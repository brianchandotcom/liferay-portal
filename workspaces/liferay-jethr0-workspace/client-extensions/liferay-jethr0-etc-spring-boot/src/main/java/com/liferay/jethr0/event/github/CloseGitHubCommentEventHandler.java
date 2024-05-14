/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.util.StringUtil;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CloseGitHubCommentEventHandler
	extends BaseGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		GitHubComment gitHubComment = getGitHubComment();

		if (gitHubComment == null) {
			return null;
		}

		String gitHubCommentBody = gitHubComment.getBody();

		if (!gitHubCommentBody.contains("ci:close")) {
			return null;
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest == null) {
			return null;
		}

		gitHubPullRequest.close();

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Close GitHub based on comment from ",
					gitHubPullRequest.getHTMLURL(), " at ",
					StringUtil.toString(new Date())));
		}

		return gitHubPullRequest.toString();
	}

	protected CloseGitHubCommentEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	private static final Log _log = LogFactory.getLog(
		CloseGitHubCommentEventHandler.class);

}