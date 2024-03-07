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
public class FixpackBuilderPullRequestJobEntity
	extends BasePullRequestJobEntity {

	public FixpackBuilderPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public String getJenkinsJobName() {
		return "test-fixpack-builder-pullrequest";
	}

	public String getQAWebsitesBranchSHA() {
		return getParameterValue("qaWebsitesBranchSHA");
	}

	public URL getQAWebsitesBranchURL() {
		return getParameterValueURL("qaWebsitesBranchURL");
	}

	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	public void setQAWebsitesBranchSHA(String qaWebsitesBranchSHA) {
		setParameterValue("qaWebsitesBranchSHA", qaWebsitesBranchSHA);
	}

	public void setQAWebsitesBranchURL(URL qaWebsitesBranchURL) {
		setParameterValueURL("qaWebsitesBranchURL", qaWebsitesBranchURL);
	}

	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("CI_TEST_SUITE", getTestSuiteName());
		initialBuildParameters.put(
			"GITHUB_PULL_REQUEST_NUMBER",
			String.valueOf(_getPullRequestNumber()));
		initialBuildParameters.put(
			"GITHUB_RECEIVER_USERNAME", _getPullRequestReceiverUserName());
		initialBuildParameters.put(
			"TEST_QA_WEBSITES_BRANCH_NAME", _getQAWebsitesBranchName());
		initialBuildParameters.put(
			"TEST_QA_WEBSITES_BRANCH_USERNAME", _getQAWebsitesBranchUserName());
		initialBuildParameters.put(
			"TEST_QA_WEBSITES_GIT_ID", getQAWebsitesBranchSHA());

		return initialBuildParameters;
	}

	private long _getPullRequestNumber() {
		if (_pullRequestNumber > 0) {
			return _pullRequestNumber;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPullRequestURL()));

		if (matcher.find()) {
			_pullRequestNumber = Long.valueOf(
				matcher.group("pullRequestNumber"));

			return _pullRequestNumber;
		}

		return -1;
	}

	private String _getPullRequestReceiverUserName() {
		if (!StringUtil.isNullOrEmpty(_receiverUserName)) {
			return _receiverUserName;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPullRequestURL()));

		if (matcher.find()) {
			_receiverUserName = matcher.group("receiverUserName");

			return _receiverUserName;
		}

		return null;
	}

	private String _getQAWebsitesBranchName() {
		if (!StringUtil.isNullOrEmpty(_qaWebsitesBranchName)) {
			return _qaWebsitesBranchName;
		}

		Matcher matcher = _branchURLPattern.matcher(
			String.valueOf(getQAWebsitesBranchURL()));

		if (matcher.find()) {
			_qaWebsitesBranchName = matcher.group("branchName");

			return _qaWebsitesBranchName;
		}

		return null;
	}

	private String _getQAWebsitesBranchUserName() {
		if (!StringUtil.isNullOrEmpty(_qaWebsitesBranchUserName)) {
			return _qaWebsitesBranchUserName;
		}

		Matcher matcher = _branchURLPattern.matcher(
			String.valueOf(getQAWebsitesBranchURL()));

		if (matcher.find()) {
			_qaWebsitesBranchUserName = matcher.group("userName");

			return _qaWebsitesBranchUserName;
		}

		return null;
	}

	private static final Pattern _branchURLPattern = Pattern.compile(
		StringUtil.combine(
			"https://github.com/(?<userName>[^/]+)/(?<repositoryName>[^/]+)",
			"/tree/(?<branchName>[^/]+)"));
	private static final Pattern _pullRequestURLPattern = Pattern.compile(
		StringUtil.combine(
			"https://github.com/(?<receiverUserName>[^/]+)/",
			"liferay-fix-pack-builder-ee/pull/(?<pullRequestNumber>\\d+)"));

	private long _pullRequestNumber;
	private String _qaWebsitesBranchName;
	private String _qaWebsitesBranchUserName;
	private String _receiverUserName;

}