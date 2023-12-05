/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.bui1d.BuildEntity;
import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class DefaultPortalPullRequestJobEntity
	extends BasePortalPullRequestJobEntity {

	public static List<ParameterDefinition> getParameterDefinitions() {
		return Arrays.asList(
			JENKINS_GITHUB_URL_PARAMETER_DEFINITION,
			PORTAL_PULL_REQUEST_PARAMETER_DEFINITION,
			TEST_SUITE_NAME_PARAMETER_DEFINITION);
	}

	@Override
	public URL getPortalPullRequestURL() {
		if (_portalPullRequestURL != null) {
			return _portalPullRequestURL;
		}

		_portalPullRequestURL = super.getPortalPullRequestURL();

		if (_portalPullRequestURL != null) {
			return _portalPullRequestURL;
		}

		int pullRequestNumber = 0;
		String receiverUserName = null;
		String repositoryName = null;

		for (BuildEntity initialBuildEntity : getInitialBuildEntities()) {
			String pullRequestNumberParameterValue =
				initialBuildEntity.getBuildParameterValue(
					"GITHUB_PULL_REQUEST_NUMBER");

			if (!StringUtil.isNullOrEmpty(pullRequestNumberParameterValue)) {
				pullRequestNumber = Integer.valueOf(
					pullRequestNumberParameterValue);
			}

			String receiverUserNameParameterValue =
				initialBuildEntity.getBuildParameterValue(
					"GITHUB_RECEIVER_USERNAME");

			if (!StringUtil.isNullOrEmpty(receiverUserNameParameterValue)) {
				receiverUserName = receiverUserNameParameterValue;
			}

			String repositoryNameParameterValue =
				initialBuildEntity.getBuildParameterValue(
					"GITHUB_REPOSITORY_NAME");

			if (!StringUtil.isNullOrEmpty(repositoryNameParameterValue)) {
				repositoryName = repositoryNameParameterValue;
			}
		}

		if ((pullRequestNumber > 0) &&
			!StringUtil.isNullOrEmpty(receiverUserName) &&
			!StringUtil.isNullOrEmpty(repositoryName)) {

			_portalPullRequestURL = StringUtil.toURL(
				StringUtil.combine(
					"https://github.com/", receiverUserName, "/",
					repositoryName, "/pull/", pullRequestNumber));

			return _portalPullRequestURL;
		}

		return null;
	}

	@Override
	public String getTestSuiteName() {
		if (!StringUtil.isNullOrEmpty(_testSuiteName)) {
			return _testSuiteName;
		}

		_testSuiteName = super.getTestSuiteName();

		if (!StringUtil.isNullOrEmpty(_testSuiteName)) {
			return _testSuiteName;
		}

		_testSuiteName = getBuildParameterValue("CI_TEST_SUITE");

		return _testSuiteName;
	}

	protected DefaultPortalPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);

		_testSuiteName = jsonObject.optString("testSuiteName");
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParamaters =
			super.getInitialBuildParameters();

		initialBuildParamaters.put("CI_TEST_SUITE", getTestSuiteName());
		initialBuildParamaters.put("GITHUB_ORIGIN_NAME", getOriginName());
		initialBuildParamaters.put(
			"GITHUB_PULL_REQUEST_NUMBER",
			String.valueOf(_getPullRequestNumber()));
		initialBuildParamaters.put(
			"GITHUB_RECEIVER_USERNAME", _getPullRequestReceiverUserName());
		initialBuildParamaters.put(
			"GITHUB_REPOSITORY_NAME", _getPullRequestRepositoryName());
		initialBuildParamaters.put(
			"GITHUB_SENDER_BRANCH_NAME", getSenderBranchName());
		initialBuildParamaters.put(
			"GITHUB_SENDER_BRANCH_SHA", getSenderBranchSHA());
		initialBuildParamaters.put(
			"GITHUB_SENDER_USERNAME", getSenderUserName());
		initialBuildParamaters.put(
			"GITHUB_UPSTREAM_BRANCH_NAME", getUpstreamBranchName());
		initialBuildParamaters.put(
			"GITHUB_UPSTREAM_BRANCH_SHA", getUpstreamBranchSHA());

		initialBuildParamaters.put("TEST_PORTAL_BUILD_PROFILE", "dxp");

		return initialBuildParamaters;
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

	private URL _portalPullRequestURL;
	private long _pullRequestNumber;
	private String _receiverUserName;
	private String _repositoryName;
	private String _testSuiteName;

}