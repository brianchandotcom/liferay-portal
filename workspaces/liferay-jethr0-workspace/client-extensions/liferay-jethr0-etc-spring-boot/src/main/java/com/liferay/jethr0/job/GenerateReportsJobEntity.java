/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GenerateReportsJobEntity extends BaseJobEntity {

	public String getReportNames() {
		return getParameterValue("reportNames");
	}

	public void setReportNames(String reportNames) {
		setParameterValue("reportNames", reportNames);
	}

	protected GenerateReportsJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		return HashMapBuilder.put(
			"BUILD_PRIORITY", String.valueOf(getPriority())
		).put(
			"JENKINS_GITHUB_URL", String.valueOf(getJenkinsBranchURL())
		).put(
			"REPORT_NAMES", getReportNames()
		).build();
	}

	@Override
	protected String getJenkinsJobName() {
		return "generate-reports";
	}

}