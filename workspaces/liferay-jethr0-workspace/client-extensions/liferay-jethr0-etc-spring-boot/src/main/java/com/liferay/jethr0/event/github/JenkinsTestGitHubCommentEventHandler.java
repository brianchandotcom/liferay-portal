/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.JenkinsPullRequestJobEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import java.io.IOException;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsTestGitHubCommentEventHandler
	extends BaseTestGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (checkLiferayGitHubUser() ||
			closeInvalidUpstreamGitHubBranchName()) {

			return null;
		}

		JenkinsPullRequestJobEntity jenkinsPullRequestJobEntity =
			_createJenkinsPullRequestJobEntity();

		invokeJobEntity(jenkinsPullRequestJobEntity);

		return jenkinsPullRequestJobEntity.toString();
	}

	protected JenkinsTestGitHubCommentEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private JenkinsPullRequestJobEntity _createJenkinsPullRequestJobEntity()
		throws InvalidJSONException {

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		JobEntityRepository jobEntityRepository = getJobEntityRepository();

		JobEntity jobEntity = jobEntityRepository.create(
			upstreamGitBranchEntity.getBranchName() + " - ci:test", 5, null,
			JobEntity.State.OPENED, JobEntity.Type.JENKINS_PULL_REQUEST);

		if (!(jobEntity instanceof JenkinsPullRequestJobEntity)) {
			return null;
		}

		JenkinsPullRequestJobEntity jenkinsPullRequestJobEntity =
			(JenkinsPullRequestJobEntity)jobEntity;

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest != null) {
			jenkinsPullRequestJobEntity.setJenkinsPullRequestURL(
				gitHubPullRequest.getHTMLURL());

			GitHubUser originGitHubUser =
				gitHubPullRequest.getOriginGitHubUser();

			jenkinsPullRequestJobEntity.setOriginName(
				originGitHubUser.getName());

			jenkinsPullRequestJobEntity.setSenderBranchName(
				gitHubPullRequest.getHeadBranchName());
			jenkinsPullRequestJobEntity.setSenderBranchSHA(
				gitHubPullRequest.getHeadBranchSHA());

			GitHubUser senderGitHubUser =
				gitHubPullRequest.getSenderGitHubUser();

			jenkinsPullRequestJobEntity.setSenderUserName(
				senderGitHubUser.getName());

			jenkinsPullRequestJobEntity.setUpstreamBranchName(
				gitHubPullRequest.getBaseBranchName());
			jenkinsPullRequestJobEntity.setUpstreamBranchSHA(
				gitHubPullRequest.getBaseBranchSHA());
		}

		if (upstreamGitBranchEntity != null) {
			jenkinsPullRequestJobEntity.setUpstreamBranchName(
				upstreamGitBranchEntity.getBranchName());
			jenkinsPullRequestJobEntity.setUpstreamBranchSHA(
				upstreamGitBranchEntity.getBranchSHA());
		}

		jobEntityRepository.update(jenkinsPullRequestJobEntity);

		return jenkinsPullRequestJobEntity;
	}

}