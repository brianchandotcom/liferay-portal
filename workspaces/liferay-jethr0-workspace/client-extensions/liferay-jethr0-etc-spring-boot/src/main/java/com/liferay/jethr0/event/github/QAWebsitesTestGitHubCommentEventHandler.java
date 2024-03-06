/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.QAWebsitesPullRequestSFJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class QAWebsitesTestGitHubCommentEventHandler
	extends BaseTestGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (checkLiferayGitHubUser() ||
			closeInvalidUpstreamGitHubBranchName()) {

			return null;
		}

		QAWebsitesPullRequestSFJobEntity qaWebsitesPullRequestSFJobEntity =
			_createQAWebsitesPullRequestSFJobEntity(getTestSuite());

		invokeJobEntity(qaWebsitesPullRequestSFJobEntity);

		return qaWebsitesPullRequestSFJobEntity.toString();
	}

	protected QAWebsitesTestGitHubCommentEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private QAWebsitesPullRequestSFJobEntity
			_createQAWebsitesPullRequestSFJobEntity(String testSuite)
		throws InvalidJSONException {

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		JobEntityRepository jobEntityRepository = getJobEntityRepository();

		JobEntity jobEntity = jobEntityRepository.create(
			StringUtil.combine(
				upstreamGitBranchEntity.getBranchName(), " - ci:test:",
				testSuite),
			5, null, JobEntity.State.OPENED,
			JobEntity.Type.QA_WEBSITES_PULL_REQUEST_SF);

		if (!(jobEntity instanceof QAWebsitesPullRequestSFJobEntity)) {
			return null;
		}

		QAWebsitesPullRequestSFJobEntity qaWebsitesPullRequestSFJobEntity =
			(QAWebsitesPullRequestSFJobEntity)jobEntity;

		qaWebsitesPullRequestSFJobEntity.setTestSuiteName(testSuite);

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest != null) {
			qaWebsitesPullRequestSFJobEntity.setQAWebsitesPullRequestURL(
				gitHubPullRequest.getHTMLURL());

			GitHubUser originGitHubUser =
				gitHubPullRequest.getOriginGitHubUser();

			qaWebsitesPullRequestSFJobEntity.setOriginName(
				originGitHubUser.getName());

			qaWebsitesPullRequestSFJobEntity.setSenderBranchName(
				gitHubPullRequest.getHeadBranchName());
			qaWebsitesPullRequestSFJobEntity.setSenderBranchSHA(
				gitHubPullRequest.getHeadBranchSHA());

			GitHubUser senderGitHubUser =
				gitHubPullRequest.getSenderGitHubUser();

			qaWebsitesPullRequestSFJobEntity.setSenderUserName(
				senderGitHubUser.getName());

			qaWebsitesPullRequestSFJobEntity.setUpstreamBranchName(
				gitHubPullRequest.getBaseBranchName());
			qaWebsitesPullRequestSFJobEntity.setUpstreamBranchSHA(
				gitHubPullRequest.getBaseBranchSHA());
		}

		if (upstreamGitBranchEntity != null) {
			qaWebsitesPullRequestSFJobEntity.setUpstreamBranchName(
				upstreamGitBranchEntity.getBranchName());
			qaWebsitesPullRequestSFJobEntity.setUpstreamBranchSHA(
				upstreamGitBranchEntity.getBranchSHA());
		}

		jobEntityRepository.update(qaWebsitesPullRequestSFJobEntity);

		return qaWebsitesPullRequestSFJobEntity;
	}

}