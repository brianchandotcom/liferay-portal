/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.client.GitHubClient;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.issue.GitHubIssue;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.event.github.repository.GitHubRepository;
import com.liferay.jethr0.event.github.user.GitHubUser;
import com.liferay.jethr0.git.branch.GitBranchEntity;
import com.liferay.jethr0.git.repository.GitBranchEntityRepository;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PortalPullRequestJobEntity;
import com.liferay.jethr0.job.PullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.PropertiesUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.Collections;
import java.util.Date;
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

	public boolean isReceiverLiferayGitHubUser() throws InvalidJSONException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest == null) {
			return false;
		}

		GitHubUser receiverGitHubUser =
			gitHubPullRequest.getReceiverGitHubUser();

		if (!receiverGitHubUser.isLiferayUser()) {
			return false;
		}

		return true;
	}

	public boolean isSenderLiferayGitHubUser() throws InvalidJSONException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest == null) {
			return false;
		}

		GitHubUser senderGitHubUser = gitHubPullRequest.getSenderGitHubUser();

		if (!senderGitHubUser.isLiferayUser()) {
			return false;
		}

		return true;
	}

	protected BaseGitHubIssueEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	protected boolean checkLiferayGitHubUser() throws InvalidJSONException {
		if (involvesLiferayGitHubUsersOnly()) {
			return false;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("You cannot perform that action because you are not a ");
		sb.append("member of the Liferay organization. Please make sure that ");
		sb.append("you have been added and that your organization membership ");
		sb.append("is set as public. See https://help.github.com/articles");
		sb.append("/publicizing-or-hiding-organization-membership for more ");
		sb.append("information.");

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		GitHubComment gitHubComment = gitHubPullRequest.comment(sb.toString());

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Ignore non-Liferay users for ", gitHubComment.getHTMLURL(),
					" at ", StringUtil.toString(new Date())));
		}

		return true;
	}

	protected void closeGitHubPullRequest(String body)
		throws InvalidJSONException {

		GitHubClient gitHubClient = Jethr0ContextUtil.getGitHubClient();

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		if ((gitHubClient == null) || (upstreamGitBranchEntity == null)) {
			return;
		}

		GitHubIssue gitHubIssue = getGitHubIssue();

		if (gitHubIssue != null) {
			gitHubIssue.createGitHubComment(body);

			gitHubIssue.close();

			return;
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest != null) {
			gitHubPullRequest.comment(body);

			gitHubPullRequest.close();
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
			upstreamGitBranchEntity.getName(),
			" should not be sent to repository ",
			upstreamGitBranchEntity.getRepositoryName(), ".");

		closeGitHubPullRequest(body);

		if (_log.isInfoEnabled()) {
			_log.info(body);
		}

		return true;
	}

	protected PortalPullRequestJobEntity createPortalPullRequestJobEntity(
			String testSuite)
		throws InvalidJSONException, IOException {

		int jobPriority = 5;
		JobEntity.Type jobEntityType = JobEntity.Type.PORTAL_PULL_REQUEST;

		if (testSuite.equals("sf")) {
			jobPriority = 4;
			jobEntityType = JobEntity.Type.PORTAL_PULL_REQUEST_SF;
		}

		JobEntity jobEntity = createPullRequestJobEntity(
			jobEntityType, jobPriority, testSuite);

		if (!(jobEntity instanceof PortalPullRequestJobEntity)) {
			return null;
		}

		return (PortalPullRequestJobEntity)jobEntity;
	}

	protected PullRequestJobEntity createPullRequestJobEntity(
			JobEntity.Type jobEntityType, int jobPriority, String testSuite)
		throws InvalidJSONException {

		GitBranchEntity upstreamGitBranchEntity = getUpstreamGitBranchEntity();

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		GitHubIssue gitHubIssue = getGitHubIssue();

		JobEntity jobEntity = jobEntityRepository.create(
			null,
			StringUtil.combine(
				"[", upstreamGitBranchEntity.getName(), "] - ci:test:",
				testSuite, " ", gitHubIssue.getRepositoryName(), "/",
				gitHubIssue.getReceiverUserName(), "#",
				gitHubIssue.getNumber()),
			null, jobPriority, null, JobEntity.State.OPENED, jobEntityType);

		if (!(jobEntity instanceof PullRequestJobEntity)) {
			return null;
		}

		PullRequestJobEntity pullRequestJobEntity =
			(PullRequestJobEntity)jobEntity;

		pullRequestJobEntity.setTestSuiteName(testSuite);

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		if (gitHubPullRequest != null) {
			pullRequestJobEntity.setPullRequestURL(
				gitHubPullRequest.getHTMLURL());

			GitHubUser originGitHubUser =
				gitHubPullRequest.getOriginGitHubUser();

			pullRequestJobEntity.setOriginName(originGitHubUser.getName());

			pullRequestJobEntity.setSenderBranchName(
				gitHubPullRequest.getHeadBranchName());
			pullRequestJobEntity.setSenderBranchSHA(
				gitHubPullRequest.getHeadBranchSHA());

			GitHubUser senderGitHubUser =
				gitHubPullRequest.getSenderGitHubUser();

			pullRequestJobEntity.setSenderUserName(senderGitHubUser.getName());

			pullRequestJobEntity.setUpstreamBranchName(
				gitHubPullRequest.getBaseBranchName());
			pullRequestJobEntity.setUpstreamBranchSHA(
				gitHubPullRequest.getBaseBranchSHA());
		}

		if (upstreamGitBranchEntity != null) {
			pullRequestJobEntity.setUpstreamBranchName(
				upstreamGitBranchEntity.getName());
			pullRequestJobEntity.setUpstreamBranchSHA(
				upstreamGitBranchEntity.getLatestSHA());
		}

		jobEntityRepository.update(pullRequestJobEntity);

		return pullRequestJobEntity;
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

	protected GitHubIssue getGitHubIssue() throws InvalidJSONException {
		JSONObject messageJSONObject = getMessageJSONObject();

		JSONObject issueJSONObject = messageJSONObject.optJSONObject("issue");

		if (issueJSONObject == null) {
			throw new InvalidJSONException(
				"Missing \"issue\" from message JSON");
		}

		GitHubFactory gitHubFactory = Jethr0ContextUtil.getGitHubFactory();

		return gitHubFactory.newGitHubIssue(issueJSONObject);
	}

	protected GitHubPullRequest getGitHubPullRequest()
		throws InvalidJSONException {

		if (_gitHubPullRequest != null) {
			return _gitHubPullRequest;
		}

		GitHubIssue gitHubIssue = getGitHubIssue();

		_gitHubPullRequest = gitHubIssue.getGitHubPullRequest();

		return _gitHubPullRequest;
	}

	protected String getSenderBranchCIPropertyValue(
			String propertyName, String... propertyOpts)
		throws InvalidJSONException, IOException {

		GitBranchEntity gitBranchEntity = getSenderGitBranchEntity();

		if (gitBranchEntity == null) {
			return null;
		}

		Properties properties = gitBranchEntity.getProperties("ci.properties");

		if (properties == null) {
			return null;
		}

		return PropertiesUtil.getPropertyValue(
			properties, propertyName, propertyOpts);
	}

	protected GitBranchEntity getSenderGitBranchEntity()
		throws InvalidJSONException {

		if (_senderGitBranchEntity != null) {
			return _senderGitBranchEntity;
		}

		GitBranchEntityRepository gitBranchEntityRepository =
			Jethr0ContextUtil.getGitBranchEntityRepository();

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		_senderGitBranchEntity = gitBranchEntityRepository.getByURL(
			gitHubPullRequest.getHeadBranchURL());

		return _senderGitBranchEntity;
	}

	protected String getUpstreamBranchCIPropertyValue(
			String propertyName, String... propertyOpts)
		throws InvalidJSONException, IOException {

		GitBranchEntity gitBranchEntity = getUpstreamGitBranchEntity();

		if (gitBranchEntity == null) {
			return null;
		}

		Properties properties = gitBranchEntity.getProperties("ci.properties");

		if (properties == null) {
			return null;
		}

		return PropertiesUtil.getPropertyValue(
			properties, propertyName, propertyOpts);
	}

	protected GitBranchEntity getUpstreamGitBranchEntity()
		throws InvalidJSONException {

		if (_upstreamGitBranchEntity != null) {
			return _upstreamGitBranchEntity;
		}

		GitBranchEntityRepository gitBranchEntityRepository =
			Jethr0ContextUtil.getGitBranchEntityRepository();

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		_upstreamGitBranchEntity = gitBranchEntityRepository.getByURL(
			gitHubPullRequest.getUpstreamBranchURL());

		return _upstreamGitBranchEntity;
	}

	protected boolean involvesLiferayGitHubUsersOnly()
		throws InvalidJSONException {

		if (isReceiverLiferayGitHubUser() && isSenderLiferayGitHubUser()) {
			return true;
		}

		return false;
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
					upstreamGitBranchEntity.getName())) {

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