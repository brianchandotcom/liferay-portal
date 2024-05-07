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

	public URL getOSBAsahBranchURL();

	public String getPortalBranchSHA();

	public URL getPortalBranchURL();

	public String getPortalBuildProfile();

	public String getPortalUpstreamBranchName();

	public String getTestrayBuildName();

	public String getTestrayProjectName();

	public String getTestrayRoutineName();

	public String getTestSuiteName();

	public void setOSBAsahBranchURL(URL osbAsahBranchURL);

	public void setPortalBranchSHA(String portalBranchSHA);

	public void setPortalBranchURL(URL portalBranchURL);

	public void setPortalBuildProfile(String portalBuildProfile);

	public void setTestrayBuildName(String testrayBuildName);

	public void setTestrayProjectName(String testrayProjectName);

	public void setTestrayRoutineName(String testrayRoutineName);

	public void setTestSuiteName(String testSuiteName);

	public void setUpstreamPortalBranchName(String portalUpstreamBranchName);

}