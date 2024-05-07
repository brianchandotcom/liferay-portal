/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalUpstreamJobEntity
	extends BaseJobEntity implements PortalUpstreamJobEntity {

	@Override
	public URL getOSBAsahBranchURL() {
		return getParameterValueURL("osbAsahBranchURL");
	}

	@Override
	public String getPortalBranchSHA() {
		return getParameterValue("portalBranchSHA");
	}

	@Override
	public URL getPortalBranchURL() {
		return getParameterValueURL("portalBranchURL");
	}

	@Override
	public String getPortalBuildProfile() {
		return getParameterValue("portalBuildProfile");
	}

	@Override
	public String getPortalUpstreamBranchName() {
		return getParameterValue("portalUpstreamBranchName");
	}

	@Override
	public String getTestrayBuildName() {
		return getParameterValue("testrayBuildName");
	}

	@Override
	public String getTestrayProjectName() {
		return getParameterValue("testrayProjectName");
	}

	@Override
	public String getTestrayRoutineName() {
		return getParameterValue("testrayRoutineName");
	}

	@Override
	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	@Override
	public void setOSBAsahBranchURL(URL osbAsahBranchURL) {
		setParameterValueURL("osbAsahBranchURL", osbAsahBranchURL);
	}

	@Override
	public void setPortalBranchSHA(String portalBranchSHA) {
		setParameterValue("portalBranchSHA", portalBranchSHA);
	}

	@Override
	public void setPortalBranchURL(URL portalBranchURL) {
		setParameterValueURL("portalBranchURL", portalBranchURL);
	}

	@Override
	public void setPortalBuildProfile(String portalBuildProfile) {
		setParameterValue("portalBuildProfile", portalBuildProfile);
	}

	@Override
	public void setTestrayBuildName(String testrayBuildName) {
		setParameterValue("testrayBuildName", testrayBuildName);
	}

	@Override
	public void setTestrayProjectName(String testrayProjectName) {
		setParameterValue("testrayProjectName", testrayProjectName);
	}

	@Override
	public void setTestrayRoutineName(String testrayRoutineName) {
		setParameterValue("testrayRoutineName", testrayRoutineName);
	}

	@Override
	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	@Override
	public void setUpstreamPortalBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	protected BasePortalUpstreamJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

}