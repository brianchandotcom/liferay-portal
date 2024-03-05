/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URL;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GenerateTestrayCSVJobEntity extends BaseJobEntity {

	public String getJenkinsSlaveLabel() {
		return getParameterValue("jenkinsSlaveLabel");
	}

	public URL getPortalPullRequestURL() {
		return getParameterValueURL("portalPullRequestURL");
	}

	public Long getTestrayBuildID() {
		return getParameterValueLong("testrayBuildID");
	}

	public void setJenkinsSlaveLabel(String jenkinsSlaveLabel) {
		setParameterValue("jenkinsSlaveLabel", jenkinsSlaveLabel);
	}

	public void setPortalPullRequestURL(URL portalPullRequestURL) {
		setParameterValueURL("portalPullRequestURL", portalPullRequestURL);
	}

	public void setTestrayBuildID(Long testrayBuildID) {
		setParameterValueLong("testrayBuildID", testrayBuildID);
	}

	protected GenerateTestrayCSVJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		return HashMapBuilder.put(
			"BUILD_PRIORITY", String.valueOf(getPriority())
		).put(
			"JENKINS_GITHUB_URL", String.valueOf(getJenkinsBranchURL())
		).put(
			"PULL_REQUEST_URL", String.valueOf(getPortalPullRequestURL())
		).put(
			"SLAVE_LABEL", getJenkinsSlaveLabel()
		).put(
			"TESTRAY_BUILD_ID", String.valueOf(getTestrayBuildID())
		).build();
	}

	@Override
	protected String getJenkinsJobName() {
		return "generate-testray-csv";
	}

}