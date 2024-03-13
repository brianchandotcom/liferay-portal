/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.util.StringUtil;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class PluginsPullRequestJobEntity extends BasePullRequestJobEntity {

	@Override
	public String getJenkinsJobName() {
		return StringUtil.combine(
			"test-plugins-acceptance-pullrequest(", getUpstreamBranchName(),
			")");
	}

	protected PluginsPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"GITHUB_PULL_REQUEST_NUMBER", String.valueOf(getNumber()));
		initialBuildParameters.put(
			"GITHUB_RECEIVER_USERNAME", getReceiverUserName());

		return initialBuildParameters;
	}

}