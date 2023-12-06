/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PortalAppReleaseJobEntity extends BaseJobEntity {

	public static List<ParameterDefinition> getParameterDefinitions() {
		return Arrays.asList(PARAMETER_DEFINITION_JENKINS_GITHUB_URL);
	}

	@Override
	public String getJenkinsJobName() {
		return "test-portal-app-release";
	}

	protected PortalAppReleaseJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

}