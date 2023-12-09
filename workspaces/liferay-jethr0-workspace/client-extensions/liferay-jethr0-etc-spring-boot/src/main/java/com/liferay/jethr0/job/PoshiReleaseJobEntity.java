/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PoshiReleaseJobEntity extends BaseJobEntity {

	public URL getPortalBranchURL() {
		String upstreamBranchURL = getParameterValue("portalBranchURL");

		if (StringUtil.isNullOrEmpty(upstreamBranchURL)) {
			return null;
		}

		return StringUtil.toURL(upstreamBranchURL);
	}

	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	public void setPortalBranchURL(URL portalBranchURL) {
		setParameterValue("portalBranchURL", String.valueOf(portalBranchURL));
	}

	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	protected PoshiReleaseJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"PORTAL_GITHUB_URL", String.valueOf(getPortalBranchURL()));
		initialBuildParameters.put(
			"PORTAL_MASTER_CI_TEST_SUITE", getTestSuiteName());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "test-poshi-release";
	}

}