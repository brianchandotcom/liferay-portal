/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.util.StringUtil;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalUpstreamJobEntity
	extends BaseJobEntity implements PortalUpstreamJobEntity {

	@Override
	public String getPortalBranchSHA() {
		return getParameterValue("portalBranchSHA");
	}

	@Override
	public URL getPortalBranchURL() {
		String upstreamBranchURL = getParameterValue("portalBranchURL");

		if (StringUtil.isNullOrEmpty(upstreamBranchURL)) {
			return null;
		}

		return StringUtil.toURL(upstreamBranchURL);
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
	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	@Override
	public void setPortalBranchSHA(String portalBranchSHA) {
		setParameterValue("portalBranchSHA", portalBranchSHA);
	}

	@Override
	public void setPortalBranchURL(URL portalBranchURL) {
		setParameterValue("portalBranchURL", String.valueOf(portalBranchURL));
	}

	@Override
	public void setPortalBuildProfile(String portalBuildProfile) {
		setParameterValue("portalBuildProfile", portalBuildProfile);
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