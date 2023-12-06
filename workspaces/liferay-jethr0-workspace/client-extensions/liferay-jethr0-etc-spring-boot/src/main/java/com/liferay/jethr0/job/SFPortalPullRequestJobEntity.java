/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class SFPortalPullRequestJobEntity
	extends BasePortalPullRequestJobEntity {

	public static List<ParameterDefinition> getParameterDefinitions() {
		return Arrays.asList(
			PARAMETER_DEFINITION_JENKINS_GITHUB_URL,
			PARAMETER_DEFINITION_PORTAL_PULL_REQUEST);
	}

	protected SFPortalPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"PULL_REQUEST_URL", String.valueOf(getPortalPullRequestURL()));

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "test-portal-source-format";
	}

}