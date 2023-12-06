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
public class PortalReleaseJobEntity extends BaseJobEntity {

	@Override
	public String getJenkinsJobName() {
		return "test-portal-release";
	}

	public String getPortalBranchSHA() {
		return getParameterValue("portalBranchSHA");
	}

	public URL getPortalBranchURL() {
		String upstreamBranchURL = getParameterValue("portalBranchURL");

		if (StringUtil.isNullOrEmpty(upstreamBranchURL)) {
			return null;
		}

		return StringUtil.toURL(upstreamBranchURL);
	}

	public String getPortalBuildProfile() {
		return getParameterValue("portalBuildProfile");
	}

	public URL getPortalReleaseDependenciesURL() {
		String portalReleaseDependenciesURL = getParameterValue(
			"portalReleaseDependenciesURL");

		if (StringUtil.isNullOrEmpty(portalReleaseDependenciesURL)) {
			return null;
		}

		return StringUtil.toURL(portalReleaseDependenciesURL);
	}

	public URL getPortalReleaseOSGiURL() {
		String portalReleaseOSGiURL = getParameterValue("portalReleaseOSGiURL");

		if (StringUtil.isNullOrEmpty(portalReleaseOSGiURL)) {
			return null;
		}

		return StringUtil.toURL(portalReleaseOSGiURL);
	}

	public URL getPortalReleaseSQLURL() {
		String portalReleaseSQLURL = getParameterValue("portalReleaseSQLURL");

		if (StringUtil.isNullOrEmpty(portalReleaseSQLURL)) {
			return null;
		}

		return StringUtil.toURL(portalReleaseSQLURL);
	}

	public URL getPortalReleaseTomcatURL() {
		String portalReleaseTomcatURL = getParameterValue(
			"portalReleaseTomcatURL");

		if (StringUtil.isNullOrEmpty(portalReleaseTomcatURL)) {
			return null;
		}

		return StringUtil.toURL(portalReleaseTomcatURL);
	}

	public URL getPortalReleaseToolsURL() {
		String portalReleaseToolsURL = getParameterValue(
			"portalReleaseToolsURL");

		if (StringUtil.isNullOrEmpty(portalReleaseToolsURL)) {
			return null;
		}

		return StringUtil.toURL(portalReleaseToolsURL);
	}

	public String getPortalReleaseVersion() {
		return getParameterValue("portalReleaseVersion");
	}

	public URL getPortalReleaseWarURL() {
		String portalReleaseWarURL = getParameterValue("portalReleaseWarURL");

		if (StringUtil.isNullOrEmpty(portalReleaseWarURL)) {
			return null;
		}

		return StringUtil.toURL(portalReleaseWarURL);
	}

	public String getPortalUpstreamBranchName() {
		return getParameterValue("portalUpstreamBranchName");
	}

	public String getTestrayBuildName() {
		return getParameterValue("testrayBuildName");
	}

	public String getTestrayRoutineName() {
		return getParameterValue("testrayRoutineName");
	}

	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	public void setPortalBranchSHA(String portalBranchSHA) {
		setParameterValue("portalBranchSHA", portalBranchSHA);
	}

	public void setPortalBranchURL(URL portalBranchURL) {
		setParameterValue("portalBranchURL", String.valueOf(portalBranchURL));
	}

	public void setPortalBuildProfile(String portalBuildProfile) {
		setParameterValue("portalBuildProfile", portalBuildProfile);
	}

	public void setPortalReleaseDependenciesURL(
		URL portalReleaseDependenciesURL) {

		setParameterValue(
			"portalReleaseDependenciesURL",
			String.valueOf(portalReleaseDependenciesURL));
	}

	public void setPortalReleaseOSGiURL(URL portalReleaseOSGiURL) {
		setParameterValue(
			"portalReleaseOSGiURL", String.valueOf(portalReleaseOSGiURL));
	}

	public void setPortalReleaseSQLURL(URL portalReleaseSQLURL) {
		setParameterValue(
			"portalReleaseSQLURL", String.valueOf(portalReleaseSQLURL));
	}

	public void setPortalReleaseTomcatURL(URL portalReleaseTomcatURL) {
		setParameterValue(
			"portalReleaseTomcatURL", String.valueOf(portalReleaseTomcatURL));
	}

	public void setPortalReleaseToolsURL(URL portalReleaseToolsURL) {
		setParameterValue(
			"portalReleaseToolsURL", String.valueOf(portalReleaseToolsURL));
	}

	public void setPortalReleaseVersion(String portalReleaseVersion) {
		setParameterValue("portalReleaseVersion", portalReleaseVersion);
	}

	public void setPortalReleaseWarURL(URL portalReleaseWarURL) {
		setParameterValue(
			"portalReleaseWarURL", String.valueOf(portalReleaseWarURL));
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	public void setTestrayBuildName(String testrayBuildName) {
		setParameterValue("testrayBuildName", testrayBuildName);
	}

	public void setTestrayRoutineName(String testrayRoutineName) {
		setParameterValue("testrayRoutineName", testrayRoutineName);
	}

	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	protected PortalReleaseJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("CI_TEST_SUITE", getTestSuiteName());
		initialBuildParameters.put("TESTRAY_BUILD_NAME", getTestrayBuildName());
		initialBuildParameters.put(
			"TESTRAY_ROUTINE_NAME", getTestrayRoutineName());
		initialBuildParameters.put(
			"TEST_PORTAL_BRANCH_NAME", getPortalUpstreamBranchName());
		initialBuildParameters.put(
			"TEST_PORTAL_BUILD_PROFILE", getPortalBuildProfile());
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_DEPENDENCIES_URL",
			String.valueOf(getPortalReleaseDependenciesURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_GIT_ID", getPortalBranchSHA());
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_OSGI_URL",
			String.valueOf(getPortalReleaseOSGiURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_SQL_URL",
			String.valueOf(getPortalReleaseSQLURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_TOMCAT_URL",
			String.valueOf(getPortalReleaseTomcatURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_TOOLS_URL",
			String.valueOf(getPortalReleaseToolsURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_VERSION", getPortalReleaseVersion());
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_WAR_URL",
			String.valueOf(getPortalReleaseWarURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_REPOSITORY_NAME", _getPortalBranchRepositoryName());
		initialBuildParameters.put(
			"TEST_PORTAL_USER_BRANCH_NAME", _getPortalBranchName());
		initialBuildParameters.put(
			"TEST_PORTAL_USER_NAME", _getPortalBranchUserName());

		return initialBuildParameters;
	}

	private String _getPortalBranchName() {
		return getBranchURLGroupValue(getPortalBranchURL(), "branchName");
	}

	private String _getPortalBranchRepositoryName() {
		return getBranchURLGroupValue(getPortalBranchURL(), "repositoryName");
	}

	private String _getPortalBranchUserName() {
		return getBranchURLGroupValue(getPortalBranchURL(), "userName");
	}

}