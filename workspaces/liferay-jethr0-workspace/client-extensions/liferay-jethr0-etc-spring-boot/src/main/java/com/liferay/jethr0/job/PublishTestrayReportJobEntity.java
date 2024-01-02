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
public class PublishTestrayReportJobEntity extends BaseJobEntity {

	public URL getJenkinsBuildURL() {
		return getParameterValueURL("jenkinsBuildURL");
	}

	public String getTestrayBuildName() {
		return getParameterValue("testrayBuildName");
	}

	public String getTestrayProductVersion() {
		return getParameterValue("testrayProductVersion");
	}

	public String getTestrayProjectName() {
		return getParameterValue("testrayProjectName");
	}

	public String getTestrayRoutineName() {
		return getParameterValue("testrayRoutineName");
	}

	public String getTestrayServerType() {
		return getParameterValue("testrayServerType");
	}

	public URL getTestrayServerURL() {
		return getParameterValueURL("testrayServerURL");
	}

	public String getTestraySlackChannels() {
		return getParameterValue("testraySlackChannels");
	}

	public String getTestraySlackIconEmoji() {
		return getParameterValue("testraySlackIconEmoji");
	}

	public String getTestraySlackUserName() {
		return getParameterValue("testraySlackUserName");
	}

	public void setJenkinsBuildURL(URL jenkinsBuildURL) {
		setParameterValueURL("jenkinsBuildURL", jenkinsBuildURL);
	}

	public void setTestrayBuildName(String testrayBuildName) {
		setParameterValue("testrayBuildName", testrayBuildName);
	}

	public void setTestrayProductVersion(String testrayProductVersion) {
		setParameterValue("testrayProductVersion", testrayProductVersion);
	}

	public void setTestrayProjectName(String testrayProjectName) {
		setParameterValue("testrayProjectName", testrayProjectName);
	}

	public void setTestrayRoutineName(String testrayRoutineName) {
		setParameterValue("testrayRoutineName", testrayRoutineName);
	}

	public void setTestrayServerType(String testrayServerType) {
		setParameterValue("testrayServerType", testrayServerType);
	}

	public void setTestrayServerURL(URL testrayServerURL) {
		setParameterValueURL("jenkinsBuildURL", testrayServerURL);
	}

	public void setTestraySlackChannels(String testraySlackChannels) {
		setParameterValue("testraySlackChannels", testraySlackChannels);
	}

	public void setTestraySlackIconEmoji(String testraySlackIconEmoji) {
		setParameterValue("testraySlackIconEmoji", testraySlackIconEmoji);
	}

	public void setTestraySlackUserName(String testraySlackUserName) {
		setParameterValue("testraySlackUserName", testraySlackUserName);
	}

	protected PublishTestrayReportJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("TESTRAY_BUILD_NAME", getTestrayBuildName());
		initialBuildParameters.put(
			"TESTRAY_PRODUCT_VERSION_NAME", getTestrayProductVersion());
		initialBuildParameters.put(
			"TESTRAY_PROJECT_NAME", getTestrayProjectName());
		initialBuildParameters.put(
			"TESTRAY_ROUTINE_NAME", getTestrayRoutineName());
		initialBuildParameters.put(
			"TESTRAY_SERVER_TYPE", getTestrayServerType());
		initialBuildParameters.put(
			"TESTRAY_SERVER_URL", String.valueOf(getTestrayServerURL()));
		initialBuildParameters.put(
			"TESTRAY_SLACK_CHANNELS", getTestraySlackChannels());
		initialBuildParameters.put(
			"TESTRAY_SLACK_ICON_EMOJI", getTestraySlackIconEmoji());
		initialBuildParameters.put(
			"TESTRAY_SLACK_USERNAME", getTestraySlackUserName());
		initialBuildParameters.put(
			"TOP_LEVEL_BUILD_URL", String.valueOf(getJenkinsBuildURL()));

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "publish-testray-report";
	}

}