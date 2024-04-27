/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.commit.GitHubCommit;
import com.liferay.jethr0.event.github.file.GitHubFile;
import com.liferay.jethr0.event.github.issue.GitHubIssue;
import com.liferay.jethr0.event.github.organization.GitHubOrganization;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.ref.GitHubRef;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.event.github.status.GitHubStatus;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

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

	public GitHubComment newGitHubComment(JSONObject jsonObject) {
		return new GitHubComment(this, jsonObject);
	}

	public GitHubCommit newGitHubCommit(JSONObject jsonObject) {
		return new GitHubCommit(this, jsonObject);
	}

	public GitHubFile newGitHubFile(JSONObject jsonObject) {
		return new GitHubFile(this, jsonObject);
	}

	public GitHubIssue newGitHubIssue(JSONObject jsonObject) {
		return new GitHubIssue(this, jsonObject);
	}

	public GitHubOrganization newGitHubOrganization(JSONObject jsonObject) {
		return new GitHubOrganization(this, jsonObject);
	}

	public GitHubPullRequest newGitHubPullRequest(JSONObject jsonObject) {
		return new GitHubPullRequest(this, jsonObject);
	}

	public GitHubRef newGitHubRef(URL gitHubRefURL) {
		URL gitHubRefAPIURL = StringUtil.toURL(
			StringUtil.combine(
				"https://api.github.com/repos/",
				GitHubRef.getUserName(gitHubRefURL), "/",
				GitHubRef.getRepositoryName(gitHubRefURL), "/branches/",
				GitHubRef.getRefName(gitHubRefURL)));

		JSONObject requestJSONObject = new JSONObject(
			_gitHubClient.requestGet(gitHubRefAPIURL));

		return new GitHubRef(this, gitHubRefURL, requestJSONObject);
	}

	public GitHubRef newGitHubRef(
		URL gitHubRefURL, GitHubCommit gitHubCommit, JSONObject jsonObject) {

		return new GitHubRef(this, gitHubCommit, gitHubRefURL, jsonObject);
	}

	public GitHubRepository newGitHubRepository(JSONObject jsonObject) {
		return new GitHubRepository(this, jsonObject);
	}

	public GitHubStatus newGitHubStatus(JSONObject jsonObject) {
		return new GitHubStatus(this, jsonObject);
	}

	public GitHubUser newGitHubUser(JSONObject jsonObject) {
		return new GitHubUser(this, jsonObject);
	}

	@Autowired
	private GitHubClient _gitHubClient;

}