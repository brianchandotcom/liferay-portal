/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.util.Map;
import java.util.Objects;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class AcceptancePortalUpstreamJobEntity
	extends BasePortalUpstreamJobEntity {

	@Override
	public String getTestSuiteName() {
		return "acceptance-upstream";
	}

	protected AcceptancePortalUpstreamJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("PORTAL_GIT_COMMIT", getBranchSHA());
		initialBuildParameters.put(
			"PORTAL_GITHUB_URL", String.valueOf(getBranchURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_BUILD_PROFILE", getBuildProfile());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		StringBuilder sb = new StringBuilder();

		sb.append("test-portal-acceptance-upstream");

		if (Objects.equals(getBuildProfile(), "dxp")) {
			sb.append("-dxp");
		}

		sb.append("(");
		sb.append(getUpstreamBranchName());
		sb.append(")");

		return sb.toString();
	}

}