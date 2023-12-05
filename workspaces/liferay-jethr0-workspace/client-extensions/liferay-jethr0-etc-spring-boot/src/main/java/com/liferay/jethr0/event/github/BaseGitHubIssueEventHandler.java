/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.bui1d.queue.BuildQueue;
import com.liferay.jethr0.bui1d.repository.BuildEntityRepository;
import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.issue.GitHubIssue;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.branch.repository.GitBranchEntityRepository;
import com.liferay.jethr0.jenkins.JenkinsQueue;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PortalPullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.PropertiesUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BaseGitHubIssueEventHandler
	extends BaseGitHubEventHandler {

	protected BaseGitHubIssueEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	protected void closeGitHubPullRequest(String body)
		throws InvalidJSONException {

		GitHubClient gitHubClient = getGitHubClient();

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		if ((gitHubClient == null) || (upstreamGitBranchEntity == null)) {
			return;
		}

		GitHubIssue gitHubIssue = getGitHubIssue();

		if (gitHubIssue != null) {
			gitHubClient.createGitHubComment(gitHubIssue, body);

			gitHubClient.closeGitHubIssue(gitHubIssue);

			return;
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest != null) {
			gitHubClient.createGitHubComment(gitHubPullRequest, body);

			gitHubClient.closeGitHubPullRequest(gitHubPullRequest);
		}
	}

	protected boolean closeInvalidUpstreamGitHubBranchName()
		throws InvalidJSONException, IOException {

		if (!_isGitHubCIEnabledBranchNames()) {
			return false;
		}

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		String body = StringUtil.combine(
			"Closing pull request because pulls for reference ",
			upstreamGitBranchEntity.getBranchName(),
			" should not be sent to repository ",
			upstreamGitBranchEntity.getRepositoryName());

		closeGitHubPullRequest(body);

		if (_log.isInfoEnabled()) {
			_log.info(body);
		}

		return true;
	}

	protected JobEntity createJobEntity(String testSuite)
		throws InvalidJSONException {

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		String name = StringUtil.combine(
			upstreamGitBranchEntity.getBranchName(), " - ci:test:", testSuite);

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

		return jobEntity;
	}

	protected Set<String> getAvailableTestSuites()
		throws InvalidJSONException, IOException {

		Set<String> availableTestSuites = new HashSet<>();

		String upstreamAvailableTestSuites = getUpstreamBranchCIPropertyValue(
			"ci.test.available.suites");

		if (!StringUtil.isNullOrEmpty(upstreamAvailableTestSuites)) {
			Collections.addAll(
				availableTestSuites, upstreamAvailableTestSuites.split(","));
		}

		String senderAvailableTestSuites = getSenderBranchCIPropertyValue(
			"ci.test.available.suites");

		if (!StringUtil.isNullOrEmpty(senderAvailableTestSuites)) {
			Collections.addAll(
				availableTestSuites, senderAvailableTestSuites.split(","));
		}

		return availableTestSuites;
	}

	protected String getCIProperty(String ciPropertyName)
		throws InvalidJSONException, IOException {

		String upstreamBranchCIPropertyValue = getUpstreamBranchCIPropertyValue(
			ciPropertyName);

		if (!StringUtil.isNullOrEmpty(upstreamBranchCIPropertyValue)) {
			return upstreamBranchCIPropertyValue;
		}

		String senderBranchCIPropertyValue = getSenderBranchCIPropertyValue(
			ciPropertyName);

		if (!StringUtil.isNullOrEmpty(senderBranchCIPropertyValue)) {
			return senderBranchCIPropertyValue;
		}

		return null;
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

	protected String getSenderBranchCIPropertyValue(String propertyName)
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

	protected String getUpstreamBranchCIPropertyValue(String propertyName)
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

	protected void invokeJobEntity(JobEntity jobEntity) {
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

	private boolean _isGitHubCIEnabledBranchNames()
		throws InvalidJSONException, IOException {

		GitHubRepository gitHubRepository = getGitHubRepository();

		String gitHubCIEnabledBranchNames = getJenkinsBranchBuildPropertyValue(
			StringUtil.combine(
				"github.ci.enabled.branch.names[", gitHubRepository.getName(),
				"]"));

		if (StringUtil.isNullOrEmpty(gitHubCIEnabledBranchNames)) {
			return false;
		}

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		if (upstreamGitBranchEntity == null) {
			return false;
		}

		for (String gitHubCIEnabledBranchName :
				gitHubCIEnabledBranchNames.split(",")) {

			if (Objects.equals(
					gitHubCIEnabledBranchName,
					upstreamGitBranchEntity.getBranchName())) {

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactory.getLog(
		BaseGitHubIssueEventHandler.class);

	private GitHubPullRequest _gitHubPullRequest;
	private GitBranchEntity _senderGitBranchEntity;
	private GitBranchEntity _upstreamGitBranchEntity;

}