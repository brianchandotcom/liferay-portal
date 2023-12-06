/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import java.net.URL;

/**
 * @author Michael Hashimoto
 */
public interface PortalUpstreamJobEntity extends JobEntity {

	public static ParameterDefinition PARAMETER_DEFINITION_BUILD_PROFILE =
		new ParameterDefinition(
			"buildProfile", "Build Profile", ParameterDefinition.Type.STRING,
			"dxp", "e.g. dxp or portal", null);

	public static ParameterDefinition PARAMETER_DEFINITION_TEST_SUITE_NAME =
		new ParameterDefinition(
			"testSuiteName", "Test Suite Name", ParameterDefinition.Type.STRING,
			null, "Insert your Test Suite Name here", null);

	public static ParameterDefinition
		PARAMETER_DEFINITION_UPSTREAM_BRANCH_NAME = new ParameterDefinition(
			"upstreamBranchName", "Upstream Branch Name",
			ParameterDefinition.Type.STRING, "master",
			"Insert your Upstream Branch Name here", null);

	public static ParameterDefinition PARAMETER_DEFINITION_UPSTREAM_BRANCH_SHA =
		new ParameterDefinition(
			"upstreamBranchSHA", "Upstream Branch SHA",
			ParameterDefinition.Type.STRING, null,
			"Insert your Upstream Branch SHA here", null);

	public static ParameterDefinition PARAMETER_DEFINITION_UPSTREAM_BRANCH_URL =
		new ParameterDefinition(
			"upstreamBranchURL", "Upstream Branch URL",
			ParameterDefinition.Type.URL,
			"https://github.com/liferay/liferay-portal/tree/master",
			"e.g. https://github.com/[user]/liferay-portal(-ee)/tree/[name]",
			"https://github.com/[^/]+/liferay-portal(-ee)?/tree/[^/]+");

	public String getBuildProfile();

	public String getTestSuiteName();

	public String getUpstreamBranchName();

	public String getUpstreamBranchSHA();

	public URL getUpstreamBranchURL();

	public void setBuildProfile(String buildProfile);

	public void setTestSuiteName(String testSuiteName);

	public void setUpstreamBranchName(String upstreamBranchName);

	public void setUpstreamBranchSHA(String upstreamBranchSHA);

	public void setUpstreamBranchURL(URL upstreamBranchURL);

}