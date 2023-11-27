/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.BaseEventHandler;
import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.issue.GitHubIssue;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.branch.repository.GitBranchEntityRepository;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitHubEventHandler extends BaseEventHandler {

	protected BaseGitHubEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	protected GitHubComment getGitHubComment() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject commentJSONObject = messageJSONObject.optJSONObject(
			"comment");

		if (commentJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"comment\" from message JSON");
		}

		return new GitHubComment(commentJSONObject);
	}

	protected GitHubIssue getGitHubIssue() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject issueJSONObject = messageJSONObject.optJSONObject("issue");

		if (issueJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"issue\" from message JSON");
		}

		return new GitHubIssue(issueJSONObject);
	}

	protected GitHubPullRequest getGitHubPullRequest()
		throws InvalidJSONException {

		if (_gitHubPullRequest != null) {
			return _gitHubPullRequest;
		}

		GitHubIssue gitHubIssue = getGitHubIssue();

		GitHubClient gitHubClient = getGitHubClient();

		_gitHubPullRequest = gitHubClient.getGitHubPullRequest(gitHubIssue);

		return _gitHubPullRequest;
	}

	protected GitHubRepository getGitHubRepository()
		throws InvalidJSONException {

		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject repositoryJSONObject = messageJSONObject.optJSONObject(
			"repository");

		if (repositoryJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"repository\" from message JSON");
		}

		return new GitHubRepository(repositoryJSONObject);
	}

	protected GitBranchEntity getSenderGitBranchEntity()
		throws InvalidJSONException {

		if (_senderGitBranchEntity != null) {
			return _senderGitBranchEntity;
		}

		GitBranchEntityRepository gitBranchEntityRepository =
			getGitBranchEntityRepository();

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		_senderGitBranchEntity = gitBranchEntityRepository.getByURL(
			gitHubPullRequest.getHeadBranchURL());

		return _senderGitBranchEntity;
	}

	protected GitBranchEntity getUpstreamGitBranchEntity()
		throws InvalidJSONException {

		if (_upstreamGitBranchEntity != null) {
			return _upstreamGitBranchEntity;
		}

		GitBranchEntityRepository gitBranchEntityRepository =
			getGitBranchEntityRepository();

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		_upstreamGitBranchEntity = gitBranchEntityRepository.getByURL(
			gitHubPullRequest.getUpstreamBranchURL());

		return _upstreamGitBranchEntity;
	}

	private GitHubPullRequest _gitHubPullRequest;
	private GitBranchEntity _senderGitBranchEntity;
	private GitBranchEntity _upstreamGitBranchEntity;

}