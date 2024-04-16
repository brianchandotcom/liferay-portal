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
import com.liferay.testray.rest.dto.v1_0.TestrayCaseTypeMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayComponentMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayRunMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayStatusMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayTeamMetric;
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
	public Page<TestrayCaseTypeMetric>
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayCaseTypesMetricsPage(
				Long testrayBuildId, String testrayCasePriorities,
				Long testrayTeamId, Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(22);

		sb.append("select ct.c_caseTypeId_, ct.name_, COUNT(cr.dueStatus_) ");
		sb.append("as TOTAL, SUM(CASE WHEN cr.dueStatus_ = 'BLOCKED' THEN 1 ");
		sb.append("ELSE 0 END) as BLOCKED, SUM(CASE WHEN cr.dueStatus_ =  ");
		sb.append("'FAILED' THEN 1 ELSE 0 END) as FAILED, ");
		sb.append("SUM(CASE WHEN cr.dueStatus_ = 'INPROGRESS' THEN 1 ELSE 0 ");
		sb.append("END) as INPROGRESS, SUM(CASE WHEN cr.dueStatus_ = ");
		sb.append("'PASSED' THEN 1 ELSE 0 END) as PASSED, SUM(CASE WHEN ");
		sb.append("cr.dueStatus_ = 'TESTFIX' THEN 1 ELSE 0 END) as TESTFIX, ");
		sb.append("SUM(CASE WHEN cr.dueStatus_ = 'UNTESTED' THEN 1 ELSE 0 ");
		sb.append("END) as UNTESTED FROM O_[%COMPANY_ID%]_Build b, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr, O_[%COMPANY_ID%]_Case c, ");
		sb.append("O_[%COMPANY_ID%]_CaseType ct, O_[%COMPANY_ID%]_Component ");
		sb.append("co WHERE b.c_buildId_ = ? AND b.c_buildId_ = ");
		sb.append("cr.r_buildToCaseResult_c_buildId AND ");
		sb.append("cr.r_caseToCaseResult_c_caseId = c.c_caseId_ AND ");
		sb.append("c.r_caseTypeToCases_c_caseTypeId = ct.c_caseTypeId_ AND ");
		sb.append("c.r_componentToCases_c_componentId = co.c_componentId_ ");

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

		if (Validator.isNotNull(testrayTeamId)) {
			sb.append("AND t.c_teamId_ = ? ");
			params.add(testrayTeamId);
		}

		sb.append("GROUP BY ct.c_caseTypeId_, ct.name_ ORDER BY ct.name_ ASC ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " LIMIT ? OFFSET ?";

		params.add(pagination.getPageSize());
		params.add(pagination.getStartPosition());

		List<Map<String, Object>> values = TestrayUtil.runSQL(sql, params);

		return Page.of(
			transform(
				values,
				value -> {
					TestrayCaseTypeMetric testrayCaseTypeMetric =
						new TestrayCaseTypeMetric();

					testrayCaseTypeMetric.setTestrayCaseTypeId(
						GetterUtil.getLong(value.get("c_caseTypeId_")));
					testrayCaseTypeMetric.setTestrayCaseTypeName(
						GetterUtil.getString(value.get("name_")));
					testrayCaseTypeMetric.setTestrayStatusMetric(
						_getTestrayStatusMetric(value));

					return testrayCaseTypeMetric;
				}),
			pagination, totalCount);
	}

	@Override
	public Page<TestrayComponentMetric>
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayComponentsMetricsPage(
				Long testrayBuildId, String testrayCasePriorities,
				String testrayCaseTypes, Long testrayTeamId,
				Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(22);

		sb.append("select co.c_componentId_, co.name_, COUNT(cr.dueStatus_) ");
		sb.append("as TOTAL, SUM(CASE WHEN cr.dueStatus_ = 'BLOCKED' THEN 1 ");
		sb.append("ELSE 0 END) as BLOCKED, SUM(CASE WHEN cr.dueStatus_ =  ");
		sb.append("'FAILED' THEN 1 ELSE 0 END) as FAILED, ");
		sb.append("SUM(CASE WHEN cr.dueStatus_ = 'INPROGRESS' THEN 1 ELSE 0 ");
		sb.append("END) as INPROGRESS, SUM(CASE WHEN cr.dueStatus_ = ");
		sb.append("'PASSED' THEN 1 ELSE 0 END) as PASSED, SUM(CASE WHEN ");
		sb.append("cr.dueStatus_ = 'TESTFIX' THEN 1 ELSE 0 END) as TESTFIX, ");
		sb.append("SUM(CASE WHEN cr.dueStatus_ = 'UNTESTED' THEN 1 ELSE 0 ");
		sb.append("END) as UNTESTED FROM O_[%COMPANY_ID%]_Build b, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr, O_[%COMPANY_ID%]_Case c, ");
		sb.append("O_[%COMPANY_ID%]_Component co WHERE b.c_buildId_ = ? AND ");
		sb.append("b.c_buildId_  = cr.r_buildToCaseResult_c_buildId AND ");
		sb.append("cr.r_caseToCaseResult_c_caseId = c.c_caseId_ AND ");
		sb.append("c.r_componentToCases_c_componentId = co.c_componentId_ ");

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
			sb.append("AND t.c_teamId_ = ? ");
			params.add(testrayTeamId);
		}

		sb.append(
			"GROUP BY co.c_componentId_, co.name_ ORDER BY co.name_ ASC ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " LIMIT ? OFFSET ?";

		params.add(pagination.getPageSize());
		params.add(pagination.getStartPosition());

		List<Map<String, Object>> values = TestrayUtil.runSQL(sql, params);

		return Page.of(
			transform(
				values,
				value -> {
					TestrayComponentMetric testrayComponentMetric =
						new TestrayComponentMetric();

					testrayComponentMetric.setTestrayComponentId(
						GetterUtil.getLong(value.get("c_componentId_")));
					testrayComponentMetric.setTestrayComponentName(
						GetterUtil.getString(value.get("name_")));
					testrayComponentMetric.setTestrayStatusMetric(
						_getTestrayStatusMetric(value));

					return testrayComponentMetric;
				}),
			pagination, totalCount);
	}

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

		sb.append("GROUP BY r.c_runId_, r.name_ ORDER BY r.number_ ASC ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " LIMIT ? OFFSET ?";

		params.add(pagination.getPageSize());
		params.add(pagination.getStartPosition());

		List<Map<String, Object>> values = TestrayUtil.runSQL(sql, params);

		return Page.of(
			transform(
				values,
				value -> {
					TestrayRunMetric testrayRunMetric = new TestrayRunMetric();

					testrayRunMetric.setTestrayRunId(
						GetterUtil.getLong(value.get("c_runId_")));
					testrayRunMetric.setTestrayRunName(
						GetterUtil.getString(value.get("name_")));
					testrayRunMetric.setTestrayRunNumber(
						GetterUtil.getLong(value.get("number_")));
					testrayRunMetric.setTestrayStatusMetric(
						_getTestrayStatusMetric(value));

					return testrayRunMetric;
				}),
			pagination, totalCount);
	}

	@Override
	public Page<TestrayTeamMetric>
			getTestrayStatusMetricByTestrayBuildIdTestrayBuildTestrayTeamsMetricsPage(
				Long testrayBuildId, String testrayCasePriorities,
				String testrayCaseTypes, Long testrayRunId, Long testrayTeamId,
				Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(21);

		sb.append("select t.c_teamId_ , t.name_, COUNT(cr.dueStatus_) as ");
		sb.append("TOTAL, SUM(CASE WHEN cr.dueStatus_ = 'BLOCKED' THEN 1 ");
		sb.append("ELSE 0 END) as BLOCKED, SUM(CASE WHEN cr.dueStatus_ = ");
		sb.append("'FAILED' THEN 1 ELSE 0 END) as FAILED, SUM(CASE WHEN ");
		sb.append("cr.dueStatus_ = 'INPROGRESS' THEN 1 ELSE 0 END) as ");
		sb.append("INPROGRESS, SUM(CASE WHEN cr.dueStatus_ = 'PASSED' THEN 1 ");
		sb.append("ELSE 0 END) as PASSED, SUM(CASE WHEN cr.dueStatus_ = ");
		sb.append("'TESTFIX' THEN 1 ELSE 0 END) as TESTFIX, SUM(CASE WHEN ");
		sb.append("cr.dueStatus_ = 'UNTESTED' THEN 1 ELSE 0 END) as UNTESTED ");
		sb.append("FROM O_[%COMPANY_ID%]_Build b, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr, O_[%COMPANY_ID%]_Case c, ");
		sb.append("O_[%COMPANY_ID%]_Component co, O_[%COMPANY_ID%]_Team t ");
		sb.append("WHERE b.c_buildId_ = ? AND b.c_buildId_ = ");
		sb.append("cr.r_buildToCaseResult_c_buildId AND ");
		sb.append("cr.r_caseToCaseResult_c_caseId = c.c_caseId_ ");

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

		if (Validator.isNotNull(testrayRunId)) {
			sb.append("AND cr.r_runToCaseResult_c_runId = ? ");
			params.add(testrayRunId);
		}

		if (Validator.isNotNull(testrayTeamId)) {
			sb.append("AND t.c_teamId_ = ? ");
			params.add(testrayTeamId);
		}

		sb.append("AND cr.r_componentToCaseResult_c_componentId  = ");
		sb.append("co.c_componentId_ AND co.r_teamToComponents_c_teamId = ");
		sb.append("t.c_teamId_ GROUP BY t.name_, t.c_teamId_ ORDER BY ");
		sb.append("t.name_ ASC ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " LIMIT ? OFFSET ?";

		params.add(pagination.getPageSize());
		params.add(pagination.getStartPosition());

		List<Map<String, Object>> values = TestrayUtil.runSQL(sql, params);

		return Page.of(
			transform(
				values,
				value -> {
					TestrayTeamMetric testrayTeamMetric =
						new TestrayTeamMetric();

					testrayTeamMetric.setTestrayTeamId(
						GetterUtil.getLong(value.get("c_teamId_")));
					testrayTeamMetric.setTestrayTeamName(
						GetterUtil.getString(value.get("name_")));
					testrayTeamMetric.setTestrayStatusMetric(
						_getTestrayStatusMetric(value));

					return testrayTeamMetric;
				}),
			pagination, totalCount);
	}

	private TestrayStatusMetric _getTestrayStatusMetric(
		Map<String, Object> map) {

		TestrayStatusMetric testrayStatusMetric = new TestrayStatusMetric();

		testrayStatusMetric.setBlocked(GetterUtil.getLong(map.get("BLOCKED")));
		testrayStatusMetric.setFailed(GetterUtil.getLong(map.get("FAILED")));
		testrayStatusMetric.setInProgress(
			GetterUtil.getLong(map.get("INPROGRESS")));
		testrayStatusMetric.setPassed(GetterUtil.getLong(map.get("PASSED")));
		testrayStatusMetric.setTestfix(GetterUtil.getLong(map.get("TESTFIX")));
		testrayStatusMetric.setTotal(GetterUtil.getLong(map.get("TOTAL")));
		testrayStatusMetric.setUntested(
			GetterUtil.getLong(map.get("UNTESTED")));

		return testrayStatusMetric;
	}

}