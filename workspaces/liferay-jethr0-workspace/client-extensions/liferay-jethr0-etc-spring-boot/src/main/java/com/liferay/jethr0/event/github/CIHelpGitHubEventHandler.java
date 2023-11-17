/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.comment.GitHubComment;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CIHelpGitHubEventHandler extends BaseGitHubEventHandler {

	@Override
	public String process() throws InvalidJSONException {
		GitHubClient gitHubClient = getGitHubClient();

		GitHubComment gitHubComment = gitHubClient.createGitHubComment(
			getGitHubIssue(), _getMessage());

		return gitHubComment.getBody();
	}

	protected CIHelpGitHubEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private String _getMessage() {
		return "## Available CI commands:";
	}

}