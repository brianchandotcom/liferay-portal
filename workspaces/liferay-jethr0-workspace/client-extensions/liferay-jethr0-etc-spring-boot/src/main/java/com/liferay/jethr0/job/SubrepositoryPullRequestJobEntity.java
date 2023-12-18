/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SubrepositoryPullRequestJobEntity extends BaseJobEntity {

	public SubrepositoryPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public String getJenkinsJobName() {
		return "test-subrepository-acceptance-pullrequest";
	}

	public String getPortalUpstreamBranchName() {
		return getParameterValue("portalUpstreamBranchName");
	}

	public URL getSubrepositoryPullRequestURL() {
		return getParameterValueURL("subrepositoryPullRequestURL");
	}

	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	public void setSubrepositoryPullRequestURL(
		URL subrepositoryPullRequestURL) {

		setParameterValueURL(
			"subrepositoryPullRequestURL", subrepositoryPullRequestURL);
	}

	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"CI_TEST_SUITE", String.valueOf(getTestSuiteName()));
		initialBuildParameters.put(
			"GITHUB_PULL_REQUEST_NUMBER",
			String.valueOf(_getPullRequestNumber()));
		initialBuildParameters.put(
			"GITHUB_RECEIVER_USERNAME", _getPullRequestReceiverUserName());
		initialBuildParameters.put(
			"PORTAL_UPSTREAM_BRANCH_NAME", getPortalUpstreamBranchName());
		initialBuildParameters.put(
			"REPOSITORY_NAME", _getPullRequestRepositoryName());

		return initialBuildParameters;
	}

	private long _getPullRequestNumber() {
		if (_pullRequestNumber > 0) {
			return _pullRequestNumber;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getSubrepositoryPullRequestURL()));

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
			String.valueOf(getSubrepositoryPullRequestURL()));

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
			String.valueOf(getSubrepositoryPullRequestURL()));

		if (matcher.find()) {
			_repositoryName = matcher.group("repositoryName");

			return _repositoryName;
		}

		return null;
	}

	private static final Pattern _pullRequestURLPattern = Pattern.compile(
		StringUtil.combine(
			"https://github.com/(?<receiverUserName>[^/]+)/",
			"(?<repositoryName>com-liferay-[^/]+)", "/pull/(?<number>\\d+)"));

	private long _pullRequestNumber;
	private String _receiverUserName;
	private String _repositoryName;

}