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

	public String getBranchSHA();

	public URL getBranchURL();

	public String getBuildProfile();

	public String getTestSuiteName();

	public String getUpstreamBranchName();

	public void setBranchSHA(String branchSHA);

	public void setBranchURL(URL branchURL);

	public void setBuildProfile(String buildProfile);

	public void setTestSuiteName(String testSuiteName);

	public void setUpstreamBranchName(String upstreamBranchName);

}