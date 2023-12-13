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
		if (type == JobEntity.Type.GENERATE_CI_SYSTEM_HISTORY_REPORT) {
			return new GenerateCISystemHistoryReportJobDefinition(type);
		}
		else if (type == JobEntity.Type.GENERATE_CI_SYSTEM_STATUS_REPORT) {
			return new GenerateCISystemStatusReportJobDefinition(type);
		}
		else if (type == JobEntity.Type.GENERATE_REPORTS) {
			return new GenerateReportsJobDefinition(type);
		}
		else if (type == JobEntity.Type.GENERATE_TEST_DURATION_METRICS) {
			return new GenerateTestDurationMetricsJobDefinition(type);
		}
		else if (type == JobEntity.Type.GENERATE_TESTRAY_CSV) {
			return new GenerateTestrayCSVJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_PULL_REQUEST) {
			return new PortalPullRequestJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_PULL_REQUEST_SF) {
			return new PortalPullRequestSFJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_FIXPACK_RELEASE) {
			return new PortalFixpackReleaseJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_HOTFIX_RELEASE) {
			return new PortalHotfixReleaseJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_RELEASE) {
			return new PortalReleaseJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_UPSTREAM_ACCEPTANCE) {
			return new PortalUpstreamAcceptanceJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_UPSTREAM_TEST_SUITE) {
			return new PortalUpstreamTestSuiteJobDefinition(type);
		}
		else if (type == JobEntity.Type.POSHI_RELEASE) {
			return new PoshiReleaseJobDefinition(type);
		}
		else if (type == JobEntity.Type.QA_WEBSITES_DAILY) {
			return new DailyQAWebsitesJobDefinition(type);
		}
		else if (type == JobEntity.Type.QA_WEBSITES_PULL_REQUEST_SF) {
			return new QAWebsitesPullRequestSFJobDefinition(type);
		}
		else if (type == JobEntity.Type.QA_WEBSITES_WEEKLY) {
			return new WeeklyQAWebsitesJobDefinition(type);
		}

		return new DefaultJobDefinition(type);
	}

}