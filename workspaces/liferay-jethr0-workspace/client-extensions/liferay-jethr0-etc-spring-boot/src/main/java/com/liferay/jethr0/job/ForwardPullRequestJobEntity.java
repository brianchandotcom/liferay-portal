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
public class ForwardPullRequestJobEntity extends BaseJobEntity {

	public ForwardPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	public boolean getForwardForce() {
		return getParameterValueBoolean("forwardForce");
	}

	public String getForwardReceiverUserName() {
		return getParameterValue("forwardReceiverUserName");
	}

	@Override
	public String getJenkinsJobName() {
		return "forward-pullrequest";
	}

	public URL getPortalPullRequestURL() {
		return getParameterValueURL("portalPullRequestURL");
	}

	public void setForwardForce(boolean forwardForce) {
		setParameterValueBoolean("forwardForce", forwardForce);
	}

	public void setForwardReceiverUserName(String forwardReceiverUserName) {
		setParameterValue("forwardReceiverUserName", forwardReceiverUserName);
	}

	public void setPortalPullRequestURL(URL portalPullRequestURL) {
		setParameterValueURL("portalPullRequestURL", portalPullRequestURL);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put(
			"CI_FORWARD_FORCE", String.valueOf(getForwardForce()));
		initialBuildParameters.put(
			"CI_FORWARD_RECEIVER_USERNAME", getForwardReceiverUserName());
		initialBuildParameters.put(
			"PULL_REQUEST_URL", String.valueOf(getPortalPullRequestURL()));

		return initialBuildParameters;
	}

}