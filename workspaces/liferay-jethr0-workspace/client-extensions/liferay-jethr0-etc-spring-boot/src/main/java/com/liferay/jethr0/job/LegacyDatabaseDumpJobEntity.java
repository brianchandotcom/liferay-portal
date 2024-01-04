/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.net.URL;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class LegacyDatabaseDumpJobEntity extends BaseJobEntity {

	public URL getPortalBranchURL() {
		return getParameterValueURL("portalBranchURL");
	}

	public URL getPortalLegacyBranchURL() {
		return getParameterValueURL("portalLegacyBranchURL");
	}

	public String getPortalLegacyVersions() {
		return getParameterValue("portalLegacyVersions");
	}

	public void setPortalBranchURL(URL portalBranchURL) {
		setParameterValueURL("portalBranchURL", portalBranchURL);
	}

	public void setPortalLegacyBranchURL(URL portalLegacyBranchURL) {
		setParameterValueURL("portalLegacyBranchURL", portalLegacyBranchURL);
	}

	public void setPortalLegacyVersions(String portalLegacyVersions) {
		setParameterValue("portalLegacyVersions", portalLegacyVersions);
	}

	protected LegacyDatabaseDumpJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"LEGACY_GITHUB_BRANCH_NAME", _getPortalLegacyBranchName());
		initialBuildParameters.put(
			"LEGACY_GITHUB_BRANCH_USERNAME", _getPortalLegacyBranchUserName());
		initialBuildParameters.put(
			"LEGACY_PORTAL_VERSIONS", getPortalLegacyVersions());
		initialBuildParameters.put(
			"PORTAL_GITHUB_BRANCH_NAME", _getPortalBranchName());
		initialBuildParameters.put(
			"PORTAL_GITHUB_BRANCH_USERNAME", _getPortalBranchUserName());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "legacy-database-dump";
	}

	private String _getPortalBranchName() {
		return getBranchURLGroupValue(getPortalBranchURL(), "branchName");
	}

	private String _getPortalBranchUserName() {
		return getBranchURLGroupValue(getPortalBranchURL(), "userName");
	}

	private String _getPortalLegacyBranchName() {
		return getBranchURLGroupValue(getPortalLegacyBranchURL(), "branchName");
	}

	private String _getPortalLegacyBranchUserName() {
		return getBranchURLGroupValue(getPortalLegacyBranchURL(), "userName");
	}

}