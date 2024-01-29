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
public class MergePortalSubrepositoryJobEntity extends BaseJobEntity {

	public URL getPortalPullRequestURL() {
		return getParameterValueURL("portalPullRequestURL");
	}

	public String getSubrepositoryBranchSHA() {
		return getParameterValue("subrepositoryBranchSHA");
	}

	public URL getSubrepositoryBranchURL() {
		return getParameterValueURL("subrepositoryBranchURL");
	}

	public String getSubrepositoryUpstreamBranchName() {
		return getParameterValue("subrepositoryUpstreamBranchName");
	}

	public void setPortalPullRequestURL(URL portalPullRequestURL) {
		setParameterValueURL("portalPullRequestURL", portalPullRequestURL);
	}

	public void setSubrepositoryBranchSHA(String subrepositoryBranchSHA) {
		setParameterValue("subrepositoryBranchSHA", subrepositoryBranchSHA);
	}

	public void setSubrepositoryBranchURL(URL subrepositoryBranchURL) {
		setParameterValueURL("subrepositoryBranchURL", subrepositoryBranchURL);
	}

	public void setSubrepositoryUpstreamBranchName(
		String subrepositoryUpstreamBranchName) {

		setParameterValue(
			"subrepositoryUpstreamBranchName", subrepositoryUpstreamBranchName);
	}

	protected MergePortalSubrepositoryJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"PORTAL_PULL_REQUEST_URL",
			String.valueOf(getPortalPullRequestURL()));
		initialBuildParameters.put(
			"SUBREPOSITORY_GITHUB_URL",
			String.valueOf(getSubrepositoryBranchURL()));
		initialBuildParameters.put(
			"SUBREPOSITORY_UPSTREAM_BRANCH_NAME",
			getSubrepositoryUpstreamBranchName());
		initialBuildParameters.put(
			"TARGET_GIT_REPO_COMMIT_SHA", getSubrepositoryBranchSHA());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "merge-portal-subrepository";
	}

}