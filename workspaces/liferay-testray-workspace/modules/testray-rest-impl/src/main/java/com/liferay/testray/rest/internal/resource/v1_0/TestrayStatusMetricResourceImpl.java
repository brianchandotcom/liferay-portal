/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.testray.rest.dto.v1_0.TestrayCaseTypeMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayRunMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayTeamMetric;
import com.liferay.testray.rest.resource.v1_0.TestrayStatusMetricResource;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/testray-status-metric.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayStatusMetricResource.class
)
public class TestrayStatusMetricResourceImpl
	extends BaseTestrayStatusMetricResourceImpl {

	@Override
	public Page<TestrayCaseTypeMetric>
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayCaseTypesMetricsPage(
				Long testrayBuildId, String testrayCasePriorities,
				Long testrayTeamId, Pagination pagination)
		throws Exception {

		return super.
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayCaseTypesMetricsPage(
				testrayBuildId, testrayCasePriorities, testrayTeamId,
				pagination);
	}

	@Override
	public Page<TestrayTeamMetric>
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayComponentsMetricsPage(
				Long testrayBuildId, String testrayCasePriorities,
				String testrayCaseTypes, Long testrayTeamId,
				Pagination pagination)
		throws Exception {

		return super.
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayComponentsMetricsPage(
				testrayBuildId, testrayCasePriorities, testrayCaseTypes,
				testrayTeamId, pagination);
	}

	@Override
	public Page<TestrayRunMetric>
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayRunsMetricsPage(
				Long testrayBuildId, String testrayCasePriorities,
				String testrayCaseTypes, Long testrayTeamId,
				Pagination pagination)
		throws Exception {

		return super.
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayRunsMetricsPage(
				testrayBuildId, testrayCasePriorities, testrayCaseTypes,
				testrayTeamId, pagination);
	}

	@Override
	public Page<TestrayTeamMetric>
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayTeamsMetricsPage(
				Long testrayBuildId, String testrayCasePriorities,
				String testrayCaseTypes, Integer testrayRunNumber,
				Long testrayTeamId, Pagination pagination)
		throws Exception {

		return super.
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayTeamsMetricsPage(
				testrayBuildId, testrayCasePriorities, testrayCaseTypes,
				testrayRunNumber, testrayTeamId, pagination);
	}

}