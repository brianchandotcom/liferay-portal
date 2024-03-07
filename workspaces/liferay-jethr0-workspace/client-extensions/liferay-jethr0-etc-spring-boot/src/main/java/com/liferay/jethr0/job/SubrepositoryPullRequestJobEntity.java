/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SubrepositoryPullRequestJobEntity
	extends BasePullRequestJobEntity {

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

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"CI_TEST_SUITE", String.valueOf(getTestSuiteName()));
		initialBuildParameters.put(
			"GITHUB_PULL_REQUEST_NUMBER", String.valueOf(getNumber()));
		initialBuildParameters.put(
			"GITHUB_RECEIVER_USERNAME", getReceiverUserName());
		initialBuildParameters.put(
			"PORTAL_UPSTREAM_BRANCH_NAME", getPortalUpstreamBranchName());
		initialBuildParameters.put("REPOSITORY_NAME", getRepositoryName());

		return initialBuildParameters;
	}

}