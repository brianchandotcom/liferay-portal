/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.util.StringUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URL;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class JenkinsPullRequestJobEntity
	extends BasePullRequestJobEntity implements PullRequestJobEntity {

	public JenkinsPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public String getJenkinsJobName() {
		return "test-jenkins-acceptance-pullrequest";
	}

	public URL getJenkinsPullRequestURL() {
		return getParameterValueURL("jenkinsPullRequestURL");
	}

	public void setJenkinsPullRequestURL(URL jenkinsPullRequestURL) {
		setParameterValueURL("jenkinsPullRequestURL", jenkinsPullRequestURL);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		return HashMapBuilder.put(
			"BUILD_PRIORITY", String.valueOf(getPriority())
		).put(
			"GITHUB_ORIGIN_NAME", getOriginName()
		).put(
			"GITHUB_PULL_REQUEST_NUMBER",
			String.valueOf(_getPullRequestNumber())
		).put(
			"GITHUB_RECEIVER_USERNAME", _getPullRequestReceiverUserName()
		).put(
			"GITHUB_SENDER_BRANCH_NAME", getSenderBranchName()
		).put(
			"GITHUB_SENDER_BRANCH_SHA", getSenderBranchSHA()
		).put(
			"GITHUB_SENDER_USERNAME", getSenderUserName()
		).put(
			"GITHUB_UPSTREAM_BRANCH_NAME", getUpstreamBranchName()
		).put(
			"GITHUB_UPSTREAM_BRANCH_SHA", getUpstreamBranchSHA()
		).build();
	}

	private long _getPullRequestNumber() {
		if (_pullRequestNumber > 0) {
			return _pullRequestNumber;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getJenkinsPullRequestURL()));

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
			String.valueOf(getJenkinsPullRequestURL()));

		if (matcher.find()) {
			_receiverUserName = matcher.group("receiverUserName");

			return _receiverUserName;
		}

		return null;
	}

	private static final Pattern _pullRequestURLPattern = Pattern.compile(
		StringUtil.combine(
			"https://github.com/(?<receiverUserName>[^/]+)/",
			"liferay-jenkins-ee/pull/(?<pullRequestNumber>\\d+)"));

	private long _pullRequestNumber;
	private String _receiverUserName;

}