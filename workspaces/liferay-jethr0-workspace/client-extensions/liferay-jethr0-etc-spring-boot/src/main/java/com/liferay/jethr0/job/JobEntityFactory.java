/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.jethr0.job;

import com.liferay.jethr0.entity.factory.BaseEntityFactory;

import org.json.JSONObject;

import org.springframework.context.annotation.Configuration;

/**
 * @author Michael Hashimoto
 */
@Configuration
public class JobEntityFactory extends BaseEntityFactory<JobEntity> {

	@Override
	public JobEntity newEntity(JSONObject jsonObject) {
		JobEntity.Type type = JobEntity.Type.get(jsonObject.get("type"));

		if (type == JobEntity.Type.ARCHIVE_CI_BUILD_DATA) {
			return new ArchiveCIBuildDataJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.FILE_PROPAGATOR) {
			return new FilePropagatorJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.FIXPACK_BUILDER_PULL_REQUEST) {
			return new FixpackBuilderPullRequestJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.FORWARD_PULL_REQUEST) {
			return new ForwardPullRequestJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.GENERATE_CI_SYSTEM_HISTORY_REPORT) {
			return new HistoryGenerateCISystemReportJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.GENERATE_CI_SYSTEM_STATUS_REPORT) {
			return new StatusGenerateCISystemReportJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.GENERATE_REPORTS) {
			return new GenerateReportsJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.GENERATE_TEST_DURATION_METRICS) {
			return new GenerateTestDurationMetricsJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.GENERATE_TESTRAY_CSV) {
			return new GenerateTestrayCSVJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.JENKINS_PULL_REQUEST) {
			return new JenkinsPullRequestJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.LEGACY_DATABASE_DUMP) {
			return new LegacyDatabaseDumpJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.LIFERAY_BINARIES_CACHE_UPDATER) {
			return new LiferayBinariesCacheUpdaterJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.MAINTENANCE_DAILY) {
			return new MaintenanceDailyJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.MAINTENANCE_MATRIX_JOBS) {
			return new MaintenanceMatrixJobsJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.MAINTENANCE_STALE_ARTIFACTS) {
			return new MaintenanceStaleArtifactsJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.MAINTENANCE_WEEKLY) {
			return new MaintenanceWeeklyJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.MAINTENANCE_WEEKLY_NODE) {
			return new MaintenanceWeeklyNodeJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.MERGE_CENTRAL_SUBREPOSITORY) {
			return new MergeCentralSubrepositoryJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.MERGE_PORTAL_SUBREPOSITORY) {
			return new MergePortalSubrepositoryJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.MIRRORS_LOCAL_CACHE_PROPAGATOR) {
			return new MirrorsLocalCachePropagatorJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PLUGINS_EXTRA_APPS) {
			return new PluginsExtraAppsJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PLUGINS_MARKETPLACE_APP) {
			return new PluginsMarketplaceAppsJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PLUGINS_PULL_REQUEST) {
			return new PluginsPullRequestJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PLUGINS_RELEASE) {
			return new ReleasePluginsJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PLUGINS_UPSTREAM) {
			return new UpstreamPluginsJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_APP_RELEASE) {
			return new PortalAppReleaseJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_BUILD_OPTIMIZATION) {
			return new PortalBuildOptimizationJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_EVALUATE_PULL_REQUEST) {
			return new PortalEvaluatePullRequestJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_FIXPACK_RELEASE) {
			return new PortalFixpackReleaseJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_HOTFIX_RELEASE) {
			return new PortalHotfixReleaseJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_PULL_REQUEST) {
			return new DefaultPortalPullRequestJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_PULL_REQUEST_SF) {
			return new SFPortalPullRequestJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_RELEASE) {
			return new PortalReleaseJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_UPSTREAM) {
			return new DefaultPortalUpstreamJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_UPSTREAM_ACCEPTANCE) {
			return new AcceptancePortalUpstreamJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PORTAL_UPSTREAM_TEST_SUITE) {
			return new TestSuitePortalUpstreamJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.POSHI_RELEASE) {
			return new PoshiReleaseJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PUBLISH_PORTAL_DOCKER_IMAGE) {
			return new PublishPortalDockerImageJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.PUBLISH_TESTRAY_REPORT) {
			return new PublishTestrayReportJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.QA_WEBSITES_DAILY) {
			return new DailyQAWebsitesJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.QA_WEBSITES_PULL_REQUEST_SF) {
			return new QAWebsitesPullRequestSFJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.QA_WEBSITES_WEEKLY) {
			return new WeeklyQAWebsitesJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.REPOSITORY_ARCHIVE) {
			return new RepositoryArchiveJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.ROOT_CAUSE_ANALYSIS_TOOL) {
			return new RootCauseAnalysisToolJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.SANITIZE_LANGUAGE) {
			return new SanitizeLanguageJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.SCANCODE_PIPELINES) {
			return new ScancodePipelinesJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.SUBREPOSITORY_PULL_REQUEST) {
			return new SubrepositoryPullRequestJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.VERIFICATION) {
			return new VerificationJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.VERIFICATION_NODE) {
			return new VerificationNodeJobEntity(jsonObject);
		}
		else if (type == JobEntity.Type.VERIFICATION_SERVERS_DAILY) {
			return new VerificationServersDailyJobEntity(jsonObject);
		}

		return new DefaultJobEntity(jsonObject);
	}

	protected JobEntityFactory() {
		super(JobEntity.class);
	}

}