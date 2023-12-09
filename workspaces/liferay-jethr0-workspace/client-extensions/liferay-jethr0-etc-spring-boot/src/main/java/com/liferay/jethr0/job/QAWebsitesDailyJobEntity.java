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
public class QAWebsitesDailyJobEntity extends BaseJobEntity {

	public String getQAWebsitesBranchSHA() {
		return getParameterValue("qaWebsitesBranchSHA");
	}

	public URL getQAWebsitesBranchURL() {
		return getParameterValueURL("qaWebsitesBranchURL");
	}

	public String getQAWebsitesProjectName() {
		return getParameterValue("qaWebsitesProjectName");
	}

	public String getQAWebsitesQuery() {
		return getParameterValue("qaWebsitesQuery");
	}

	public String getTestrayProjectName() {
		return getParameterValue("testrayProjectName");
	}

	public String getTestrayRoutineName() {
		return getParameterValue("testrayRoutineName");
	}

	public String getTestSuiteName() {
		return getParameterValue("testSuiteName");
	}

	public void setQAWebsitesBranchSHA(String qaWebsitesBranchSHA) {
		setParameterValue("qaWebsitesBranchSHA", qaWebsitesBranchSHA);
	}

	public void setQAWebsitesBranchURL(URL qaWebsitesBranchURL) {
		setParameterValueURL("qaWebsitesBranchURL", qaWebsitesBranchURL);
	}

	public void setQAWebsitesProjectName(String qaWebsitesProjectName) {
		setParameterValue("qaWebsitesProjectName", qaWebsitesProjectName);
	}

	public void setQAWebsitesQuery(String qaWebsitesQuery) {
		setParameterValue("qaWebsitesQuery", qaWebsitesQuery);
	}

	public void setTestrayProjectName(String testrayProjectName) {
		setParameterValue("testrayProjectName", testrayProjectName);
	}

	public void setTestrayRoutineName(String testrayRoutineName) {
		setParameterValue("testrayRoutineName", testrayRoutineName);
	}

	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	protected QAWebsitesDailyJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected String getJenkinsJobName() {
		return "test-qa-websites-functional-daily";
	}

}