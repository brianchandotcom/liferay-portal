/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.net.URL;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GenerateTestDurationMetricsJobEntity extends BaseJobEntity {

	public String getBatchNames() {
		return getParameterValue("batchNames");
	}

	public URL getJenkinsBuildURL() {
		return getParameterValueURL("jenkinsBuildURL");
	}

	public String getJenkinsSlaveLabel() {
		return getParameterValue("jenkinsSlaveLabel");
	}

	public void setBatchNames(String batchNames) {
		setParameterValue("batchNames", batchNames);
	}

	public void setJenkinsBuildURL(URL jenkinsBuildURL) {
		setParameterValueURL("jenkinsBuildURL", jenkinsBuildURL);
	}

	public void setJenkinsSlaveLabel(String slaveLabel) {
		setParameterValue("jenkinsSlaveLabel", slaveLabel);
	}

	protected GenerateTestDurationMetricsJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("BATCH_NAMES", getBatchNames());
		initialBuildParameters.put(
			"BUILD_URL", String.valueOf(getJenkinsBuildURL()));
		initialBuildParameters.put("SLAVE_LABEL", getJenkinsSlaveLabel());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "generate-test-duration-metrics";
	}

}