/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.net.URL;

import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class RootCauseAnalysisToolJobEntity extends BaseJobEntity {

	public String getPortalBatchName() {
		return getParameterValue("portalBatchName");
	}

	public String getPortalBatchTestSelector() {
		return getParameterValue("portalBatchTestSelector");
	}

	public String getPortalBranchSHAs() {
		return getParameterValue("portalBranchSHAs");
	}

	public URL getPortalBranchURL() {
		return getParameterValueURL("portalBranchURL");
	}

	public String getPortalCherryPickSHAs() {
		return getParameterValue("portalCherryPickSHAs");
	}

	public String getPortalUpstreamBranchName() {
		return getParameterValue("portalUpstreamBranchName");
	}

	public int getRetestCount() {
		return getParameterValueInteger("retestCount");
	}

	public void setPortalBatchName(String portalBatchName) {
		setParameterValue("portalBatchName", portalBatchName);
	}

	public void setPortalBatchTestSelector(String portalBatchTestSelector) {
		setParameterValue("portalBatchTestSelector", portalBatchTestSelector);
	}

	public void setPortalBranchSHAs(String portalBranchSHAs) {
		setParameterValue("portalBranchSHAs", portalBranchSHAs);
	}

	public void setPortalBranchURL(URL portalBranchURL) {
		setParameterValueURL("portalBranchURL", portalBranchURL);
	}

	public void setPortalCherryPickSHAs(String portalCherryPickSHAs) {
		setParameterValue("portalCherryPickSHAs", portalCherryPickSHAs);
	}

	public void setPortalUpstreamBranchName(String portalUpstreamBranchName) {
		setParameterValue("portalUpstreamBranchName", portalUpstreamBranchName);
	}

	public void setRetestCount(int retestCount) {
		setParameterValueInteger("retestCount", retestCount);
	}

	protected RootCauseAnalysisToolJobEntity(JSONObject jsonObject) {
		super(jsonObject);
	}

	@Override
	protected Map<String, String> getInitialBuildParameters() {
		return HashMapBuilder.put(
			"BUILD_PRIORITY", String.valueOf(getPriority())
		).put(
			"JENKINS_GITHUB_URL", String.valueOf(getJenkinsBranchURL())
		).put(
			"PORTAL_BATCH_NAME", getPortalBatchName()
		).put(
			"PORTAL_BATCH_TEST_SELECTOR", getPortalBatchTestSelector()
		).put(
			"PORTAL_BRANCH_SHAS", getPortalBranchSHAs()
		).put(
			"PORTAL_CHERRY_PICK_SHAS", getPortalCherryPickSHAs()
		).put(
			"PORTAL_GITHUB_URL", String.valueOf(getPortalBranchURL())
		).put(
			"PORTAL_UPSTREAM_BRANCH_NAME", getPortalUpstreamBranchName()
		).put(
			"RETEST_COUNT", String.valueOf(getRetestCount())
		).build();
	}

	@Override
	protected String getJenkinsJobName() {
		return "root-cause-analysis-tool";
	}

}