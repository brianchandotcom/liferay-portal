/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

/**
 * @author Michael Hashimoto
 */
public class JobParameterDefinitionFactory {

	public static JobParameterDefinition newJobParameterDefinition(
		JobParameterDefinition.Type type) {

		if (type == JobParameterDefinition.Type.JENKINS_BRANCH_URL) {
			return new JenkinsBranchURLJobParameterDefinition();
		}
		else if (type == JobParameterDefinition.Type.PORTAL_BRANCH_SHA) {
			return new PortalBranchSHAJobParameterDefinition();
		}
		else if (type == JobParameterDefinition.Type.PORTAL_BRANCH_URL) {
			return new PortalBranchURLJobParameterDefinition();
		}
		else if (type == JobParameterDefinition.Type.PORTAL_BUILD_PROFILE) {
			return new PortalBuildProfileJobParameterDefinition();
		}
		else if (type == JobParameterDefinition.Type.PORTAL_PULL_REQUEST_URL) {
			return new PortalPullRequestURLJobParameterDefinition();
		}
		else if (type ==
					JobParameterDefinition.Type.PORTAL_UPSTREAM_BRANCH_NAME) {

			return new PortalUpstreamBranchNameJobParameterDefinition();
		}
		else if (type == JobParameterDefinition.Type.TEST_SUITE_NAME) {
			return new TestSuiteNameJobParameterDefinition();
		}

		return null;
	}

}