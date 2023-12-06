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
public class TestSuitePortalUpstreamJobEntity
	extends BasePortalUpstreamJobEntity {

	protected TestSuitePortalUpstreamJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"CI_TEST_SUITE", String.valueOf(getTestSuiteName()));
		initialBuildParameters.put("PORTAL_GIT_COMMIT", getBranchSHA());
		initialBuildParameters.put(
			"PORTAL_GITHUB_URL", String.valueOf(getBranchURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_BUILD_PROFILE", getBuildProfile());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return StringUtil.combine(
			"test-portal-testsuite-upstream(", getUpstreamBranchName(), ")");
	}

}