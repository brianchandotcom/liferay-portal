/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public interface PortalUpstreamJobEntity {

	public static JobEntity.ParameterDefinition
		PARAMETER_DEFINITION_TEST_SUITE_NAME =
			new JobEntity.ParameterDefinition(
				"testSuiteName", "Test Suite Name",
				JobEntity.ParameterDefinition.Type.STRING, null,
				"Insert your Test Suite Name here", null);

	public static JobEntity.ParameterDefinition
		PARAMETER_DEFINITION_UPSTREAM_BRANCH_NAME =
			new JobEntity.ParameterDefinition(
				"upstreamBranchName", "Upstream Branch Name",
				JobEntity.ParameterDefinition.Type.STRING, "master",
				"Insert your Upstream Branch Name here", null);

	public static JobEntity.ParameterDefinition
		PARAMETER_DEFINITION_UPSTREAM_BRANCH_SHA =
			new JobEntity.ParameterDefinition(
				"upstreamBranchSHA", "Upstream Branch SHA",
				JobEntity.ParameterDefinition.Type.STRING, null,
				"Insert your Upstream Branch SHA here", null);

	public static JobEntity.ParameterDefinition
		PARAMETER_DEFINITION_UPSTREAM_BRANCH_URL =
			new JobEntity.ParameterDefinition(
				"upstreamBranchURL", "Upstream Branch URL",
				JobEntity.ParameterDefinition.Type.URL,
				"https://github.com/liferay/liferay-portal/tree/master",
				"e.g. https://github.com/[user]/liferay-portal(-ee)/tree" +
					"/[name]",
				"https://github.com/[^/]+/liferay-portal(-ee)?/tree/[^/]+");

	public String getTestSuiteName();

	public String getUpstreamBranchName();

	public String getUpstreamBranchSHA();

	public URL getUpstreamBranchURL();

	public void setTestSuiteName(String testSuiteName);

	public void setUpstreamBranchName(String upstreamBranchName);

	public void setUpstreamBranchSHA(String upstreamBranchSHA);

	public void setUpstreamBranchURL(URL upstreamBranchURL);

}