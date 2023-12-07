/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job.definition;

import com.liferay.jethr0.job.JobEntity;

import java.util.Set;
import java.util.TreeSet;

/**
 * @author Michael Hashimoto
 */
public class JobDefinitionFactory {

	public static Set<JobDefinition> getJobDefinitions() {
		Set<JobDefinition> jobDefinitions = new TreeSet<>();

		for (JobEntity.Type type : JobEntity.Type.values()) {
			jobDefinitions.add(newJobDefinition(type));
		}

		return jobDefinitions;
	}

	public static JobDefinition newJobDefinition(JobEntity.Type type) {
		if (type == JobEntity.Type.PORTAL_PULL_REQUEST) {
			return new PortalPullRequestJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_PULL_REQUEST_SF) {
			return new PortalPullRequestSFJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_RELEASE) {
			return new PortalReleaseJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_HOTFIX_RELEASE) {
			return new PortalHotfixReleaseJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_UPSTREAM_ACCEPTANCE) {
			return new PortalUpstreamAcceptanceJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_UPSTREAM_TEST_SUITE) {
			return new PortalUpstreamTestSuiteJobDefinition(type);
		}

		return new DefaultJobDefinition(type);
	}

}