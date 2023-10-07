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

	@Override
	public String getTestSuiteName() {
		if (_testSuiteName != null) {
			return _testSuiteName;
		}

		_testSuiteName = getBuildParameterValue("CI_TEST_SUITE");

		return _testSuiteName;
	}

	protected DefaultPortalPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);

		_portalPullRequestURL = jsonObject.optString("portalPullRequestURL");

		_testSuiteName = jsonObject.optString("testSuiteName");
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParamaters =
			super.getInitialBuildParameters();

		initialBuildParamaters.put("CI_TEST_SUITE", getTestSuiteName());
		initialBuildParamaters.put(
			"GITHUB_PULL_REQUEST_NUMBER",
			String.valueOf(_getPullRequestNumber()));
		initialBuildParamaters.put(
			"GITHUB_RECEIVER_USERNAME", _getPullRequestReceiverUserName());
		initialBuildParamaters.put("TEST_PORTAL_BUILD_PROFILE", "dxp");

		return initialBuildParamaters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "test-portal-acceptance-pullrequest(master)";
	}

	private long _getPullRequestNumber() {
		if (_pullRequestNumber > 0) {
			return _pullRequestNumber;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(_portalPullRequestURL);

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

		Matcher matcher = _pullRequestURLPattern.matcher(_portalPullRequestURL);

		if (matcher.find()) {
			_receiverUserName = matcher.group("receiverUserName");

			return _receiverUserName;
		}

		return null;
	}

	private static final Pattern _pullRequestURLPattern = Pattern.compile(
		StringUtil.combine(
			"https://github.com/(?<receiverUserName>[^/]+)/liferay-portal",
			"/pull/(?<number>\\d+)"));

	private final String _portalPullRequestURL;
	private long _pullRequestNumber;
	private String _receiverUserName;
	private String _testSuiteName;

}