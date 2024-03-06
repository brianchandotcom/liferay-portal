/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.util.StringUtil;

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
	public URL getPortalPullRequestURL() {
		String portalPullRequestURL = getParameterValue("portalPullRequestURL");

		if (StringUtil.isNullOrEmpty(portalPullRequestURL)) {
			return null;
		}

		return StringUtil.toURL(portalPullRequestURL);
	}

	@Override
	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	@Override
	public void setForwardReceiverUserName(String forwardReceiverUserName) {
		setParameterValue("forwardReceiverUserName", forwardReceiverUserName);
	}

	@Override
	public void setPortalPullRequestURL(URL portalPullRequestURL) {
		setParameterValue(
			"portalPullRequestURL", String.valueOf(portalPullRequestURL));
	}

	@Override
	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	protected BasePortalPullRequestJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

}