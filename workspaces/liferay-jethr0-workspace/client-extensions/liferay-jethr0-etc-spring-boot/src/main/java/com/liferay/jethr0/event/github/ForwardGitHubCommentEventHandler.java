/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.job.ForwardPullRequestJobEntity;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PortalPullRequestJobEntity;
import com.liferay.jethr0.job.repository.JobEntityRepository;
import com.liferay.jethr0.util.Jethr0ContextUtil;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ForwardGitHubCommentEventHandler
	extends BaseGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (checkLiferayGitHubUser() || _checkRequiredTestSuites() ||
			closeInvalidUpstreamGitHubBranchName()) {

			return null;
		}

		_commentRequiredTestSuites();

		for (String invocableTestSuite : _getInvocableTestSuites()) {
			invokeJobEntity(
				createPortalPullRequestJobEntity(invocableTestSuite));
		}

		_commentSkippedTestSuites();

		if (!_isForwardEligible()) {
			return null;
		}

		ForwardPullRequestJobEntity forwardPullRequestJobEntity =
			_createForwardPullRequestJobEntity();

		invokeJobEntity(forwardPullRequestJobEntity);

		return String.valueOf(forwardPullRequestJobEntity);
	}

	protected ForwardGitHubCommentEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	@Override
	protected PortalPullRequestJobEntity createPortalPullRequestJobEntity(
			String testSuite)
		throws InvalidJSONException, IOException {

		PortalPullRequestJobEntity portalPullRequestJobEntity =
			super.createPortalPullRequestJobEntity(testSuite);

		if (portalPullRequestJobEntity == null) {
			return null;
		}

		portalPullRequestJobEntity.setForwardReceiverUserName(
			_getForwardReceiverUserName());

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		jobEntityRepository.update(portalPullRequestJobEntity);

		return portalPullRequestJobEntity;
	}

	private boolean _checkRequiredTestSuites()
		throws InvalidJSONException, IOException {

		Set<String> requiredCompletedTestSuites =
			_getRequiredCompletedTestSuites();
		Set<String> requiredPassingTestSuites = _getRequiredPassingTestSuites();

		if (!requiredCompletedTestSuites.isEmpty() ||
			!requiredPassingTestSuites.isEmpty()) {

			return false;
		}

		StringBuilder sb = new StringBuilder();

		sb.append("There are no required test suites specified ");
		sb.append("for `ci:forward");

		if (_isForwardForce()) {
			sb.append(":force");
		}

		sb.append("`.\nNo test will be triggered.\nIf you think this ");
		sb.append("is a mistake please contact the CI Infrastructure ");
		sb.append("team.");

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		gitHubPullRequest.comment(sb.toString());

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"All required test suites have passed or completed for ",
					gitHubPullRequest.getHTMLURL(), " at ",
					StringUtil.toString(new Date())));
		}

		return true;
	}

	private void _commentRequiredTestSuites()
		throws InvalidJSONException, IOException {

		StringBuilder sb = new StringBuilder();

		Set<String> requiredCompletedTestSuites =
			_getRequiredCompletedTestSuites();
		Set<String> requiredPassingTestSuites = _getRequiredPassingTestSuites();

		Set<String> requiredTestSuites = new HashSet<>();

		requiredTestSuites.addAll(requiredCompletedTestSuites);
		requiredTestSuites.addAll(requiredPassingTestSuites);

		sb.append("CI is automatically triggering the following ");
		sb.append("test suites:\n");

		for (String requiredTestSuite : requiredTestSuites) {
			sb.append("- &nbsp;&nbsp;&nbsp;&nbsp;ci:test:**");
			sb.append(requiredTestSuite);
			sb.append("**\n");
		}

		sb.append("\n");
		sb.append("The pull request will automatically be forwarded ");
		sb.append("to the user `");
		sb.append(_getForwardReceiverUserName());
		sb.append("` ");

		if (!requiredCompletedTestSuites.isEmpty()) {
			sb.append("if the following test suites complete:\n");

			for (String requiredCompletedTestSuite :
					requiredCompletedTestSuites) {

				sb.append("- &nbsp;&nbsp;&nbsp;&nbsp;ci:test:**");
				sb.append(requiredCompletedTestSuite);
				sb.append("**\n");
			}

			if (!requiredPassingTestSuites.isEmpty()) {
				sb.append("AND ");
			}
		}

		if (!requiredPassingTestSuites.isEmpty()) {
			sb.append("If the following test suites pass:\n");

			for (String requiredPassingTestSuite : requiredPassingTestSuites) {
				sb.append("- &nbsp;&nbsp;&nbsp;&nbsp;ci:test:**");
				sb.append(requiredPassingTestSuite);
				sb.append("**\n");
			}
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		gitHubPullRequest.comment(sb.toString());
	}

	private void _commentSkippedTestSuites()
		throws InvalidJSONException, IOException {

		Set<String> skippedCompletedTestSuites =
			_getSkippedCompletedTestSuites();
		Set<String> skippedPassingTestSuites = _getSkippedPassingTestSuites();

		if (skippedCompletedTestSuites.isEmpty() &&
			skippedPassingTestSuites.isEmpty()) {

			return;
		}

		StringBuilder sb = new StringBuilder();

		if (!skippedCompletedTestSuites.isEmpty()) {
			sb.append("Skipping previously completed test suites:\n");

			for (String skippedCompletedTestSuite :
					skippedCompletedTestSuites) {

				sb.append("- `ci:test:");
				sb.append(skippedCompletedTestSuite);
				sb.append("`\n");
			}

			if (!skippedPassingTestSuites.isEmpty()) {
				sb.append("AND ");
			}
		}

		if (!skippedPassingTestSuites.isEmpty()) {
			sb.append("Skipping previously passed test suites:\n");

			for (String skippedPassingTestSuite : skippedPassingTestSuites) {
				sb.append("- `ci:test:");
				sb.append(skippedPassingTestSuite);
				sb.append("`\n");
			}
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		gitHubPullRequest.comment(sb.toString());
	}

	private ForwardPullRequestJobEntity _createForwardPullRequestJobEntity()
		throws InvalidJSONException, IOException {

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		String name = "ci:forward - " + gitHubPullRequest.getHTMLURL();

		int priority = 3;
		JobEntity.Type type = JobEntity.Type.FORWARD_PULL_REQUEST;

		JobEntityRepository jobEntityRepository =
			Jethr0ContextUtil.getJobEntityRepository();

		JobEntity jobEntity = jobEntityRepository.create(
			null, name, null, priority, null, JobEntity.State.OPENED, type);

		if (!(jobEntity instanceof ForwardPullRequestJobEntity)) {
			return null;
		}

		ForwardPullRequestJobEntity forwardPullRequestJobEntity =
			(ForwardPullRequestJobEntity)jobEntity;

		forwardPullRequestJobEntity.setPortalPullRequestURL(
			gitHubPullRequest.getHTMLURL());
		forwardPullRequestJobEntity.setForwardForce(_isForwardForce());
		forwardPullRequestJobEntity.setForwardReceiverUserName(
			_getForwardReceiverUserName());

		jobEntityRepository.update(forwardPullRequestJobEntity);

		return forwardPullRequestJobEntity;
	}

	private Set<String> _getCompletedTestSuites() throws InvalidJSONException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		return gitHubPullRequest.getCompletedTestSuites();
	}

	private List<String> _getForwardOptions() throws InvalidJSONException {
		List<String> testOptions = new ArrayList<>();

		GitHubComment gitHubComment = getGitHubComment();

		Matcher matcher = _pattern.matcher(gitHubComment.getBody());

		if (!matcher.find()) {
			return testOptions;
		}

		String testOptionsString = matcher.group("forwardOptions");

		Collections.addAll(testOptions, testOptionsString.split(","));

		return testOptions;
	}

	private String _getForwardReceiverUserName()
		throws InvalidJSONException, IOException {

		for (String forwardOption : _getForwardOptions()) {
			if (!forwardOption.equals("force")) {
				return forwardOption;
			}
		}

		return getCIProperty("ci.forward.default.receiver.username");
	}

	private Set<String> _getInvocableTestSuites()
		throws InvalidJSONException, IOException {

		Set<String> invocableTestSuites = new HashSet<>();

		Set<String> completedTestSuites = _getCompletedTestSuites();

		for (String requiredCompletedTestSuite :
				_getRequiredCompletedTestSuites()) {

			if (!completedTestSuites.contains(requiredCompletedTestSuite)) {
				invocableTestSuites.add(requiredCompletedTestSuite);
			}
		}

		Set<String> passingTestSuites = _getPassingTestSuites();

		for (String requiredPassingTestSuite :
				_getRequiredPassingTestSuites()) {

			if (!passingTestSuites.contains(requiredPassingTestSuite)) {
				invocableTestSuites.add(requiredPassingTestSuite);
			}
		}

		if (invocableTestSuites.contains("relevant") &&
			invocableTestSuites.contains("stable")) {

			invocableTestSuites.remove("stable");
		}

		return invocableTestSuites;
	}

	private Set<String> _getPassingTestSuites() throws InvalidJSONException {
		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		return gitHubPullRequest.getPassingTestSuites();
	}

	private Set<String> _getRequiredCompletedTestSuites()
		throws InvalidJSONException, IOException {

		String propertyName = "ci.forward.required.completed.suites";

		if (_isForwardForce()) {
			propertyName = "ci.forward.force.required.completed.suites";
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		String propertyValue = getJenkinsBranchBuildPropertyValue(
			propertyName, gitHubPullRequest.getBaseRepositoryName(),
			gitHubPullRequest.getBaseBranchName());

		return StringUtil.toSet(propertyValue, ",");
	}

	private Set<String> _getRequiredPassingTestSuites()
		throws InvalidJSONException, IOException {

		String propertyName = "ci.forward.required.passing.suites";

		if (_isForwardForce()) {
			propertyName = "ci.forward.force.required.passing.suites";
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		String propertyValue = getJenkinsBranchBuildPropertyValue(
			propertyName, gitHubPullRequest.getBaseRepositoryName(),
			gitHubPullRequest.getBaseBranchName());

		return StringUtil.toSet(propertyValue, ",");
	}

	private Set<String> _getSkippedCompletedTestSuites()
		throws InvalidJSONException, IOException {

		Set<String> skippedCompletedTestSuites = new HashSet<>();

		Set<String> completedTestSuites = _getCompletedTestSuites();

		for (String requiredCompletedTestSuite :
				_getRequiredCompletedTestSuites()) {

			if (completedTestSuites.contains(requiredCompletedTestSuite)) {
				skippedCompletedTestSuites.add(requiredCompletedTestSuite);
			}
		}

		return skippedCompletedTestSuites;
	}

	private Set<String> _getSkippedPassingTestSuites()
		throws InvalidJSONException, IOException {

		Set<String> skippedPassingTestSuites = new HashSet<>();

		Set<String> passingTestSuites = _getPassingTestSuites();

		for (String requiredPassingTestSuite :
				_getRequiredPassingTestSuites()) {

			if (passingTestSuites.contains(requiredPassingTestSuite)) {
				skippedPassingTestSuites.add(requiredPassingTestSuite);
			}
		}

		return skippedPassingTestSuites;
	}

	private boolean _isForwardEligible()
		throws InvalidJSONException, IOException {

		Set<String> completedTestSuites = _getCompletedTestSuites();

		for (String requiredCompletedTestSuite :
				_getRequiredCompletedTestSuites()) {

			if (!completedTestSuites.contains(requiredCompletedTestSuite)) {
				return false;
			}
		}

		Set<String> passingTestSuites = _getPassingTestSuites();

		for (String requiredPassingTestSuite :
				_getRequiredPassingTestSuites()) {

			if (!passingTestSuites.contains(requiredPassingTestSuite)) {
				return false;
			}
		}

		return true;
	}

	private boolean _isForwardForce() throws InvalidJSONException {
		for (String forwardOption : _getForwardOptions()) {
			if (forwardOption.equals("force")) {
				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactory.getLog(
		ForwardGitHubCommentEventHandler.class);

	private static final Pattern _pattern = Pattern.compile(
		"ci:forward(\\:(?<forwardOptions>[^\\s]+))?");

}