/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.testray.rest.dto.v1_0.TestrayRunMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayStatusMetric;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.resource.v1_0.TestrayStatusMetricResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	public Page<TestrayRunMetric>
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayRunsMetricsPage(
				Long testrayBuildId, String testrayCasePriorities,
				String testrayCaseTypes, Long testrayTeamId,
				Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(22);

		sb.append("select r.c_runId_, r.name_, r.number_, ");
		sb.append("COUNT(cr.dueStatus_) as TOTAL, SUM(CASE WHEN ");
		sb.append("cr.dueStatus_ = 'BLOCKED' THEN 1 ELSE 0 END) as BLOCKED,");
		sb.append(" SUM(CASE WHEN cr.dueStatus_ = 'FAILED' THEN 1 ELSE 0 END)");
		sb.append(" as FAILED, SUM(CASE WHEN cr.dueStatus_ = 'INPROGRESS' ");
		sb.append("THEN 1 ELSE 0 END) as INPROGRESS, SUM(CASE WHEN ");
		sb.append("cr.dueStatus_ = 'PASSED' THEN 1 ELSE 0 END) as PASSED, ");
		sb.append("SUM(CASE WHEN cr.dueStatus_ = 'TESTFIX' THEN 1 ELSE 0 ");
		sb.append("END) as TESTFIX, SUM(CASE WHEN cr.dueStatus_ = 'UNTESTED' ");
		sb.append("THEN 1 ELSE 0 END) as UNTESTED FROM ");
		sb.append("O_[%COMPANY_ID%]_Build b, O_[%COMPANY_ID%]_Run r, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr, O_[%COMPANY_ID%]_Case c, ");
		sb.append("O_[%COMPANY_ID%]_Component co WHERE b.c_buildId_  = ? AND ");
		sb.append("b.c_buildId_  = r.r_buildToRuns_c_buildId AND ");
		sb.append("cr.r_runToCaseResult_c_runId = r.c_runId_ AND ");
		sb.append("cr.r_caseToCaseResult_c_caseId = c.c_caseId_ AND ");
		sb.append("cr.r_componentToCaseResult_c_componentId = ");
		sb.append("co.c_componentId_ ");

		List<Object> params = new ArrayList<>();

		params.add(testrayBuildId);

		if (Validator.isNotNull(testrayCasePriorities)) {
			String[] testrayCasePrioritiesArray = StringUtil.split(
				testrayCasePriorities);

			sb.append("AND c.priority_ IN (");

			for (String testrayCasePriority : testrayCasePrioritiesArray) {
				sb.append("? ");
				sb.append(", ");
				params.add(testrayCasePriority);
			}

			sb.setIndex(sb.index() - 1);
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayCaseTypes)) {
			String[] testrayCaseTypesArray = StringUtil.split(testrayCaseTypes);

			sb.append("AND c.r_caseTypeToCases_c_caseTypeId IN (");

			for (String testrayCaseType : testrayCaseTypesArray) {
				sb.append("? ");
				sb.append(", ");
				params.add(testrayCaseType);
			}

			sb.setIndex(sb.index() - 1);
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayTeamId)) {
			sb.append("AND co.r_teamToComponents_c_teamId  = ? ");
			params.add(testrayTeamId);
		}

		sb.append("GROUP BY r.c_runId_, r.name_ ORDER BY r.number_ ASC LIMIT ");
		sb.append("? OFFSET ?");

		params.add(pagination.getPageSize());
		params.add(pagination.getStartPosition());

		List<Map<String, Object>> values = TestrayUtil.runSQL(
			StringUtil.replace(
				sb.toString(), "[%COMPANY_ID%]",
				String.valueOf(contextCompany.getCompanyId())),
			params);

		return Page.of(
			transform(
				values,
				value -> {
					TestrayStatusMetric testrayStatusMetric =
						new TestrayStatusMetric();

					testrayStatusMetric.setBlocked(
						GetterUtil.getLong(value.get("BLOCKED")));
					testrayStatusMetric.setFailed(
						GetterUtil.getLong(value.get("FAILED")));
					testrayStatusMetric.setInProgress(
						GetterUtil.getLong(value.get("INPROGRESS")));
					testrayStatusMetric.setPassed(
						GetterUtil.getLong(value.get("PASSED")));
					testrayStatusMetric.setTestfix(
						GetterUtil.getLong(value.get("TESTFIX")));
					testrayStatusMetric.setTotal(
						GetterUtil.getLong(value.get("TOTAL")));
					testrayStatusMetric.setUntested(
						GetterUtil.getLong(value.get("UNTESTED")));

					TestrayRunMetric testrayRunMetric = new TestrayRunMetric();

					testrayRunMetric.setTestrayRunId(
						GetterUtil.getLong(value.get("c_runId_")));
					testrayRunMetric.setTestrayRunName(
						GetterUtil.getString(value.get("name_")));
					testrayRunMetric.setTestrayRunNumber(
						GetterUtil.getLong(value.get("number_")));
					testrayRunMetric.setTestrayStatusMetric(
						testrayStatusMetric);

					return testrayRunMetric;
				}));
	}

}