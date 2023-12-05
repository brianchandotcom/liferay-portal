/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PortalPullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class OpenPullRequestEventHandler extends BaseGitHubEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (!isGitHubCIEnabledBranchNames()) {
			if (_log.isInfoEnabled()) {
				_log.info("Skipped processing opened pull request");
			}

			return null;
		}

		Set<JobEntity> jobEntities = _createJobEntities();

		for (JobEntity jobEntity : jobEntities) {
			BuildEntityRepository buildEntityRepository = getBuildRepository();

			for (JSONObject initialBuildJSONObject :
					jobEntity.getInitialBuildJSONObjects()) {

				buildEntityRepository.create(jobEntity, initialBuildJSONObject);
			}

			BuildQueue buildQueue = getBuildQueue();

			buildQueue.addJobEntity(jobEntity);

			JenkinsQueue jenkinsQueue = getJenkinsQueue();

			jenkinsQueue.invoke();
		}

		return jobEntities.toString();
	}

	protected OpenPullRequestEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	private Set<JobEntity> _createJobEntities()
		throws InvalidJSONException, IOException {

		Set<JobEntity> jobEntities = new HashSet<>();

		for (String testSuite : _getTestSuites()) {
			GitBranchEntity upstreamGitBranchEntity =
				getUpstreamGitBranchEntity();

			String name = StringUtil.combine(
				upstreamGitBranchEntity.getBranchName(), " - ci:test:",
				testSuite);

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

					GitHubUser originGitHubUser =
						gitHubPullRequest.getOriginGitHubUser();

					portalPullRequestJobEntity.setOriginName(
						originGitHubUser.getName());

					portalPullRequestJobEntity.setSenderBranchName(
						gitHubPullRequest.getHeadBranchName());
					portalPullRequestJobEntity.setSenderBranchSHA(
						gitHubPullRequest.getHeadBranchSHA());

					GitHubUser senderGitHubUser =
						gitHubPullRequest.getSenderGitHubUser();

					portalPullRequestJobEntity.setSenderUserName(
						senderGitHubUser.getName());

					portalPullRequestJobEntity.setUpstreamBranchName(
						gitHubPullRequest.getBaseBranchName());
					portalPullRequestJobEntity.setUpstreamBranchSHA(
						gitHubPullRequest.getBaseBranchSHA());
				}

				if (upstreamGitBranchEntity != null) {
					portalPullRequestJobEntity.setUpstreamBranchName(
						upstreamGitBranchEntity.getBranchName());
					portalPullRequestJobEntity.setUpstreamBranchSHA(
						upstreamGitBranchEntity.getBranchSHA());
				}

				jobEntityRepository.update(portalPullRequestJobEntity);
			}

			jobEntities.add(jobEntity);
		}

		return jobEntities;
	}

	private Set<String> _getTestSuites()
		throws InvalidJSONException, IOException {

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		Set<String> ciTestAutoRecipients = new HashSet<>();

		String upstreamCITestAutoRecipients = getUpstreamBranchCIPropertyValue(
			"ci.test.auto.recipients");

		if (!StringUtil.isNullOrEmpty(upstreamCITestAutoRecipients)) {
			Collections.addAll(
				ciTestAutoRecipients, upstreamCITestAutoRecipients.split(","));
		}

		String senderCITestAutoRecipients = getSenderBranchCIPropertyValue(
			"ci.test.auto.recipients");

		if (!StringUtil.isNullOrEmpty(senderCITestAutoRecipients)) {
			Collections.addAll(
				ciTestAutoRecipients, senderCITestAutoRecipients.split(","));
		}

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		Set<String> testSuites = new HashSet<>();

		for (String ciTestAutoRecipient : ciTestAutoRecipients) {
			Matcher matcher = _ciTestAutoRecipientPattern.matcher(
				ciTestAutoRecipient);

			if (!matcher.find() ||
				!Objects.equals(
					matcher.group("userName"), receiverGitHubUser.getName())) {

				continue;
			}

			String testSuitesString = matcher.group("testSuites");

			Collections.addAll(testSuites, testSuitesString.split(","));
		}

		return testSuites;
	}

	private static final Log _log = LogFactory.getLog(
		OpenPullRequestEventHandler.class);

	private static final Pattern _ciTestAutoRecipientPattern = Pattern.compile(
		"(?<userName>[^\\]]+)\\[(?<testSuites>[^\\]]+)\\]");

}