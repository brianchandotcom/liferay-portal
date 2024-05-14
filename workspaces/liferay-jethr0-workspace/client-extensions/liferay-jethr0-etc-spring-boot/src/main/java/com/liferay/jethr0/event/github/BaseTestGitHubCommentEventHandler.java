/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.commit.GitHubCommit;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PullRequestJobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
public abstract class BaseTestGitHubCommentEventHandler
	extends BaseGitHubCommentEventHandler {

	@Override
	public String process() throws InvalidJSONException, IOException {
		if (checkLiferayGitHubUser() ||
			closeInvalidUpstreamGitHubBranchName() ||
			_checkInvalidForwardTestSuiteName()) {

			return null;
		}

		PullRequestJobEntity pullRequestJobEntity = getPullRequestJobEntity();

		invokeJobEntity(pullRequestJobEntity);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringUtil.combine(
					"Invoked a job ", pullRequestJobEntity.getEntityURL(),
					" at ", StringUtil.toString(new Date())));
		}

		return pullRequestJobEntity.toString();
	}

	protected BaseTestGitHubCommentEventHandler(JSONObject messageJSONObject) {
		super(messageJSONObject);
	}

	protected PullRequestJobEntity createPullRequestJobEntity(String testSuite)
		throws InvalidJSONException {

		return createPullRequestJobEntity(
			getJobEntityType(), getJobPriority(), testSuite);
	}

	protected abstract JobEntity.Type getJobEntityType();

	protected abstract int getJobPriority();

	protected PullRequestJobEntity getPullRequestJobEntity()
		throws InvalidJSONException, IOException {

		if (_pullRequestJobEntity != null) {
			return _pullRequestJobEntity;
		}

		_pullRequestJobEntity = createPullRequestJobEntity(getTestSuite());

		String rebaseBranchSHA = getRebaseBranchSHA();

		if (!StringUtil.isNullOrEmpty(rebaseBranchSHA)) {
			_pullRequestJobEntity.setUpstreamBranchSHA(rebaseBranchSHA);
		}

		return _pullRequestJobEntity;
	}

	protected String getRebaseBranchSHA()
		throws InvalidJSONException, IOException {

		for (String testOption : getTestOptions()) {
			if (StringUtil.isNullOrEmpty(testOption)) {
				continue;
			}

			Matcher gitHubCommitIDMatcher = _gitHubCommitIDPattern.matcher(
				testOption);

			if (gitHubCommitIDMatcher.matches()) {
				return gitHubCommitIDMatcher.group();
			}
		}

		if (_testNoRebase()) {
			GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

			GitHubCommit commonParentGitHubCommit =
				gitHubPullRequest.getCommonParentGitHubCommit();

			return commonParentGitHubCommit.getSHA();
		}

		return null;
	}

	protected List<String> getTestOptions() throws InvalidJSONException {
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

	protected String getTestSuite() throws InvalidJSONException, IOException {
		Set<String> availableTestSuites = getAvailableTestSuites();

		for (String testOption : getTestOptions()) {
			if (availableTestSuites.contains(testOption)) {
				return testOption;
			}
		}

		return "default";
	}

	private boolean _checkInvalidForwardTestSuiteName()
		throws InvalidJSONException, IOException {

		String testSuite = getTestSuite();

		if (!testSuite.equals("forward")) {
			return false;
		}

		GitHubPullRequest gitHubPullRequest = getGitHubPullRequest();

		String message = StringUtil.combine(
			"The test will not be initiated because `ci:test:forward` is not ",
			"a valid command. Use `ci:forward` instead.");

		gitHubPullRequest.comment(message);

		if (_log.isInfoEnabled()) {
			_log.info(message);
		}

		return true;
	}

	private boolean _testNoRebase() throws InvalidJSONException {
		for (String testOption : getTestOptions()) {
			if (testOption.equals("norebase")) {
				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactory.getLog(
		BaseTestGitHubCommentEventHandler.class);

	private static final Pattern _gitHubCommitIDPattern = Pattern.compile(
		"[a-f0-9]{7,40}");
	private static final Pattern _pattern = Pattern.compile(
		"ci:test(\\:(?<testOptions>[^\\s]+))?");

	private PullRequestJobEntity _pullRequestJobEntity;

}