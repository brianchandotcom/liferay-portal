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
public class PluginsMarketplaceAppsJobEntity extends BaseJobEntity {

	public URL getCallbackURL() {
		return getParameterValueURL("callbackURL");
	}

	@Override
	public String getJenkinsJobName() {
		return "test-plugins-marketplaceapp";
	}

	public String getPluginsMarketplaceAppFileName() {
		return getParameterValue("pluginsMarketplaceAppFileName");
	}

	public String getPluginsMarketplaceAppType() {
		return getParameterValue("pluginsMarketplaceAppType");
	}

	public URL getPluginsMarketplaceAppURL() {
		return getParameterValueURL("pluginsMarketplaceAppURL");
	}

	public String getPortalReleaseVersion() {
		return getParameterValue("portalReleaseVersion");
	}

	public void setCallbackURL(URL callbackURL) {
		setParameterValueURL("callbackURL", callbackURL);
	}

	public void setPluginsMarketplaceAppFileName(
		String pluginsMarketplaceAppFileName) {

		setParameterValue(
			"pluginsMarketplaceAppFileName", pluginsMarketplaceAppFileName);
	}

	public void setPluginsMarketplaceAppType(String pluginsMarketplaceAppType) {
		setParameterValue(
			"pluginsMarketplaceAppType", pluginsMarketplaceAppType);
	}

	public void setPluginsMarketplaceAppURL(URL pluginsMarketplaceAppURL) {
		setParameterValueURL(
			"pluginsMarketplaceAppURL", pluginsMarketplaceAppURL);
	}

	public void setPortalReleaseVersion(String portalReleaseVersion) {
		setParameterValue("portalReleaseVersion", portalReleaseVersion);
	}

	protected PluginsMarketplaceAppsJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"TEST_APP_TYPE", getPluginsMarketplaceAppType());
		initialBuildParameters.put(
			"TEST_CALLBACK_URL", String.valueOf(getCallbackURL()));
		initialBuildParameters.put(
			"TEST_MARKETPLACE_APP_ZIP_URL",
			String.valueOf(getPluginsMarketplaceAppURL()));
		initialBuildParameters.put(
			"TEST_PACKAGE_FILE_NAME", getPluginsMarketplaceAppFileName());
		initialBuildParameters.put(
			"TEST_PORTAL_BUILD_NUMBER", getPortalReleaseVersion());

		return initialBuildParameters;
	}

}