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
import com.liferay.testray.rest.dto.v1_0.TestrayRoutineMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayRunMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayStatusMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayTeamMetric;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.resource.v1_0.TestrayStatusMetricResource;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import java.util.ArrayList;
import java.util.Date;
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
				String testrayTeamIds, Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(24);

		sb.append("select ct.c_caseTypeId_, ct.name_, count(cr.dueStatus_) ");
		sb.append("as total, sum(case when cr.dueStatus_ = 'blocked' then 1 ");
		sb.append("else 0 end) as blocked, sum(case when cr.dueStatus_ =  ");
		sb.append("'failed' then 1 else 0 end) as failed, sum(case when ");
		sb.append("cr.dueStatus_ = 'inprogress' then 1 else 0 end) as ");
		sb.append("inprogress, sum(case when cr.dueStatus_ = 'passed' then 1 ");
		sb.append("else 0 end) as passed, sum(case when cr.dueStatus_ = ");
		sb.append("'testfix' then 1 else 0 end) as testfix, sum(case when ");
		sb.append("cr.dueStatus_ = 'untested' then 1 else 0 end) as untested ");
		sb.append("from O_[%COMPANY_ID%]_Build b, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr, O_[%COMPANY_ID%]_Case c, ");
		sb.append("O_[%COMPANY_ID%]_CaseType ct, O_[%COMPANY_ID%]_Component ");
		sb.append("co where b.c_buildId_ = ? and b.c_buildId_ = ");
		sb.append("cr.r_buildToCaseResult_c_buildId and ");
		sb.append("cr.r_caseToCaseResult_c_caseId = c.c_caseId_ and ");
		sb.append("c.r_caseTypeToCases_c_caseTypeId = ct.c_caseTypeId_ and ");
		sb.append("c.r_componentToCases_c_componentId = co.c_componentId_ ");

		List<Object> params = new ArrayList<>();

		params.add(testrayBuildId);

		if (Validator.isNotNull(testrayCasePriorities)) {
			sb.append("and c.priority_ in (");
			sb.append(_interpolateParams(params, testrayCasePriorities));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and co.r_teamToComponents_c_teamId in (");
			sb.append(_interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		sb.append("group by ct.c_caseTypeId_, ct.name_ order by ct.name_ asc ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " limit ? offset ?";

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
				String testrayCaseTypes, String testrayTeamIds,
				Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(25);

		sb.append("select co.c_componentId_, co.name_, count(cr.dueStatus_) ");
		sb.append("as total, sum(case when cr.dueStatus_ = 'blocked' then 1 ");
		sb.append("else 0 end) as blocked, sum(case when cr.dueStatus_ =  ");
		sb.append("'failed' then 1 else 0 end) as failed, sum(case when ");
		sb.append("cr.dueStatus_ = 'inprogress' then 1 else 0 end) as  ");
		sb.append("inprogress, sum(case when cr.dueStatus_ = 'passed' then 1 ");
		sb.append("else 0 end) as passed, sum(case when cr.dueStatus_ = ");
		sb.append("'testfix' then 1 else 0 end) as testfix, sum(case when ");
		sb.append("cr.dueStatus_ = 'untested' then 1 else 0 end) as untested ");
		sb.append("from O_[%COMPANY_ID%]_Build b, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr, O_[%COMPANY_ID%]_Case c, ");
		sb.append("O_[%COMPANY_ID%]_Component co where b.c_buildId_ = ? and ");
		sb.append("b.c_buildId_  = cr.r_buildToCaseResult_c_buildId and ");
		sb.append("cr.r_caseToCaseResult_c_caseId = c.c_caseId_ and ");
		sb.append("c.r_componentToCases_c_componentId = co.c_componentId_ ");

		List<Object> params = new ArrayList<>();

		params.add(testrayBuildId);

		if (Validator.isNotNull(testrayCasePriorities)) {
			sb.append("and c.priority_ in (");
			sb.append(_interpolateParams(params, testrayCasePriorities));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayCaseTypes)) {
			sb.append("and c.r_caseTypeToCases_c_caseTypeId in (");
			sb.append(_interpolateParams(params, testrayCaseTypes));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and co.r_teamToComponents_c_teamId in (");
			sb.append(_interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		sb.append(
			"group by co.c_componentId_, co.name_ order by co.name_ asc ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " limit ? offset ?";

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
				String testrayCaseTypes, String testrayTeamIds,
				Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(28);

		sb.append("select r.c_runId_, r.name_, r.number_, ");
		sb.append("count(cr.dueStatus_) as total, sum(case when ");
		sb.append("cr.dueStatus_ = 'blocked' then 1 else 0 end) as blocked, ");
		sb.append("sum(case when cr.dueStatus_ = 'failed' then 1 else 0 end) ");
		sb.append("as failed, sum(case when cr.dueStatus_ = 'inprogress' ");
		sb.append("then 1 else 0 end) as inprogress, sum(case when ");
		sb.append("cr.dueStatus_ = 'passed' then 1 else 0 end) as passed, ");
		sb.append("sum(case when cr.dueStatus_ = 'testfix' then 1 else 0 ");
		sb.append("end) as testfix, sum(case when cr.dueStatus_ = 'untested' ");
		sb.append("then 1 else 0 end) as untested from ");
		sb.append("O_[%COMPANY_ID%]_Build b, O_[%COMPANY_ID%]_Run r, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr, O_[%COMPANY_ID%]_Case c, ");
		sb.append("O_[%COMPANY_ID%]_Component co where b.c_buildId_  = ? and ");
		sb.append("b.c_buildId_  = r.r_buildToRuns_c_buildId and ");
		sb.append("cr.r_runToCaseResult_c_runId = r.c_runId_ and ");
		sb.append("cr.r_caseToCaseResult_c_caseId = c.c_caseId_ and ");
		sb.append("cr.r_componentToCaseResult_c_componentId = ");
		sb.append("co.c_componentId_ ");

		List<Object> params = new ArrayList<>();

		params.add(testrayBuildId);

		if (Validator.isNotNull(testrayCasePriorities)) {
			sb.append("and c.priority_ in (");
			sb.append(_interpolateParams(params, testrayCasePriorities));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayCaseTypes)) {
			sb.append("and c.r_caseTypeToCases_c_caseTypeId in (");
			sb.append(_interpolateParams(params, testrayCaseTypes));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and co.r_teamToComponents_c_teamId in (");
			sb.append(_interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		sb.append("group by r.c_runId_, r.name_ order by r.number_ asc ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " limit ? offset ?";

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
				String testrayCaseTypes, Long testrayRunId,
				String testrayTeamIds, Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(29);

		sb.append("select t.c_teamId_ , t.name_, count(cr.dueStatus_) as ");
		sb.append("TOTAL, sum(case when cr.dueStatus_ = 'blocked' then 1 ");
		sb.append("else 0 end) as blocked, sum(case when cr.dueStatus_ = ");
		sb.append("'failed' then 1 else 0 end) as failed, sum(case when ");
		sb.append("cr.dueStatus_ = 'inprogress' then 1 else 0 end) as ");
		sb.append("inprogress, sum(case when cr.dueStatus_ = 'passed' then 1 ");
		sb.append("else 0 end) as passed, sum(case when cr.dueStatus_ = ");
		sb.append("'testfix' then 1 else 0 end) as testfix, sum(case when ");
		sb.append("cr.dueStatus_ = 'untested' then 1 else 0 end) as untested ");
		sb.append("from O_[%COMPANY_ID%]_Build b, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr, O_[%COMPANY_ID%]_Case c, ");
		sb.append("O_[%COMPANY_ID%]_Component co, O_[%COMPANY_ID%]_Team t ");
		sb.append("where b.c_buildId_ = ? and b.c_buildId_ = ");
		sb.append("cr.r_buildToCaseResult_c_buildId and ");
		sb.append("cr.r_caseToCaseResult_c_caseId = c.c_caseId_ ");

		List<Object> params = new ArrayList<>();

		params.add(testrayBuildId);

		if (Validator.isNotNull(testrayCasePriorities)) {
			sb.append("and c.priority_ in (");
			sb.append(_interpolateParams(params, testrayCasePriorities));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayCaseTypes)) {
			sb.append("and c.r_caseTypeToCases_c_caseTypeId in (");
			sb.append(_interpolateParams(params, testrayCaseTypes));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayRunId)) {
			sb.append("and cr.r_runToCaseResult_c_runId = ? ");
			params.add(testrayRunId);
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and t.c_teamId_ in (");
			sb.append(_interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		sb.append("and cr.r_componentToCaseResult_c_componentId  = ");
		sb.append("co.c_componentId_ and co.r_teamToComponents_c_teamId = ");
		sb.append("t.c_teamId_ group by t.name_, t.c_teamId_ order by ");
		sb.append("t.name_ asc ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " limit ? offset ?";

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

	@Override
	public Page<TestrayRoutineMetric>
			getTestrayStatusMetricByTestrayProjectIdTestrayProjectTestrayRoutinesMetricsPage(
				Long testrayProjectId, String testrayCasePriorities,
				String testrayCaseTypes, Long testrayRoutineId,
				Long testrayTeamId, Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(34);

		sb.append("select count(cr.dueStatus_) as total, sum(case when ");
		sb.append("cr.dueStatus_ = 'blocked' then 1 else 0 end) as blocked, ");
		sb.append("sum(case when cr.dueStatus_ = 'failed' then 1 else 0 end ");
		sb.append(") as failed, sum(case when cr.dueStatus_ = 'inprogress' ");
		sb.append("then 1 else 0 end) as inprogress, sum(case when ");
		sb.append("cr.dueStatus_ = 'passed' then 1 else 0 end) as passed, ");
		sb.append("sum(case when cr.dueStatus_ = 'testfix' then 1 else 0 end ");
		sb.append(") as testfix, sum(case when cr.dueStatus_ = 'untested' ");
		sb.append("then 1 else 0 end) as untested, r.c_routineId_, r.name_, ");
		sb.append("b.dueDate_ from O_[%COMPANY_ID%]_Project p, ");
		sb.append("O_[%COMPANY_ID%]_Routine r, O_[%COMPANY_ID%]_CaseResult ");
		sb.append("cr, O_[%COMPANY_ID%]_Build b, O_[%COMPANY_ID%]_Case c, ");
		sb.append("O_[%COMPANY_ID%]_Component cp, O_[%COMPANY_ID%]_Team t ");
		sb.append("where p.c_projectId_ = ? and r.c_routineId_ = ");
		sb.append("b.r_routineToBuilds_c_routineId and ");
		sb.append("cr.r_buildToCaseResult_c_buildId = b.c_buildId_ and ");
		sb.append("p.c_projectId_ = r.r_routineToProjects_c_projectId and ");
		sb.append("cr.r_caseToCaseResult_c_caseId = c.c_caseId_ and ");
		sb.append("c.r_componentToCases_c_componentId = cp.c_componentId_ ");
		sb.append("and cp.r_teamToComponents_c_teamId = t.c_teamId_ and ");
		sb.append("b.c_buildId_ = (select b2.c_buildId_ from ");
		sb.append("O_[%COMPANY_ID%]_Build b2 where ");
		sb.append("b2.r_routineToBuilds_c_routineId = r.c_routineId_ and ");
		sb.append("b2.dueDate_ = (select max(b3.dueDate_) from ");
		sb.append("O_[%COMPANY_ID%]_Build b3 where ");
		sb.append("b3.r_routineToBuilds_c_routineId = r.c_routineId_)) ");

		List<Object> params = new ArrayList<>();

		params.add(testrayProjectId);

		if (Validator.isNotNull(testrayCasePriorities)) {
			sb.append("and c.priority_ in (");
			sb.append(_interpolateParams(params, testrayCasePriorities));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayCaseTypes)) {
			sb.append("and c.r_caseTypeToCases_c_caseTypeId in (");
			sb.append(_interpolateParams(params, testrayCaseTypes));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayTeamId)) {
			sb.append("and t.c_teamId_ = ? ");
			params.add(testrayTeamId);
		}

		sb.append("group by  r.c_routineId_, r.name_, b.dueDate_ ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " limit ? offset ?";

		params.add(pagination.getPageSize());
		params.add(pagination.getStartPosition());

		List<Map<String, Object>> values = TestrayUtil.runSQL(sql, params);

		return Page.of(
			transform(
				values,
				value -> {
					TestrayRoutineMetric testrayRoutineMetric =
						new TestrayRoutineMetric();

					testrayRoutineMetric.setTestrayRoutineId(
						GetterUtil.getLong(value.get("c_routineId_")));
					testrayRoutineMetric.setTestrayRoutineName(
						GetterUtil.getString(value.get("name_")));
					testrayRoutineMetric.setTestrayStatusMetric(
						_getTestrayStatusMetric(value));

					LocalDateTime localDateTime = (LocalDateTime)value.get(
						"dueDate_");

					testrayRoutineMetric.setCreateDate(
						Date.from(localDateTime.toInstant(ZoneOffset.UTC)));

					return testrayRoutineMetric;
				}),
			pagination, totalCount);
	}

	private TestrayStatusMetric _getTestrayStatusMetric(
		Map<String, Object> map) {

		TestrayStatusMetric testrayStatusMetric = new TestrayStatusMetric();

		testrayStatusMetric.setBlocked(GetterUtil.getLong(map.get("blocked")));
		testrayStatusMetric.setFailed(GetterUtil.getLong(map.get("failed")));
		testrayStatusMetric.setInProgress(
			GetterUtil.getLong(map.get("inprogress")));
		testrayStatusMetric.setPassed(GetterUtil.getLong(map.get("passed")));
		testrayStatusMetric.setTestfix(GetterUtil.getLong(map.get("testfix")));
		testrayStatusMetric.setTotal(GetterUtil.getLong(map.get("total")));
		testrayStatusMetric.setUntested(
			GetterUtil.getLong(map.get("untested")));

		return testrayStatusMetric;
	}

	private String _interpolateParams(List<Object> params, String values) {
		String[] valuesArray = StringUtil.split(values);

		StringBundler sb = new StringBundler();

		for (String value : valuesArray) {
			sb.append("? ");
			sb.append(", ");
			params.add(value);
		}

		sb.setIndex(sb.index() - 1);

		return sb.toString();
	}

}