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
public class PortalHotfixReleaseJobEntity extends BaseJobEntity {

	@Override
	public String getJenkinsJobName() {
		return "test-portal-hotfix-release";
	}

	public URL getPortalBranchURL() {
		String portalBranchURL = getParameterValue("portalBranchURL");

		if (StringUtil.isNullOrEmpty(portalBranchURL)) {
			return null;
		}

		return StringUtil.toURL(portalBranchURL);
	}

	public URL getPortalHotfixReleaseURL() {
		String portalHotfixReleaseURL = getParameterValue(
			"portalHotfixReleaseURL");

		if (StringUtil.isNullOrEmpty(portalHotfixReleaseURL)) {
			return null;
		}

		return StringUtil.toURL(portalHotfixReleaseURL);
	}

	public String getPortalPatcherBuildID() {
		return getParameterValue("portalPatcherBuildID");
	}

	public String getPortalPatcherRequestKey() {
		return getParameterValue("portalPatcherRequestKey");
	}

	public String getPortalPatcherUserID() {
		return getParameterValue("portalPatcherUserID");
	}

	public URL getPortalPatchTicketURL() {
		String portalPatchTicketURL = getParameterValue("portalPatchTicketURL");

		if (StringUtil.isNullOrEmpty(portalPatchTicketURL)) {
			return null;
		}

		return StringUtil.toURL(portalPatchTicketURL);
	}

	public String getPortalReleaseVersion() {
		return getParameterValue("portalReleaseVersion");
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

	public void setPortalHotfixReleaseURL(URL portalHotfixReleaseURL) {
		setParameterValue(
			"portalHotfixReleaseURL", String.valueOf(portalHotfixReleaseURL));
	}

	public void setPortalPatcherBuildID(String portalPatcherBuildID) {
		setParameterValue("portalPatcherBuildID", portalPatcherBuildID);
	}

	public void setPortalPatcherRequestKey(String portalPatcherRequestKey) {
		setParameterValue("portalPatcherRequestKey", portalPatcherRequestKey);
	}

	public void setPortalPatcherUserID(String portalPatcherUserID) {
		setParameterValue("portalPatcherUserID", portalPatcherUserID);
	}

	public void setPortalPatchTicketURL(URL portalPatchTicketURL) {
		setParameterValue(
			"portalPatchTicketURL", String.valueOf(portalPatchTicketURL));
	}

	public void setPortalReleaseVersion(String portalReleaseVersion) {
		setParameterValue("portalReleaseVersion", portalReleaseVersion);
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	protected PortalHotfixReleaseJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("CI_TEST_SUITE", getTestSuiteName());
		initialBuildParameters.put(
			"PATCHER_BUILD_ID", getPortalPatcherBuildID());
		initialBuildParameters.put(
			"PATCHER_BUILD_PATCHER_PORTAL_VERSION", getPortalReleaseVersion());
		initialBuildParameters.put(
			"PATCHER_REQUEST_KEY", getPortalPatcherRequestKey());
		initialBuildParameters.put("PATCHER_USER_ID", getPortalPatcherUserID());
		initialBuildParameters.put(
			"TEST_BUILD_HOTFIX_ZIP_URL",
			String.valueOf(getPortalHotfixReleaseURL()));
		initialBuildParameters.put(
			"TEST_BUILD_TICKET_URLS",
			String.valueOf(getPortalPatchTicketURL()));
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