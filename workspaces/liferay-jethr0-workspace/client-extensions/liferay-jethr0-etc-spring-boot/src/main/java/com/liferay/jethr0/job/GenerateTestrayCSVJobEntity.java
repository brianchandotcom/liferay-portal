/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GenerateTestrayCSVJobEntity extends BaseJobEntity {

	public String getJenkinsSlaveLabel() {
		return getParameterValue("jenkinsSlaveLabel");
	}

	public Long getTestrayBuildID() {
		return getParameterValueLong("jenkinsSlaveLabel");
	}

	public void setJenkinsSlaveLabel(String slaveLabel) {
		setParameterValue("jenkinsSlaveLabel", slaveLabel);
	}

	public void setTestrayBuildID(Long testrayBuildID) {
		setParameterValueLong("testrayBuildID", testrayBuildID);
	}

	protected GenerateTestrayCSVJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"JENKINS_GITHUB_URL", String.valueOf(getJenkinsBranchURL()));
		initialBuildParameters.put("SLAVE_LABEL", getJenkinsSlaveLabel());
		initialBuildParameters.put(
			"TESTRAY_BUILD_ID", String.valueOf(getTestrayBuildID()));

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "generate-testray-csv";
	}

}