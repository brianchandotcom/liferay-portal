/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.util.StringUtil;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class TestSuitePortalUpstreamJobEntity
	extends BasePortalUpstreamJobEntity {

	public static List<ParameterDefinition> getParameterDefinitions() {
		return Arrays.asList(
			PARAMETER_DEFINITION_JENKINS_GITHUB_URL,
			PARAMETER_DEFINITION_TEST_SUITE_NAME,
			PARAMETER_DEFINITION_UPSTREAM_BRANCH_NAME,
			PARAMETER_DEFINITION_UPSTREAM_BRANCH_URL);
	}

	protected TestSuitePortalUpstreamJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected String getJenkinsJobName() {
		return StringUtil.combine(
			"test-portal-testsuite-upstream(", getUpstreamBranchName(), ")");
	}

}