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
import com.liferay.jethr0.util.PropertiesUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitHubEventHandler extends BaseEventHandler {

	protected BaseGitHubEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	protected Set<String> getAvailableTestSuites()
		throws InvalidJSONException, IOException {

		Set<String> availableTestSuites = new HashSet<>();

		String upstreamAvailableTestSuites = _getUpstreamBranchCIPropertyValue(
			"ci.test.available.suites");

		if (!StringUtil.isNullOrEmpty(upstreamAvailableTestSuites)) {
			Collections.addAll(
				availableTestSuites, upstreamAvailableTestSuites.split(","));
		}

		String senderAvailableTestSuites = _getSenderBranchCIPropertyValue(
			"ci.test.available.suites");

		if (!StringUtil.isNullOrEmpty(senderAvailableTestSuites)) {
			Collections.addAll(
				availableTestSuites, senderAvailableTestSuites.split(","));
		}

		return availableTestSuites;
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

	private String _getSenderBranchCIPropertyValue(String propertyName)
		throws InvalidJSONException, IOException {

		GitBranchEntity gitBranchEntity = getSenderGitBranchEntity();

		if (gitBranchEntity == null) {
			return null;
		}

		Properties properties = gitBranchEntity.getProperties("ci.properties");

		if (properties == null) {
			return null;
		}

		return PropertiesUtil.getPropertyValue(properties, propertyName);
	}

	private String _getUpstreamBranchCIPropertyValue(String propertyName)
		throws InvalidJSONException, IOException {

		GitBranchEntity gitBranchEntity = getUpstreamGitBranchEntity();

		if (gitBranchEntity == null) {
			return null;
		}

		Properties properties = gitBranchEntity.getProperties("ci.properties");

		if (properties == null) {
			return null;
		}

		return PropertiesUtil.getPropertyValue(properties, propertyName);
	}

	private GitHubPullRequest _gitHubPullRequest;
	private GitBranchEntity _senderGitBranchEntity;
	private GitBranchEntity _upstreamGitBranchEntity;

}