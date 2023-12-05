/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public interface PortalPullRequestJobEntity extends JobEntity {

	public static ParameterDefinition PARAMETER_DEFINITION_PORTAL_PULL_REQUEST =
		new ParameterDefinition(
			"portalPullRequestURL", "Portal Pull Request URL",
			ParameterDefinition.Type.URL, null,
			"e.g. https://github.com/[user]/liferay-portal/pull/[number]",
			"https://github.com/[^/]+/liferay-portal(-ee)?/pull/[^/]+");

	public static ParameterDefinition PARAMETER_DEFINITION_TEST_SUITE_NAME =
		new ParameterDefinition(
			"testSuiteName", "Test Suite Name", ParameterDefinition.Type.STRING,
			null, "Insert your Test Suite Name here", null);

	public URL getJenkinsGitHubURL();

	public String getOriginName();

	public URL getPortalPullRequestURL();

	public String getSenderBranchName();

	public String getSenderBranchSHA();

	public String getSenderUserName();

	public String getTestSuiteName();

	public String getUpstreamBranchName();

	public String getUpstreamBranchSHA();

	public void setJenkinsGitHubURL(URL jenkinsGitHubURL);

	public void setOriginName(String originName);

	public void setPortalPullRequestURL(URL portalPullRequestURL);

	public void setSenderBranchName(String senderBranchName);

	public void setSenderBranchSHA(String senderBranchSHA);

	public void setSenderUserName(String senderUserName);

	public void setTestSuiteName(String testSuiteName);

	public void setUpstreamBranchName(String upstreamBranchName);

	public void setUpstreamBranchSHA(String upstreamBranchSHA);

}