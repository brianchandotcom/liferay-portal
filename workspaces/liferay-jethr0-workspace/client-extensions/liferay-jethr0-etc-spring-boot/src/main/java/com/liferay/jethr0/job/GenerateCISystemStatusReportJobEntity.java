/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class GenerateCISystemStatusReportJobEntity extends BaseJobEntity {

	public String getSlaveLabel() {
		return getParameterValue("slaveLabel");
	}

	public void setSlaveLabel(String slaveLabel) {
		setParameterValue("slaveLabel", slaveLabel);
	}

	protected GenerateCISystemStatusReportJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected String getJenkinsJobName() {
		return "generate-ci-system-status-report";
	}

}