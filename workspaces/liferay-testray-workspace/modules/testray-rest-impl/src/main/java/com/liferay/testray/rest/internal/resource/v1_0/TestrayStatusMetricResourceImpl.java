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
import com.liferay.testray.rest.dto.v1_0.TestrayBuildMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayCaseTypeMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayComponentMetric;
import com.liferay.testray.rest.dto.v1_0.TestrayRoutineMetric;
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
				String testrayTeamIds, Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(24);

		sb.append("select ct.c_caseTypeId_, ct.name_, count(cr.dueStatus_) ");
		sb.append("as total, sum(case when cr.dueStatus_ = 'BLOCKED' then 1 ");
		sb.append("else 0 end) as blocked, sum(case when cr.dueStatus_ =  ");
		sb.append("'FAILED' then 1 else 0 end) as failed, sum(case when ");
		sb.append("cr.dueStatus_ = 'INPROGRESS' then 1 else 0 end) as ");
		sb.append("inprogress, sum(case when cr.dueStatus_ = 'PASSED' then 1 ");
		sb.append("else 0 end) as passed, sum(case when cr.dueStatus_ = ");
		sb.append("'TESTFIX' then 1 else 0 end) as testfix, sum(case when ");
		sb.append("cr.dueStatus_ = 'UNTESTED' then 1 else 0 end) as untested ");
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
			sb.append(
				TestrayUtil.interpolateParams(params, testrayCasePriorities));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and co.r_teamToComponents_c_teamId in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		sb.append("group by ct.c_caseTypeId_, ct.name_ order by ct.name_ asc ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		if (pagination != null) {
			sql += " limit ? offset ?";

			params.add(pagination.getPageSize());
			params.add(pagination.getStartPosition());
		}

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			sql, params);

		return Page.of(
			transform(
				values,
				value -> new TestrayCaseTypeMetric() {
					{
						testrayCaseTypeId = GetterUtil.getLong(
							value.get("c_casetypeid_"));
						testrayCaseTypeName = GetterUtil.getString(
							value.get("name_"));
						testrayStatusMetric = _getTestrayStatusMetric(value);
					}
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
		sb.append("as total, sum(case when cr.dueStatus_ = 'BLOCKED' then 1 ");
		sb.append("else 0 end) as blocked, sum(case when cr.dueStatus_ =  ");
		sb.append("'FAILED' then 1 else 0 end) as failed, sum(case when ");
		sb.append("cr.dueStatus_ = 'INPROGRESS' then 1 else 0 end) as  ");
		sb.append("inprogress, sum(case when cr.dueStatus_ = 'PASSED' then 1 ");
		sb.append("else 0 end) as passed, sum(case when cr.dueStatus_ = ");
		sb.append("'TESTFIX' then 1 else 0 end) as testfix, sum(case when ");
		sb.append("cr.dueStatus_ = 'UNTESTED' then 1 else 0 end) as untested ");
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
			sb.append(
				TestrayUtil.interpolateParams(params, testrayCasePriorities));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayCaseTypes)) {
			sb.append("and c.r_caseTypeToCases_c_caseTypeId in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayCaseTypes));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and co.r_teamToComponents_c_teamId in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		sb.append(
			"group by co.c_componentId_, co.name_ order by co.name_ asc ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		if (pagination != null) {
			sql += " limit ? offset ?";

			params.add(pagination.getPageSize());
			params.add(pagination.getStartPosition());
		}

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			sql, params);

		return Page.of(
			transform(
				values,
				value -> new TestrayComponentMetric() {
					{
						testrayComponentId = GetterUtil.getLong(
							value.get("c_componentid_"));
						testrayComponentName = GetterUtil.getString(
							value.get("name_"));
						testrayStatusMetric = _getTestrayStatusMetric(value);
					}
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
		sb.append("cr.dueStatus_ = 'BLOCKED' then 1 else 0 end) as blocked, ");
		sb.append("sum(case when cr.dueStatus_ = 'FAILED' then 1 else 0 end) ");
		sb.append("as failed, sum(case when cr.dueStatus_ = 'INPROGRESS' ");
		sb.append("then 1 else 0 end) as inprogress, sum(case when ");
		sb.append("cr.dueStatus_ = 'PASSED' then 1 else 0 end) as passed, ");
		sb.append("sum(case when cr.dueStatus_ = 'TESTFIX' then 1 else 0 ");
		sb.append("end) as testfix, sum(case when cr.dueStatus_ = 'UNTESTED' ");
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
			sb.append(
				TestrayUtil.interpolateParams(params, testrayCasePriorities));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayCaseTypes)) {
			sb.append("and c.r_caseTypeToCases_c_caseTypeId in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayCaseTypes));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and co.r_teamToComponents_c_teamId in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		sb.append("group by r.c_runId_, r.name_ order by r.number_ asc ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		if (pagination != null) {
			sql += " limit ? offset ?";

			params.add(pagination.getPageSize());
			params.add(pagination.getStartPosition());
		}

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			sql, params);

		return Page.of(
			transform(
				values,
				value -> new TestrayRunMetric() {
					{
						testrayRunId = GetterUtil.getLong(
							value.get("c_runid_"));
						testrayRunName = GetterUtil.getString(
							value.get("name_"));
						testrayRunNumber = GetterUtil.getLong(
							value.get("number_"));
						testrayStatusMetric = _getTestrayStatusMetric(value);
					}
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
		sb.append("total, sum(case when cr.dueStatus_ = 'BLOCKED' then 1 ");
		sb.append("else 0 end) as blocked, sum(case when cr.dueStatus_ = ");
		sb.append("'FAILED' then 1 else 0 end) as failed, sum(case when ");
		sb.append("cr.dueStatus_ = 'INPROGRESS' then 1 else 0 end) as ");
		sb.append("inprogress, sum(case when cr.dueStatus_ = 'PASSED' then 1 ");
		sb.append("else 0 end) as passed, sum(case when cr.dueStatus_ = ");
		sb.append("'TESTFIX' then 1 else 0 end) as testfix, sum(case when ");
		sb.append("cr.dueStatus_ = 'UNTESTED' then 1 else 0 end) as untested ");
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
			sb.append(
				TestrayUtil.interpolateParams(params, testrayCasePriorities));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayCaseTypes)) {
			sb.append("and c.r_caseTypeToCases_c_caseTypeId in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayCaseTypes));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayRunId)) {
			sb.append("and cr.r_runToCaseResult_c_runId = ? ");
			params.add(testrayRunId);
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and t.c_teamId_ in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTeamIds));
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

		if (pagination != null) {
			sql += " limit ? offset ?";

			params.add(pagination.getPageSize());
			params.add(pagination.getStartPosition());
		}

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			sql, params);

		return Page.of(
			transform(
				values,
				value -> new TestrayTeamMetric() {
					{
						testrayStatusMetric = _getTestrayStatusMetric(value);
						testrayTeamId = GetterUtil.getLong(
							value.get("c_teamid_"));
						testrayTeamName = GetterUtil.getString(
							value.get("name_"));
					}
				}),
			pagination, totalCount);
	}

	@Override
	public Page<TestrayRoutineMetric>
			getTestrayStatusMetricByTestrayProjectIdTestrayProjectTestrayRoutinesMetricsPage(
				Long testrayProjectId, Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(24);

		sb.append("select (b.caseresultblocked_ + b.caseresultfailed_ + ");
		sb.append("b.caseresultinprogress_ + b.caseresultpassed_ + ");
		sb.append("b.caseresulttestfix_ + b.caseresultuntested_) as total,");
		sb.append("b.caseResultBlocked_ as blocked, b.caseresultfailed_ as ");
		sb.append("failed, b.caseresultinprogress_ as inprogress, ");
		sb.append("b.caseresultpassed_ as passed, b.caseresulttestfix_ as ");
		sb.append("testfix, b.caseresultuntested_ as untested, ");
		sb.append("r.c_routineId_, r.name_, b.dueDate_ from ");
		sb.append("O_[%COMPANY_ID%]_Project p, O_[%COMPANY_ID%]_Routine r, ");
		sb.append("O_[%COMPANY_ID%]_Build b where p.c_projectId_ = ? and ");
		sb.append("r.c_routineId_ = b.r_routineToBuilds_c_routineId and ");
		sb.append("p.c_projectId_ = r.r_routineToProjects_c_projectId and ");
		sb.append("b.c_buildId_ = (select b2.c_buildId_ from ");
		sb.append("O_[%COMPANY_ID%]_Build b2 where ");
		sb.append("b2.r_routineToBuilds_c_routineId = r.c_routineId_ and ");
		sb.append("b2.dueDate_ = (select max(b3.dueDate_) from ");
		sb.append("O_[%COMPANY_ID%]_Build b3 where ");
		sb.append("b3.r_routineToBuilds_c_routineId = r.c_routineId_ and ");
		sb.append("exists  (select 1 from O_[%COMPANY_ID%]_CaseResult cr ");
		sb.append("where cr.r_buildToCaseResult_c_buildId = b3.c_buildId_)) ");
		sb.append("limit 1) group by r.c_routineId_, r.name_, b.dueDate_, ");
		sb.append("b.caseresultblocked_, b.caseresultfailed_, ");
		sb.append("b.caseresultinprogress_, b.caseresultpassed_, ");
		sb.append("b.caseresulttestfix_, b.caseresultuntested_");

		List<Object> params = new ArrayList<>();

		params.add(testrayProjectId);

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		if (pagination != null) {
			sql += " limit ? offset ?";

			params.add(pagination.getPageSize());
			params.add(pagination.getStartPosition());
		}

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			sql, params);

		return Page.of(
			transform(
				values,
				value -> new TestrayRoutineMetric() {
					{
						testrayRoutineId = GetterUtil.getLong(
							value.get("c_routineid_"));
						testrayRoutineName = GetterUtil.getString(
							value.get("name_"));
						testrayStatusMetric = _getTestrayStatusMetric(value);

						setTestrayBuildDueDate(
							() -> {
								if (value.get("duedate_") == null) {
									return null;
								}

								return value.get(
									"duedate_"
								).toString();
							});
					}
				}),
			pagination, totalCount);
	}

	@Override
	public Page<TestrayBuildMetric>
			getTestrayStatusMetricByTestrayRoutineIdTestrayRoutineTestrayBuildsMetricsPage(
				Long testrayRoutineId, Long testrayBuildId,
				String testrayBuildName, String testrayProductVersion,
				String testrayTaskStatus, Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(14);

		sb.append("select b.c_buildId_ from O_[%COMPANY_ID%]_Build b, ");
		sb.append("O_[%COMPANY_ID%]_ProductVersion pv ");

		if (Validator.isNotNull(testrayTaskStatus)) {
			sb.append(", O_[%COMPANY_ID%]_Task t ");
		}

		sb.append("where b.r_routineToBuilds_c_routineId = ? and ");
		sb.append("pv.c_productVersionId_ = ");
		sb.append("b.r_productVersionToBuilds_c_productVersionId and ");
		sb.append("b.template_ = false and b.archived_ = false ");

		List<Object> params = new ArrayList<>();

		params.add(testrayRoutineId);

		if (Validator.isNotNull(testrayProductVersion)) {
			sb.append("and pv.c_productVersionId_ = ? ");
			params.add(GetterUtil.getLong(testrayProductVersion));
		}

		if (Validator.isNotNull(testrayBuildName)) {
			sb.append("and b.name_ like ? ");
			params.add("%" + testrayBuildName + "%");
		}

		if (Validator.isNotNull(testrayTaskStatus)) {
			sb.append("and t.r_buildToTasks_c_buildId = b.c_buildId_ and ");
			sb.append("t.dueStatus_ in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTaskStatus));
			sb.append(") ");
		}

		sb.append("group by b.c_buildId_");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sb = new StringBundler(26);

		sb.append("select (b.caseresultblocked_ + b.caseresultfailed_ + ");
		sb.append("b.caseresultinprogress_ + b.caseresultpassed_ + ");
		sb.append("b.caseresulttestfix_ + b.caseresultuntested_) as total,");
		sb.append("b.caseResultBlocked_ as blocked, b.caseresultfailed_ as ");
		sb.append("failed, b.caseresultinprogress_ as inprogress, ");
		sb.append("b.caseresultpassed_ as passed, b.caseresulttestfix_ as ");
		sb.append("testfix, b.caseresultuntested_ as untested, b.c_buildId_, ");
		sb.append("b.dueDate_, bx.importStatus_, b.gitHash_, b.name_, ");
		sb.append("b.promoted_, b.archived_, pv.name_ as productVersionName, ");
		sb.append("(select dueStatus_ from O_[%COMPANY_ID%]_Task t where ");
		sb.append("t.r_buildToTasks_c_buildId = b.c_buildId_) as taskStatus ");
		sb.append("from O_[%COMPANY_ID%]_Build b, O_[%COMPANY_ID%]_Build_x ");
		sb.append("bx, O_[%COMPANY_ID%]_ProductVersion pv ");

		if (Validator.isNotNull(testrayTaskStatus)) {
			sb.append(", O_[%COMPANY_ID%]_Task t ");
		}

		sb.append("where b.r_routineToBuilds_c_routineId = ? and ");
		sb.append("bx.c_buildid_ = b.c_buildid_ and pv.c_productVersionId_ = ");
		sb.append("b.r_productVersionToBuilds_c_productVersionId and ");
		sb.append("b.template_ = false and b.archived_ = false ");

		params = new ArrayList<>();

		params.add(testrayRoutineId);

		if (Validator.isNotNull(testrayProductVersion)) {
			sb.append("and pv.c_productVersionId_ = ? ");
			params.add(GetterUtil.getLong(testrayProductVersion));
		}

		if (Validator.isNotNull(testrayBuildName)) {
			sb.append("and b.name_ like ? ");
			params.add("%" + testrayBuildName + "%");
		}

		if (Validator.isNotNull(testrayTaskStatus)) {
			sb.append("and t.r_buildToTasks_c_buildId = b.c_buildId_ and ");
			sb.append("t.dueStatus_ in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTaskStatus));
			sb.append(") ");
		}

		sb.append("group by b.c_buildId_, bx.importstatus_, pv.name_ order ");
		sb.append("by b.c_buildId_ desc limit ? offset ?");

		sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		params.add(pagination.getPageSize());
		params.add(pagination.getStartPosition());

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			sql, params);

		return Page.of(
			transform(
				values,
				value -> new TestrayBuildMetric() {
					{
						testrayBuildArchived = GetterUtil.getBoolean(
							String.valueOf(value.get("archived_")));
						testrayBuildGitHash = GetterUtil.getString(
							value.get("githash_"));
						testrayBuildId = GetterUtil.getLong(
							value.get("c_buildid_"));
						testrayBuildImportStatus = GetterUtil.getString(
							value.get("importstatus_"));
						testrayBuildName = GetterUtil.getString(
							value.get("name_"));
						testrayBuildProductVersion = GetterUtil.getString(
							value.get("productversionname"));
						testrayBuildPromoted = GetterUtil.getBoolean(
							String.valueOf(value.get("promoted_")));
						testrayBuildTaskStatus = GetterUtil.getString(
							value.get("taskstatus"));
						testrayStatusMetric = _getTestrayStatusMetric(value);

						setTestrayBuildDueDate(
							() -> {
								if (value.get("duedate_") == null) {
									return null;
								}

								return value.get(
									"duedate_"
								).toString();
							});
					}
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

}