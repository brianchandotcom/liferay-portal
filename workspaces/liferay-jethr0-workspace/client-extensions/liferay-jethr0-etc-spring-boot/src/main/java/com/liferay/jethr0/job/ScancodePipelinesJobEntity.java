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
public class ScancodePipelinesJobEntity extends BaseJobEntity {

	public String getDockerTag() {
		return getParameterValue("dockerTag");
	}

	@Override
	public String getJenkinsJobName() {
		return "test-scancode-pipelines";
	}

	public String getJenkinsSlaveLabel() {
		return getParameterValue("jenkinsSlaveLabel");
	}

	public String getPipelineName() {
		return getParameterValue("pipelineName");
	}

	public String getPortalBranchSHA() {
		return getParameterValue("portalBranchSHA");
	}

	public URL getPortalReleaseTomcatURL() {
		return getParameterValueURL("portalReleaseTomcatURL");
	}

	public String getPortalReleaseVersion() {
		return getParameterValue("portalReleaseVersion");
	}

	public String getPortalUserName() {
		return getParameterValue("portalUserName");
	}

	public void setDockerTag(String dockerTag) {
		setParameterValue("dockerTag", dockerTag);
	}

	public void setJenkinsSlaveLabel(String jenkinsSlaveLabel) {
		setParameterValue("jenkinsSlaveLabel", jenkinsSlaveLabel);
	}

	public void setPipelineName(String pipelineName) {
		setParameterValue("pipelineName", pipelineName);
	}

	public void setPortalBranchSHA(String portalBranchSHA) {
		setParameterValue("portalBranchSHA", portalBranchSHA);
	}

	public void setPortalReleaseTomcatURL(URL portalReleaseTomcatURL) {
		setParameterValueURL("portalReleaseTomcatURL", portalReleaseTomcatURL);
	}

	public void setPortalReleaseVersion(String portalReleaseVersion) {
		setParameterValue("portalReleaseVersion", portalReleaseVersion);
	}

	public void setPortalUserName(String portalUserName) {
		setParameterValue("portalUserName", portalUserName);
	}

	protected ScancodePipelinesJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("DOCKER_TAG", getDockerTag());
		initialBuildParameters.put("PIPELINE_NAME", getPipelineName());
		initialBuildParameters.put("SLAVE_LABEL", getJenkinsSlaveLabel());
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_GIT_ID", getPortalBranchSHA());
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_TOMCAT_URL",
			String.valueOf(getPortalReleaseTomcatURL()));
		initialBuildParameters.put(
			"TEST_PORTAL_RELEASE_VERSION", getPortalReleaseVersion());
		initialBuildParameters.put(
			"TEST_PORTAL_USER_NAME", getPortalUserName());

		return initialBuildParameters;
	}

}