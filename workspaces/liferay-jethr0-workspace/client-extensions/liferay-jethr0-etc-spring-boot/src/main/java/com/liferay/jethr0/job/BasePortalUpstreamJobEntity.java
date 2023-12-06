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
	public String getBuildProfile() {
		return getParameterValue("buildProfile");
	}

	@Override
	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	@Override
	public String getUpstreamBranchName() {
		return getParameterValue("upstreamBranchName");
	}

	@Override
	public String getUpstreamBranchSHA() {
		return getParameterValue("upstreamBranchSHA");
	}

	@Override
	public URL getUpstreamBranchURL() {
		String upstreamBranchURL = getParameterValue("upstreamBranchURL");

		if (StringUtil.isNullOrEmpty(upstreamBranchURL)) {
			return null;
		}

		return StringUtil.toURL(upstreamBranchURL);
	}

	@Override
	public void setBuildProfile(String buildProfile) {
		setParameterValue("buildProfile", buildProfile);
	}

	@Override
	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	@Override
	public void setUpstreamBranchName(String upstreamBranchName) {
		setParameterValue("upstreamBranchName", upstreamBranchName);
	}

	@Override
	public void setUpstreamBranchSHA(String upstreamBranchSHA) {
		setParameterValue("upstreamBranchSHA", upstreamBranchSHA);
	}

	@Override
	public void setUpstreamBranchURL(URL upstreamBranchURL) {
		setParameterValue(
			"upstreamBranchName", String.valueOf(upstreamBranchURL));
	}

	protected BasePortalUpstreamJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

}