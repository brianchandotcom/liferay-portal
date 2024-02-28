/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class MergeCentralSubrepositoryJobEntity extends BaseJobEntity {

	public String getPortalUpstreamBranchName() {
		return getParameterValue("portalUpstreamBranchName");
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	protected MergeCentralSubrepositoryJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"PORTAL_UPSTREAM_BRANCH_NAME", getPortalUpstreamBranchName());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "merge-central-subrepository";
	}

}