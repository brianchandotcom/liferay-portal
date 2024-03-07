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
public class JenkinsPullRequestJobEntity
	extends BasePullRequestJobEntity implements PullRequestJobEntity {

	public JenkinsPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	public String getJenkinsJobName() {
		return "test-jenkins-acceptance-pullrequest";
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		return HashMapBuilder.put(
			"BUILD_PRIORITY", String.valueOf(getPriority())
		).put(
			"GITHUB_ORIGIN_NAME", getOriginName()
		).put(
			"GITHUB_PULL_REQUEST_NUMBER", String.valueOf(getNumber())
		).put(
			"GITHUB_RECEIVER_USERNAME", getReceiverUserName()
		).put(
			"GITHUB_SENDER_BRANCH_NAME", getSenderBranchName()
		).put(
			"GITHUB_SENDER_BRANCH_SHA", getSenderBranchSHA()
		).put(
			"GITHUB_SENDER_USERNAME", getSenderUserName()
		).put(
			"GITHUB_UPSTREAM_BRANCH_NAME", getUpstreamBranchName()
		).put(
			"GITHUB_UPSTREAM_BRANCH_SHA", getUpstreamBranchSHA()
		).build();
	}

}