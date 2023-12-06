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
	public String getBranchSHA() {
		return getParameterValue("branchSHA");
	}

	@Override
	public URL getBranchURL() {
		String upstreamBranchURL = getParameterValue("branchURL");

		if (StringUtil.isNullOrEmpty(upstreamBranchURL)) {
			return null;
		}

		return StringUtil.toURL(upstreamBranchURL);
	}

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
	public void setBranchSHA(String branchSHA) {
		setParameterValue("branchSHA", branchSHA);
	}

	@Override
	public void setBranchURL(URL branchURL) {
		setParameterValue("branchURL", String.valueOf(branchURL));
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

	protected BasePortalUpstreamJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

}