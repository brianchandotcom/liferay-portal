/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.util.StringUtil;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class DefaultPortalPullRequestJobEntity
	extends BasePortalPullRequestJobEntity {

	protected DefaultPortalPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("CI_TEST_SUITE", getTestSuiteName());
		initialBuildParameters.put("GITHUB_ORIGIN_NAME", getOriginName());
		initialBuildParameters.put(
			"GITHUB_PULL_REQUEST_NUMBER",
			String.valueOf(_getPullRequestNumber()));
		initialBuildParameters.put(
			"GITHUB_RECEIVER_USERNAME", _getPullRequestReceiverUserName());
		initialBuildParameters.put(
			"GITHUB_REPOSITORY_NAME", _getPullRequestRepositoryName());
		initialBuildParameters.put(
			"GITHUB_SENDER_BRANCH_NAME", getSenderBranchName());
		initialBuildParameters.put(
			"GITHUB_SENDER_BRANCH_SHA", getSenderBranchSHA());
		initialBuildParameters.put(
			"GITHUB_SENDER_USERNAME", getSenderUserName());
		initialBuildParameters.put(
			"GITHUB_UPSTREAM_BRANCH_NAME", getUpstreamBranchName());
		initialBuildParameters.put(
			"GITHUB_UPSTREAM_BRANCH_SHA", getUpstreamBranchSHA());

		initialBuildParameters.put("TEST_PORTAL_BUILD_PROFILE", "dxp");

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return StringUtil.combine(
			"test-portal-acceptance-pullrequest(", getUpstreamBranchName(),
			")");
	}

	private long _getPullRequestNumber() {
		if (_pullRequestNumber > 0) {
			return _pullRequestNumber;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPortalPullRequestURL()));

		if (matcher.find()) {
			_pullRequestNumber = Long.valueOf(matcher.group("number"));

			return _pullRequestNumber;
		}

		return -1;
	}

	private String _getPullRequestReceiverUserName() {
		if (!StringUtil.isNullOrEmpty(_receiverUserName)) {
			return _receiverUserName;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPortalPullRequestURL()));

		if (matcher.find()) {
			_receiverUserName = matcher.group("receiverUserName");

			return _receiverUserName;
		}

		return null;
	}

	private String _getPullRequestRepositoryName() {
		if (!StringUtil.isNullOrEmpty(_repositoryName)) {
			return _repositoryName;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPortalPullRequestURL()));

		if (matcher.find()) {
			_repositoryName = matcher.group("repositoryName");

			return _repositoryName;
		}

		return null;
	}

	private static final Pattern _pullRequestURLPattern = Pattern.compile(
		StringUtil.combine(
			"https://github.com/(?<receiverUserName>[^/]+)/",
			"(?<repositoryName>liferay-portal(-ee)?)",
			"/pull/(?<number>\\d+)"));

	private long _pullRequestNumber;
	private String _receiverUserName;
	private String _repositoryName;

}