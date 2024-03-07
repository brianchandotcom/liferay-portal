/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.event.github;

import com.liferay.jethr0.event.EventHandlerContext;
import com.liferay.jethr0.event.github.comment.GitHubComment;
import com.liferay.jethr0.event.github.pullrequest.GitHubPullRequest;
import com.liferay.jethr0.job.JobEntity;
import com.liferay.jethr0.job.PullRequestJobEntity;
import com.liferay.jethr0.util.StringUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
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

		PullRequestJobEntity pullRequestJobEntity = createPullRequestJobEntity(
			getTestSuite());

		invokeJobEntity(pullRequestJobEntity);

		return pullRequestJobEntity.toString();
	}

	protected BaseTestGitHubCommentEventHandler(
		EventHandlerContext eventHandlerContext, JSONObject messageJSONObject) {

		super(eventHandlerContext, messageJSONObject);
	}

	protected PullRequestJobEntity createPullRequestJobEntity(String testSuite)
		throws InvalidJSONException {

		return createPullRequestJobEntity(
			getJobEntityType(), getJobPriority(), testSuite);
	}

	protected abstract JobEntity.Type getJobEntityType();

	protected abstract int getJobPriority();

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
			"a valid command.  Please use `ci:forward` instead.");

		gitHubPullRequest.comment(message);

		if (_log.isInfoEnabled()) {
			_log.info(message);
		}

		return true;
	}

	private static final Log _log = LogFactory.getLog(
		BaseTestGitHubCommentEventHandler.class);

	private static final Pattern _pattern = Pattern.compile(
		"ci:test(\\:(?<testOptions>[^\\s]+))?");

}