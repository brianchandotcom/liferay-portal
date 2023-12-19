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
public class BaseQAWebsitesJobEntity extends BaseJobEntity {

	public String getPoshiQuery() {
		return getParameterValue("poshiQuery");
	}

	public String getQAWebsitesBranchSHA() {
		return getParameterValue("qaWebsitesBranchSHA");
	}

	public URL getQAWebsitesBranchURL() {
		return getParameterValueURL("qaWebsitesBranchURL");
	}

	public String getQAWebsitesProjectName() {
		return getParameterValue("qaWebsitesProjectName");
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

	public void setPoshiQuery(String poshiQuery) {
		setParameterValue("poshiQuery", poshiQuery);
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

	public void setTestrayProjectName(String testrayProjectName) {
		setParameterValue("testrayProjectName", testrayProjectName);
	}

	public void setTestrayRoutineName(String testrayRoutineName) {
		setParameterValue("testrayRoutineName", testrayRoutineName);
	}

	public void setTestSuiteName(String testSuiteName) {
		setParameterValue("testSuiteName", testSuiteName);
	}

	protected BaseQAWebsitesJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		Map<String, String> initialBuildParameters =
			super.getInitialBuildParameters();

		initialBuildParameters.put("CI_TEST_SUITE", getTestSuiteName());
		initialBuildParameters.put("PROJECT_NAMES", getQAWebsitesProjectName());
		initialBuildParameters.put(
			"TEST_QA_WEBSITES_BRANCH_NAME", _getQAWebsitesBranchName());
		initialBuildParameters.put(
			"TEST_QA_WEBSITES_BRANCH_USERNAME", _getQAWebsitesBranchUserName());
		initialBuildParameters.put(
			"TEST_QA_WEBSITES_GIT_ID", getQAWebsitesBranchSHA());
		initialBuildParameters.put(
			"TEST_QA_WEBSITES_PROPERTY_QUERY", getPoshiQuery());

		return initialBuildParameters;
	}

	@Override
	protected String getJenkinsJobName() {
		return "test-qa-websites-functional-daily";
	}

	private String _getQAWebsitesBranchName() {
		return getBranchURLGroupValue(getQAWebsitesBranchURL(), "branchName");
	}

	private String _getQAWebsitesBranchUserName() {
		return getBranchURLGroupValue(getQAWebsitesBranchURL(), "userName");
	}

}