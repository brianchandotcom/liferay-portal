/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.FixpackBuilderPullRequestJobEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import java.io.IOException;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class FixpackTestGitHubCommentEventHandler
	extends BaseTestGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (checkLiferayGitHubUser() ||
			closeInvalidUpstreamGitHubBranchName()) {

			return null;
		}

		FixpackBuilderPullRequestJobEntity fixpackBuilderPullRequestJobEntity =
			_createFixpackBuilderPullRequestJobEntity();

		invokeJobEntity(fixpackBuilderPullRequestJobEntity);

		return fixpackBuilderPullRequestJobEntity.toString();
	}

	protected FixpackTestGitHubCommentEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private FixpackBuilderPullRequestJobEntity
			_createFixpackBuilderPullRequestJobEntity()
		throws InvalidJSONException {

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		JobEntityRepository jobEntityRepository = getJobEntityRepository();

		JobEntity jobEntity = jobEntityRepository.create(
			upstreamGitBranchEntity.getBranchName() + " - ci:test", 5, null,
			JobEntity.State.OPENED,
			JobEntity.Type.FIXPACK_BUILDER_PULL_REQUEST);

		if (!(jobEntity instanceof FixpackBuilderPullRequestJobEntity)) {
			return null;
		}

		FixpackBuilderPullRequestJobEntity fixpackBuilderPullRequestJobEntity =
			(FixpackBuilderPullRequestJobEntity)jobEntity;

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest != null) {
			fixpackBuilderPullRequestJobEntity.setPullRequestURL(
				gitHubPullRequest.getHTMLURL());

			GitHubUser originGitHubUser =
				gitHubPullRequest.getOriginGitHubUser();

			fixpackBuilderPullRequestJobEntity.setOriginName(
				originGitHubUser.getName());

			fixpackBuilderPullRequestJobEntity.setSenderBranchName(
				gitHubPullRequest.getHeadBranchName());
			fixpackBuilderPullRequestJobEntity.setSenderBranchSHA(
				gitHubPullRequest.getHeadBranchSHA());

			GitHubUser senderGitHubUser =
				gitHubPullRequest.getSenderGitHubUser();

			fixpackBuilderPullRequestJobEntity.setSenderUserName(
				senderGitHubUser.getName());

			fixpackBuilderPullRequestJobEntity.setUpstreamBranchName(
				gitHubPullRequest.getBaseBranchName());
			fixpackBuilderPullRequestJobEntity.setUpstreamBranchSHA(
				gitHubPullRequest.getBaseBranchSHA());
		}

		if (upstreamGitBranchEntity != null) {
			fixpackBuilderPullRequestJobEntity.setUpstreamBranchName(
				upstreamGitBranchEntity.getBranchName());
			fixpackBuilderPullRequestJobEntity.setUpstreamBranchSHA(
				upstreamGitBranchEntity.getBranchSHA());
		}

		jobEntityRepository.update(fixpackBuilderPullRequestJobEntity);

		return fixpackBuilderPullRequestJobEntity;
	}

}