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
public class PluginsExtraAppsJobEntity extends BaseJobEntity {

	@Override
	public String getJenkinsJobName() {
		return "test-plugins-extraapps";
	}

	public URL getPluginsExtraAppsURL() {
		return getParameterValueURL("pluginsExtraAppsURL");
	}

	public String getPortalReleaseVersion() {
		return getParameterValue("portalReleaseVersion");
	}

	public String getPortalUpstreamBranchName() {
		return getParameterValue("portalUpstreamBranchName");
	}

	public String getPoshiQuery() {
		return getParameterValue("poshiQuery");
	}

	public void setPluginsExtraAppsURL(URL pluginsExtraAppsURL) {
		setParameterValueURL("pluginsExtraAppsURL", pluginsExtraAppsURL);
	}

	public void setPortalReleaseVersion(String portalReleaseVersion) {
		setParameterValue("portalReleaseVersion", portalReleaseVersion);
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	public void setPoshiQuery(String poshiQuery) {
		setParameterValue("poshiQuery", poshiQuery);
	}

	protected PluginsExtraAppsJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"TEST_BATCH_RUN_PROPERTY_QUERY", getPoshiQuery());
		initialBuildParameters.put(
			"TEST_BUILD_EXTRAAPPS_ZIP_URL",
			String.valueOf(getPluginsExtraAppsURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_BRANCH_NAME", getPortalUpstreamBranchName());
		initialBuildParameters.put(
			"TEST_PORTAL_BUNDLE_VERSION", getPortalReleaseVersion());

		return initialBuildParameters;
	}

}