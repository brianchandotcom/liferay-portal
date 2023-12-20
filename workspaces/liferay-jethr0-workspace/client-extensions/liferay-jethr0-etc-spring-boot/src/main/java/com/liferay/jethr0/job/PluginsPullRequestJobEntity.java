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
public class PluginsPullRequestJobEntity extends BaseJobEntity {

	@Override
	public String getJenkinsJobName() {
		return StringUtil.combine(
			"test-plugins-acceptance-pullrequest(",
			getPluginsUpstreamBranchName(), ")");
	}

	public URL getPluginsPullRequestURL() {
		return getParameterValueURL("pluginsPullRequestURL");
	}

	public String getPluginsUpstreamBranchName() {
		return getParameterValue("pluginsUpstreamBranchName");
	}

	public void setPluginsPullRequestURL(URL pluginsPullRequestURL) {
		setParameterValueURL("pluginsPullRequestURL", pluginsPullRequestURL);
	}

	public void setPluginsUpstreamBranchName(String pluginsUpstreamBranchName) {
		setParameterValue(
			"pluginsUpstreamBranchName", pluginsUpstreamBranchName);
	}

	protected PluginsPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"GITHUB_PULL_REQUEST_NUMBER",
			String.valueOf(_getPullRequestNumber()));
		initialBuildParameters.put(
			"GITHUB_RECEIVER_USERNAME", _getPullRequestReceiverUserName());

		return initialBuildParameters;
	}

	private long _getPullRequestNumber() {
		if (_pullRequestNumber > 0) {
			return _pullRequestNumber;
		}

		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPluginsPullRequestURL()));

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
			String.valueOf(getPluginsPullRequestURL()));

		if (matcher.find()) {
			_receiverUserName = matcher.group("receiverUserName");

			return _receiverUserName;
		}

		return null;
	}

	private static final Pattern _pullRequestURLPattern = Pattern.compile(
		StringUtil.combine(
			"https://github.com/(?<receiverUserName>[^/]+)/liferay-plugins-ee",
			"/pull/(?<number>\\d+)"));

	private long _pullRequestNumber;
	private String _receiverUserName;

}