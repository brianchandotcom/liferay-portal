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
public class QAWebsitesPullRequestSFJobEntity extends BaseJobEntity {

	public URL getQAWebsitesPullRequestURL() {
		return getParameterValueURL("qaWebsitesPullRequestURL");
	}

	public void setQAWebsitesPullRequestURL(URL qaWebsitesPullRequestURL) {
		setParameterValueURL(
			"qaWebsitesPullRequestURL", qaWebsitesPullRequestURL);
	}

	protected QAWebsitesPullRequestSFJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected String getJenkinsJobName() {
		return "test-qa-websites-source-format";
	}

}