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
public class PortalFixpackReleaseJobEntity extends BaseJobEntity {

	@Override
	public String getJenkinsJobName() {
		return "test-portal-fixpack-release";
	}

	public URL getPortalBranchURL() {
		String portalBranchURL = getParameterValue("portalBranchURL");

		if (StringUtil.isNullOrEmpty(portalBranchURL)) {
			return null;
		}

		return StringUtil.toURL(portalBranchURL);
	}

	public URL getPortalFixpackReleaseURL() {
		String portalFixpackReleaseURL = getParameterValue(
			"portalFixpackReleaseURL");

		if (StringUtil.isNullOrEmpty(portalFixpackReleaseURL)) {
			return null;
		}

		return StringUtil.toURL(portalFixpackReleaseURL);
	}

	public String getPortalUpstreamBranchName() {
		return getParameterValue("portalUpstreamBranchName");
	}

	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	public void setPortalBranchURL(URL portalBranchURL) {
		setParameterValue("portalBranchURL", String.valueOf(portalBranchURL));
	}

	public void setPortalFixpackReleaseURL(URL portalFixpackReleaseURL) {
		setParameterValue(
			"portalFixpackReleaseURL", String.valueOf(portalFixpackReleaseURL));
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	protected PortalFixpackReleaseJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("CI_TEST_SUITE", getTestSuiteName());
		initialBuildParameters.put(
			"TEST_BUILD_FIX_PACK_ZIP_URL",
			String.valueOf(getPortalFixpackReleaseURL()));
		initialBuildParameters.put(
			"TEST_BUILD_TICKET_URLS", String.valueOf(getPortalBranchURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_BASE_BRANCH_NAME", getPortalUpstreamBranchName());
		initialBuildParameters.put(
			"TEST_PORTAL_USER_BRANCH_NAME", _getPortalBranchName());
		initialBuildParameters.put(
			"TEST_PORTAL_USER_NAME", _getPortalBranchUserName());

		return initialBuildParameters;
	}

	private String _getPortalBranchName() {
		return getBranchURLGroupValue(getPortalBranchURL(), "branchName");
	}

	private String _getPortalBranchUserName() {
		return getBranchURLGroupValue(getPortalBranchURL(), "userName");
	}

}