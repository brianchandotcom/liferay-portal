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
public class PortalEvaluatePullRequestJobEntity extends BaseJobEntity {

	public String getJenkinsBuildID() {
		return getParameterValue("jenkinsBuildID");
	}

	public URL getJenkinsBuildURL() {
		return getParameterValueURL("jenkinsBuildURL");
	}

	@Override
	public String getJenkinsJobName() {
		return "test-portal-evaluate-pullrequest";
	}

	public String getJenkinsSlaveLabel() {
		return getParameterValue("jenkinsSlaveLabel");
	}

	public URL getPortalPullRequestURL() {
		return getParameterValueURL("portalPullRequestURL");
	}

	public void setJenkinsBuildID(String jenkinsBuildID) {
		setParameterValue("jenkinsBuildID", jenkinsBuildID);
	}

	public void setJenkinsBuildURL(URL jenkinsBuildURL) {
		setParameterValueURL("jenkinsBuildURL", jenkinsBuildURL);
	}

	public void setJenkinsSlaveLabel(String slaveLabel) {
		setParameterValue("jenkinsSlaveLabel", slaveLabel);
	}

	public void setPortalPullRequestURL(URL portalPullRequestURL) {
		setParameterValueURL("portalPullRequestURL", portalPullRequestURL);
	}

	protected PortalEvaluatePullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("BUILD_ID", getJenkinsBuildID());
		initialBuildParameters.put(
			"ORIGINAL_BUILD_URL", String.valueOf(getJenkinsBuildURL()));
		initialBuildParameters.put(
			"PULL_REQUEST_URL", String.valueOf(getPortalPullRequestURL()));
		initialBuildParameters.put("SLAVE_LABEL", getJenkinsSlaveLabel());

		return initialBuildParameters;
	}

}