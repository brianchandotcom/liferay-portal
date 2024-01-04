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
		if (type == JobEntity.Type.ARCHIVE_CI_BUILD_DATA) {
			return new ArchiveCIBuildDataJobDefinition(type);
		}
		else if (type == JobEntity.Type.FILE_PROPAGATOR) {
			return new FilePropagatorJobDefinition(type);
		}
		else if (type == JobEntity.Type.FIXPACK_BUILDER_PULL_REQUEST) {
			return new FixpackBuilderPullRequestJobDefinition(type);
		}
		else if (type == JobEntity.Type.FORWARD_PULL_REQUEST) {
			return new ForwardPullRequestJobDefinition(type);
		}
		else if (type == JobEntity.Type.GENERATE_CI_SYSTEM_HISTORY_REPORT) {
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
		else if (type == JobEntity.Type.JENKINS_PULL_REQUEST) {
			return new JenkinsPullRequestJobDefinition(type);
		}
		else if (type == JobEntity.Type.LIFERAY_BINARIES_CACHE_UPDATER) {
			return new LiferayBinariesCacheUpdaterJobDefinition(type);
		}
		else if (type == JobEntity.Type.MAINTENANCE_DAILY) {
			return new MaintenanceDailyJobDefinition(type);
		}
		else if (type == JobEntity.Type.MAINTENANCE_MATRIX_JOBS) {
			return new MaintenanceMatrixJobsJobDefinition(type);
		}
		else if (type == JobEntity.Type.MAINTENANCE_STALE_ARTIFACTS) {
			return new MaintenanceStaleArtifactsJobDefinition(type);
		}
		else if (type == JobEntity.Type.MAINTENANCE_WEEKLY) {
			return new MaintenanceWeeklyJobDefinition(type);
		}
		else if (type == JobEntity.Type.MAINTENANCE_WEEKLY_NODE) {
			return new MaintenanceWeeklyNodeJobDefinition(type);
		}
		else if (type == JobEntity.Type.MIRRORS_LOCAL_CACHE_PROPAGATOR) {
			return new MirrorsLocalCachePropagatorJobDefinition(type);
		}
		else if (type == JobEntity.Type.PLUGINS_EXTRA_APPS) {
			return new PluginsExtraAppsJobDefinition(type);
		}
		else if (type == JobEntity.Type.PLUGINS_MARKETPLACE_APP) {
			return new PluginsMarketplaceAppsJobDefinition(type);
		}
		else if (type == JobEntity.Type.PLUGINS_PULL_REQUEST) {
			return new PluginsPullRequestJobDefinition(type);
		}
		else if (type == JobEntity.Type.PLUGINS_RELEASE) {
			return new ReleasePluginsJobDefinition(type);
		}
		else if (type == JobEntity.Type.PLUGINS_UPSTREAM) {
			return new UpstreamPluginsJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_APP_RELEASE) {
			return new DefaultJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_FIXPACK_RELEASE) {
			return new PortalFixpackReleaseJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_HOTFIX_RELEASE) {
			return new PortalHotfixReleaseJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_PULL_REQUEST) {
			return new PortalPullRequestJobDefinition(type);
		}
		else if (type == JobEntity.Type.PORTAL_PULL_REQUEST_SF) {
			return new PortalPullRequestSFJobDefinition(type);
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
		else if (type == JobEntity.Type.PUBLISH_PORTAL_DOCKER_IMAGE) {
			return new PublishPortalDockerImageJobDefinition(type);
		}
		else if (type == JobEntity.Type.PUBLISH_TESTRAY_REPORT) {
			return new PublishTestrayReportJobDefinition(type);
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
		else if (type == JobEntity.Type.REPOSITORY_ARCHIVE) {
			return new RepositoryArchiveJobDefinition(type);
		}
		else if (type == JobEntity.Type.SUBREPOSITORY_PULL_REQUEST) {
			return new SubrepositoryPullRequestJobDefinition(type);
		}
		else if (type == JobEntity.Type.VERIFICATION) {
			return new VerificationJobDefinition(type);
		}
		else if (type == JobEntity.Type.VERIFICATION_NODE) {
			return new VerificationNodeJobDefinition(type);
		}

		return new DefaultJobDefinition(type);
	}

}