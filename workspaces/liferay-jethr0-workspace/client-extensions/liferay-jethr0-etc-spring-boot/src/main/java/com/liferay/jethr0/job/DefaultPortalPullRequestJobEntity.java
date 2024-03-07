/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.util.StringUtil;

import java.util.Map;

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

		initialBuildParameters.put(
			"CI_FORWARD_RECEIVER_USERNAME", getForwardReceiverUserName());
		initialBuildParameters.put("CI_TEST_SUITE", getTestSuiteName());
		initialBuildParameters.put("GITHUB_GIST_ID", getGitHubGistID());
		initialBuildParameters.put("GITHUB_ORIGIN_NAME", getOriginName());
		initialBuildParameters.put(
			"GITHUB_PULL_REQUEST_NUMBER", String.valueOf(getNumber()));
		initialBuildParameters.put(
			"GITHUB_RECEIVER_USERNAME", getReceiverUserName());
		initialBuildParameters.put(
			"GITHUB_REPOSITORY_NAME", getRepositoryName());
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

}