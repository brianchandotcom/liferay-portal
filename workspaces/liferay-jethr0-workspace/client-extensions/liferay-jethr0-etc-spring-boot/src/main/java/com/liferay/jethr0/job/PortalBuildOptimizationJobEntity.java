/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
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
public class PortalBuildOptimizationJobEntity extends BaseJobEntity {

	@Override
	public String getJenkinsJobName() {
		return "test-portal-build-optimization";
	}

	public URL getPortalPullRequestURL() {
		return getParameterValueURL("portalPullRequestURL");
	}

	public String getPortalUpstreamBranchName() {
		return getParameterValue("portalUpstreamBranchName");
	}

	public String getPortalUpstreamBranchSHA() {
		return getParameterValue("portalUpstreamBranchSHA");
	}

	public void setPortalPullRequestURL(URL portalPullRequestURL) {
		setParameterValueURL("portalPullRequestURL", portalPullRequestURL);
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	public void setPortalUpstreamBranchSHA(String portalUpstreamBranchSHA) {
		setParameterValue("portalUpstreamBranchSHA", portalUpstreamBranchSHA);
	}

	protected PortalBuildOptimizationJobEntity(JSONObject jsonObject) {
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
		initialBuildParameters.put(
			"GITHUB_UPSTREAM_BRANCH_SHA", getPortalUpstreamBranchSHA());
		initialBuildParameters.put(
			"TEST_PORTAL_BRANCH_NAME", getPortalUpstreamBranchName());

		return initialBuildParameters;
	}

	private long _getPullRequestNumber() {
		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPortalPullRequestURL()));

		if (!matcher.find()) {
			return -1;
		}

		return Long.valueOf(matcher.group("number"));
	}

	private String _getPullRequestReceiverUserName() {
		Matcher matcher = _pullRequestURLPattern.matcher(
			String.valueOf(getPortalPullRequestURL()));

		if (!matcher.find()) {
			return null;
		}

		return matcher.group("receiverUserName");
	}

	private static final Pattern _pullRequestURLPattern = Pattern.compile(
		StringUtil.combine(
			"https://github.com/(?<receiverUserName>[^/]+)/",
			"(?<repositoryName>liferay-portal(-ee)?)",
			"/pull/(?<number>\\d+)"));

}