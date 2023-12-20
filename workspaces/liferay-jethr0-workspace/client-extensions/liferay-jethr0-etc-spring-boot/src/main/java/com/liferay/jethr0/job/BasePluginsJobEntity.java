/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.net.URL;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePluginsJobEntity extends BaseJobEntity {

	public String getPluginName() {
		return getParameterValue("pluginName");
	}

	public URL getPluginsBranchURL() {
		return getParameterValueURL("pluginsBranchURL");
	}

	public String getPluginsUpstreamBranchName() {
		return getParameterValue("pluginsUpstreamBranchName");
	}

	public String getPortalBranchSHA() {
		return getParameterValue("portalBranchSHA");
	}

	public URL getPortalBranchURL() {
		return getParameterValueURL("portalBranchURL");
	}

	public String getPortalReleaseVersion() {
		return getParameterValue("portalReleaseVersion");
	}

	public String getPortalUpstreamBranchName() {
		return getParameterValue("portalUpstreamBranchName");
	}

	public void setPluginName(String pluginName) {
		setParameterValue("pluginName", pluginName);
	}

	public void setPluginsBranchURL(URL pluginsBranchURL) {
		setParameterValueURL("pluginsBranchURL", pluginsBranchURL);
	}

	public void setPluginsUpstreamBranchName(String pluginsUpstreamBranchName) {
		setParameterValue(
			"pluginsUpstreamBranchName", pluginsUpstreamBranchName);
	}

	public void setPortalBranchSHA(String portalBranchSHA) {
		setParameterValue("portalBranchSHA", portalBranchSHA);
	}

	public void setPortalBranchURL(URL portalBranchURL) {
		setParameterValueURL("portalBranchURL", portalBranchURL);
	}

	public void setPortalReleaseVersion(String portalReleaseVersion) {
		setParameterValue("portalReleaseVersion", portalReleaseVersion);
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	protected BasePluginsJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"PORTAL_GITHUB_BRANCH_NAME", _getPortalBranchName());
		initialBuildParameters.put(
			"PORTAL_GITHUB_BRANCH_USERNAME", _getPortalBranchUserName());
		initialBuildParameters.put(
			"TEST_PLUGINS_BRANCH_NAME", getPluginsUpstreamBranchName());
		initialBuildParameters.put(
			"TEST_PLUGINS_BRANCH_USERNAME", _getPluginsBranchUserName());
		initialBuildParameters.put("TEST_PLUGINS_GIT_ID", getPortalBranchSHA());
		initialBuildParameters.put(
			"TEST_PLUGINS_RELEASE_TAG", _getPluginsBranchName());
		initialBuildParameters.put("TEST_PLUGIN_NAME", getPluginName());
		initialBuildParameters.put(
			"TEST_PORTAL_BRANCH_NAME", getPortalUpstreamBranchName());
		initialBuildParameters.put(
			"TEST_PORTAL_BUNDLE_VERSION", getPortalReleaseVersion());

		return initialBuildParameters;
	}

	private String _getPluginsBranchName() {
		return getBranchURLGroupValue(getPluginsBranchURL(), "branchName");
	}

	private String _getPluginsBranchUserName() {
		return getBranchURLGroupValue(getPluginsBranchURL(), "userName");
	}

	private String _getPortalBranchName() {
		return getBranchURLGroupValue(getPortalBranchURL(), "branchName");
	}

	private String _getPortalBranchUserName() {
		return getBranchURLGroupValue(getPortalBranchURL(), "userName");
	}

}