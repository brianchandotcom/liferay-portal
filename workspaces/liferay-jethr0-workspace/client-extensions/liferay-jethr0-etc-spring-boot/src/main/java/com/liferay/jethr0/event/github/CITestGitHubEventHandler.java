/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PortalPullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class CITestGitHubEventHandler extends BaseGitHubEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		JobEntity jobEntity = _getJobEntity();

		BuildEntityRepository buildEntityRepository = getBuildRepository();

		for (JSONObject initialBuildJSONObject :
				jobEntity.getInitialBuildJSONObjects()) {

			buildEntityRepository.create(jobEntity, initialBuildJSONObject);
		}

		BuildQueue buildQueue = getBuildQueue();

		buildQueue.addJobEntity(jobEntity);

		JenkinsQueue jenkinsQueue = getJenkinsQueue();

		jenkinsQueue.invoke();

		return jobEntity.toString();
	}

	protected CITestGitHubEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private JobEntity _getJobEntity() throws InvalidJSONException, IOException {
		String testSuite = _getTestSuite();

		String name = "Portal Pull Request - ci:test:" + testSuite;

		int priority = 5;
		JobEntity.Type type = JobEntity.Type.PORTAL_PULL_REQUEST;

		if (testSuite.equals("sf")) {
			priority = 4;
			type = JobEntity.Type.PORTAL_PULL_REQUEST_SF;
		}

		JobEntityRepository jobEntityRepository = getJobEntityRepository();

		JobEntity jobEntity = jobEntityRepository.create(
			name, priority, null, JobEntity.State.OPENED, type);

		if (jobEntity instanceof PortalPullRequestJobEntity) {
			PortalPullRequestJobEntity portalPullRequestJobEntity =
				(PortalPullRequestJobEntity)jobEntity;

			portalPullRequestJobEntity.setTestSuiteName(testSuite);

			GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

			if (gitHubPullRequest != null) {
				portalPullRequestJobEntity.setPortalPullRequestURL(
					gitHubPullRequest.getHTMLURL());

				portalPullRequestJobEntity.setOriginName(
					gitHubPullRequest.getHeadUserName());

				portalPullRequestJobEntity.setSenderBranchName(
					gitHubPullRequest.getHeadBranchName());
				portalPullRequestJobEntity.setSenderBranchSHA(
					gitHubPullRequest.getHeadBranchSHA());
				portalPullRequestJobEntity.setSenderUserName(
					gitHubPullRequest.getSenderUserName());

				portalPullRequestJobEntity.setUpstreamBranchName(
					gitHubPullRequest.getBaseBranchName());
				portalPullRequestJobEntity.setUpstreamBranchSHA(
					gitHubPullRequest.getBaseBranchSHA());
			}

			GitBranchEntity upstreamGitBranchEntity =
				getUpstreamGitBranchEntity();

			if (upstreamGitBranchEntity != null) {
				portalPullRequestJobEntity.setUpstreamBranchName(
					upstreamGitBranchEntity.getBranchName());
				portalPullRequestJobEntity.setUpstreamBranchSHA(
					upstreamGitBranchEntity.getBranchSHA());
			}

			jobEntityRepository.update(jobEntity);
		}

		return jobEntity;
	}

	private List<String> _getTestOptions() throws InvalidJSONException {
		List<String> testOptions = new ArrayList<>();

		GitHubComment gitHubComment = getGitHubComment();

		Matcher matcher = _pattern.matcher(gitHubComment.getBody());

		if (!matcher.find()) {
			return testOptions;
		}

		String testOptionsString = matcher.group("testOptions");

		Collections.addAll(testOptions, testOptionsString.split(","));

		return testOptions;
	}

	private String _getTestSuite() throws InvalidJSONException, IOException {
		Set<String> availableTestSuites = getAvailableTestSuites();

		for (String testOption : _getTestOptions()) {
			if (availableTestSuites.contains(testOption)) {
				return testOption;
			}
		}

		return "default";
	}

	private static final Pattern _pattern = Pattern.compile(
		"ci:test(?<testOptions>\\:[^\\s]+)?");

}