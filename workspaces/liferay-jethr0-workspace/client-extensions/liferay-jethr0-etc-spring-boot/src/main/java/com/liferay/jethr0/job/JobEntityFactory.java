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
		JSONObject typeJSONObject = jsonObject.getJSONObject("type");

		JobEntity.Type jobEntityType = JobEntity.Type.getByKey(
			typeJSONObject.getString("key"));

		if (jobEntityType == JobEntity.Type.GENERATE_CI_SYSTEM_HISTORY_REPORT) {
			return new HistoryGenerateCISystemReportJobEntity(jsonObject);
		}
		else if (jobEntityType ==
					JobEntity.Type.GENERATE_CI_SYSTEM_STATUS_REPORT) {

			return new StatusGenerateCISystemReportJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.GENERATE_REPORTS) {
			return new GenerateReportsJobEntity(jsonObject);
		}
		else if (jobEntityType ==
					JobEntity.Type.GENERATE_TEST_DURATION_METRICS) {

			return new GenerateTestDurationMetricsJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.GENERATE_TESTRAY_CSV) {
			return new GenerateTestrayCSVJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.PORTAL_APP_RELEASE) {
			return new PortalAppReleaseJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.PORTAL_FIXPACK_RELEASE) {
			return new PortalFixpackReleaseJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.PORTAL_HOTFIX_RELEASE) {
			return new PortalHotfixReleaseJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.PORTAL_PULL_REQUEST) {
			return new DefaultPortalPullRequestJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.PORTAL_PULL_REQUEST_SF) {
			return new SFPortalPullRequestJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.PORTAL_RELEASE) {
			return new PortalReleaseJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.PORTAL_UPSTREAM_ACCEPTANCE) {
			return new AcceptancePortalUpstreamJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.PORTAL_UPSTREAM_TEST_SUITE) {
			return new TestSuitePortalUpstreamJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.POSHI_RELEASE) {
			return new PoshiReleaseJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.QA_WEBSITES_DAILY) {
			return new DailyQAWebsitesJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.QA_WEBSITES_PULL_REQUEST_SF) {
			return new QAWebsitesPullRequestSFJobEntity(jsonObject);
		}
		else if (jobEntityType == JobEntity.Type.QA_WEBSITES_WEEKLY) {
			return new WeeklyQAWebsitesJobEntity(jsonObject);
		}

		return new DefaultJobEntity(jsonObject);
	}

	protected JobEntityFactory() {
		super(JobEntity.class);
	}

}