/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePullRequestJobEntity
	extends BaseJobEntity implements PullRequestJobEntity {

	@Override
	public String getOriginName() {
		return getParameterValue("originName");
	}

	@Override
	public String getSenderBranchName() {
		return getParameterValue("senderBranchName");
	}

	@Override
	public String getSenderBranchSHA() {
		return getParameterValue("senderBranchSHA");
	}

	@Override
	public String getSenderUserName() {
		return getParameterValue("senderUserName");
	}

	@Override
	public String getUpstreamBranchName() {
		return getParameterValue("upstreamBranchName");
	}

	@Override
	public String getUpstreamBranchSHA() {
		return getParameterValue("upstreamBranchSHA");
	}

	@Override
	public void setOriginName(String originName) {
		setParameterValue("originName", originName);
	}

	@Override
	public void setSenderBranchName(String senderBranchName) {
		setParameterValue("senderBranchName", senderBranchName);
	}

	@Override
	public void setSenderBranchSHA(String senderBranchSHA) {
		setParameterValue("senderBranchSHA", senderBranchSHA);
	}

	@Override
	public void setSenderUserName(String senderUserName) {
		setParameterValue("senderUserName", senderUserName);
	}

	@Override
	public void setUpstreamBranchName(String upstreamBranchName) {
		setParameterValue("upstreamBranchName", upstreamBranchName);
	}

	@Override
	public void setUpstreamBranchSHA(String upstreamBranchSHA) {
		setParameterValue("upstreamBranchSHA", upstreamBranchSHA);
	}

	protected BasePullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

}