/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class DefaultPortalUpstreamJobEntity
	extends BasePortalUpstreamJobEntity {

	protected DefaultPortalUpstreamJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"CI_TEST_SUITE", String.valueOf(getTestSuiteName()));
		initialBuildParameters.put(
			"OSB_ASAH_GITHUB_URL", String.valueOf(getOSBAsahBranchURL()));
		initialBuildParameters.put("PORTAL_GIT_COMMIT", getPortalBranchSHA());
		initialBuildParameters.put(
			"PORTAL_GITHUB_URL", String.valueOf(getPortalBranchURL()));
		initialBuildParameters.put(
			"PORTAL_UPSTREAM_BRANCH_NAME", getPortalUpstreamBranchName());
		initialBuildParameters.put(
			"TEST_PORTAL_BUILD_PROFILE", getPortalBuildProfile());
		initialBuildParameters.put("TESTRAY_BUILD_NAME", getTestrayBuildName());
		initialBuildParameters.put(
			"TESTRAY_PROJECT_NAME", getTestrayProjectName());
		initialBuildParameters.put(
			"TESTRAY_ROUTINE_NAME", getTestrayRoutineName());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "test-portal-upstream";
	}

}