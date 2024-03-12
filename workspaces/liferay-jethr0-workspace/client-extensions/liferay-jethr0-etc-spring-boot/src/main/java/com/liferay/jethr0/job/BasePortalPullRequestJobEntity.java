/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.net.URL;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public abstract class BasePortalPullRequestJobEntity
	extends BasePullRequestJobEntity implements PortalPullRequestJobEntity {

	@Override
	public String getForwardReceiverUserName() {
		return getParameterValue("forwardReceiverUserName");
	}

	@Override
	public String getGitHubGistID() {
		return getParameterValue("gitHubGistID");
	}

	@Override
	public URL getPortalBundlesDistURL() {
		return getParameterValueURL("portalBundlesDistURL");
	}

	@Override
	public void setForwardReceiverUserName(String forwardReceiverUserName) {
		setParameterValue("forwardReceiverUserName", forwardReceiverUserName);
	}

	@Override
	public void setGitHubGistID(String gitHubGistID) {
		setParameterValue("gitHubGistID", gitHubGistID);
	}

	@Override
	public void setPortalBundlesDistURL(URL portalBundlesDistURL) {
		setParameterValueURL("portalBundlesDistURL", portalBundlesDistURL);
	}

	protected BasePortalPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

}