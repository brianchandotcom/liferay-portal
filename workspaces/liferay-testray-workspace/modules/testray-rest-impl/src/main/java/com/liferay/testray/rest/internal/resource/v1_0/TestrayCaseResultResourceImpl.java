/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.testray.rest.dto.v1_0.TestrayCaseResult;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.resource.v1_0.TestrayCaseResultResource;

import java.net.URI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/testray-case-result.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayCaseResultResource.class
)
public class TestrayCaseResultResourceImpl
	extends BaseTestrayCaseResultResourceImpl {

	@Override
	public Page<TestrayCaseResult> getTestrayCaseResultHistoryTestrayCasePage(
			Long testrayCaseId, String error, String issues,
			String maxExecutionDate, String minExecutionDate, Boolean noError,
			Boolean noIssues, String status, String testrayProductVersionIds,
			String testrayRoutineIds, String testrayRunName,
			String testrayTeamIds, String testrayUserId, String warning,
			Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(43);

		sb.append("select b.c_buildId_, b.dueDate_ as executionDate, ");
		sb.append("b.gitHash_,  cr.c_caseResultId_, cr.dueStatus_, ");
		sb.append("cr.errors_, cr.issues_, cr.warnings_, pv.name_ as ");
		sb.append("productVersion, r.name_ as runName, ro.name_ as ");
		sb.append("routineName, ro.c_routineId_, t.name_ as teamName from ");
		sb.append("O_[%COMPANY_ID%]_Build b, O_[%COMPANY_ID%]_CaseResult cr, ");
		sb.append("O_[%COMPANY_ID%]_Case c, O_[%COMPANY_ID%]_CaseType ct, ");
		sb.append("O_[%COMPANY_ID%]_Component co, O_[%COMPANY_ID%]_Run r, ");
		sb.append("O_[%COMPANY_ID%]_Routine ro, ");
		sb.append("O_[%COMPANY_ID%]_ProductVersion pv, O_[%COMPANY_ID%]_Team ");
		sb.append("t where c.c_caseId_ = ? and ");
		sb.append("cr.r_buildToCaseResult_c_buildId = b.c_buildId_ and ");
		sb.append("c.c_caseId_ = cr.r_caseToCaseResult_c_caseId and ");
		sb.append("ct.c_caseTypeId_ = c.r_caseTypeToCases_c_caseTypeId and ");
		sb.append("co.c_componentId_ = ");
		sb.append("cr.r_componentToCaseResult_c_componentId and r.c_runId_ = ");
		sb.append("cr.r_runToCaseResult_c_runId and t.c_teamId_ = ");
		sb.append("co.r_teamToComponents_c_teamId and ro.c_routineId_ = ");
		sb.append("b.r_routineToBuilds_c_routineId and ");
		sb.append("pv.c_productVersionId_ =");
		sb.append("b.r_productVersionToBuilds_c_productVersionId ");

		List<Object> params = new ArrayList<>();

		params.add(testrayCaseId);

		if (Validator.isNotNull(error)) {
			sb.append("and cr.errors_ like ? ");
			params.add("%" + error + "%");
		}

		if (Validator.isNotNull(issues)) {
			sb.append("and cr.issues_ like ? ");
			params.add("%" + issues + "%");
		}

		if (Validator.isNotNull(maxExecutionDate)) {
			sb.append("and b.dueDate_ <= ?");
			params.add(maxExecutionDate);
		}

		if (Validator.isNotNull(minExecutionDate)) {
			sb.append("and b.dueDate_ >= ?");
			params.add(minExecutionDate);
		}

		if (noError != null) {
			sb.append("and (cr.errors_ is null or cr.errors_ = '') ");
		}

		if (noIssues != null) {
			sb.append("and (cr.issues_ is null or cr.issues_ = '') ");
		}

		if (Validator.isNotNull(status)) {
			sb.append("and cr.dueStatus_ in (");
			sb.append(TestrayUtil.interpolateParams(params, status));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayProductVersionIds)) {
			sb.append("and pv.c_productVersionId_ in (");
			sb.append(
				TestrayUtil.interpolateParams(
					params, testrayProductVersionIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayRoutineIds)) {
			sb.append("and ro.c_routineId_ in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayRoutineIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayRunName)) {
			sb.append("and r.name_ like ? ");
			params.add("%" + testrayRunName + "%");
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and co.r_teamToComponents_c_teamId in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayUserId)) {
			sb.append("and cr.r_userToCaseResults_userId  = ? ");
			params.add(testrayUserId);
		}

		if (Validator.isNotNull(warning)) {
			sb.append("and cr.warnings_  = ? ");
			params.add(warning);
		}

		sb.append("group by cr.c_caseResultId_ order by b.dueDate_ desc ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " limit ? offset ?";

		params.add(pagination.getPageSize());
		params.add(pagination.getStartPosition());

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			sql, params);

		return Page.of(
			transform(
				values,
				value -> new TestrayCaseResult() {
					{
						error = GetterUtil.getString(value.get("errors_"));
						gitHash = GetterUtil.getString(value.get("gitHash_"));
						issues = GetterUtil.getString(value.get("issues_"));
						status = GetterUtil.getString(value.get("dueStatus_"));
						testrayBuildId = GetterUtil.getLong(
							value.get("c_buildId_"));
						testrayCaseResultId = GetterUtil.getLong(
							value.get("c_caseResultId_"));
						testrayProductVersionName = GetterUtil.getString(
							value.get("productVersion"));
						testrayRoutineId = GetterUtil.getLong(
							value.get("c_routineId_"));
						testrayRoutineName = GetterUtil.getString(
							value.get("routineName"));
						testrayRunName = GetterUtil.getString(
							value.get("runName"));
						testrayTeamName = GetterUtil.getString(
							value.get("teamName"));
						userName = GetterUtil.getString(value.get("name"));
						warning = GetterUtil.getInteger(value.get("warnings_"));

						setExecutionDate(
							() -> {
								if (value.get("executionDate") == null) {
									return null;
								}

								return value.get(
									"executionDate"
								).toString();
							});
					}
				}),
			pagination, totalCount);
	}

	@Override
	public Page<TestrayCaseResult> getTestrayCaseResultsTestrayBuildPage(
			Long testrayBuildId, String comment, String error, Boolean flaky,
			String issues, Boolean noComment, Boolean noError, Boolean noIssues,
			String priority, String status, String testrayCaseName,
			String testrayCaseTypeIds, String testrayComponentIds,
			String testrayRunId, String testrayRunName, String testrayTeamIds,
			String testrayUserId, Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(49);

		sb.append("select cr.c_caseResultId_, cr.comment_, cr.dueStatus_, ");
		sb.append("cr.errors_, cr.issues_, ct.name_ as caseTypeName, c.name_ ");
		sb.append("as caseName, c.priority_, cx.flaky_, r.name_ as runName, ");
		sb.append("r.number_ as runNumber, co.name_ as componentName, ");
		sb.append("t.name_ as teamName, u.firstName, u.lastName, ");
		sb.append("u.middleName, u.uuid_, u.portraitId from ");
		sb.append("O_[%COMPANY_ID%]_Build b, O_[%COMPANY_ID%]_CaseResult cr ");
		sb.append("left outer join User_ u on u.userId = ");
		sb.append("cr.r_userToCaseResults_userId, O_[%COMPANY_ID%]_Case c, ");
		sb.append("O_[%COMPANY_ID%]_Case_x cx, O_[%COMPANY_ID%]_CaseType ct, ");
		sb.append("O_[%COMPANY_ID%]_Component co, O_[%COMPANY_ID%]_Run r, ");
		sb.append("O_[%COMPANY_ID%]_Team t where b.c_buildId_ = ? and ");
		sb.append("cr.r_buildToCaseResult_c_buildId = b.c_buildId_ and ");
		sb.append("c.c_caseId_ = cr.r_caseToCaseResult_c_caseId and ");
		sb.append("c.c_caseId_ = cx.c_caseId_ and ct.c_caseTypeId_ = ");
		sb.append("c.r_caseTypeToCases_c_caseTypeId and co.c_componentId_ = ");
		sb.append("cr.r_componentToCaseResult_c_componentId and r.c_runId_ = ");
		sb.append("cr.r_runToCaseResult_c_runId and t.c_teamId_ = ");
		sb.append("co.r_teamToComponents_c_teamId ");

		List<Object> params = new ArrayList<>();

		params.add(testrayBuildId);

		if (Validator.isNotNull(comment)) {
			sb.append("and cr.comment_ like ? ");
			params.add("%" + comment + "%");
		}

		if (Validator.isNotNull(error)) {
			sb.append("and cr.errors_ like ? ");
			params.add("%" + error + "%");
		}

		if (flaky != null) {
			sb.append("and cx.flaky_ = ? ");
			params.add(flaky);
		}

		if (Validator.isNotNull(issues)) {
			sb.append("and cr.issues_ like ? ");
			params.add("%" + issues + "%");
		}

		if (noComment != null) {
			sb.append("and (cr.comment_ is null or cr.comment_ = '') ");
		}

		if (noError != null) {
			sb.append("and (cr.errors_ is null or cr.errors_ = '') ");
		}

		if (noIssues != null) {
			sb.append("and (cr.issues_ is null or cr.issues_ = '') ");
		}

		if (Validator.isNotNull(priority)) {
			sb.append("and c.priority_ in (");
			sb.append(TestrayUtil.interpolateParams(params, priority));
			sb.append(") ");
		}

		if (Validator.isNotNull(status)) {
			sb.append("and cr.dueStatus_ in (");
			sb.append(TestrayUtil.interpolateParams(params, status));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayCaseName)) {
			sb.append("and c.name_ like ? ");
			params.add("%" + testrayCaseName + "%");
		}

		if (Validator.isNotNull(testrayCaseTypeIds)) {
			sb.append("and c.r_caseTypeToCases_c_caseTypeId in (");
			sb.append(
				TestrayUtil.interpolateParams(params, testrayCaseTypeIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayComponentIds)) {
			sb.append("and cr.r_componentToCaseResult_c_componentId in (");
			sb.append(
				TestrayUtil.interpolateParams(params, testrayComponentIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayRunId)) {
			sb.append("and cr.r_runToCaseResult_c_runId = ? ");
			params.add(testrayRunId);
		}

		if (Validator.isNotNull(testrayRunName)) {
			sb.append("and r.name_ like ? ");
			params.add("%" + testrayRunName + "%");
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and co.r_teamToComponents_c_teamId in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayUserId)) {
			sb.append("and cr.r_userToCaseResults_userId  = ? ");
			params.add(testrayUserId);
		}

		sb.append("group by cr.c_caseResultId_, u.firstName, u.lastName, ");
		sb.append("u.middleName, u.uuid_, u.portraitId order by ");
		sb.append("cr.dueStatus_ asc, cr.errors_ is null asc, c.priority_ ");
		sb.append("desc, t.name_ asc, co.name_ asc, ct.name_ asc ");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		long totalCount = TestrayUtil.getTotalCount(sql, params);

		sql += " limit ? offset ?";

		params.add(pagination.getPageSize());
		params.add(pagination.getStartPosition());

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			sql, params);

		return Page.of(
			transform(
				values,
				value -> new TestrayCaseResult() {
					{
						actions = _getActions(value);
						comment = GetterUtil.getString(value.get("comment_"));
						error = GetterUtil.getString(value.get("errors_"));
						flaky = GetterUtil.getBoolean(
							String.valueOf(value.get("flaky_")));
						issues = GetterUtil.getString(value.get("issues_"));
						priority = GetterUtil.getLong(value.get("priority_"));
						status = GetterUtil.getString(value.get("dueStatus_"));
						testrayCaseName = GetterUtil.getString(
							value.get("caseName"));
						testrayCaseResultId = GetterUtil.getLong(
							value.get("c_caseResultId_"));
						testrayCaseTypeName = GetterUtil.getString(
							value.get("caseTypeName"));
						testrayComponentName = GetterUtil.getString(
							value.get("componentName"));
						testrayRunName = GetterUtil.getString(
							value.get("runName"));
						testrayRunNumber = GetterUtil.getLong(
							value.get("runNumber"));
						testrayTeamName = GetterUtil.getString(
							value.get("teamName"));

						setUserImgUrl(
							() -> {
								if (value.get("portraitId") == null) {
									return null;
								}

								return UserConstants.getPortraitURL(
									"/image", true,
									GetterUtil.getLong(value.get("portraitId")),
									GetterUtil.getString(value.get("uuid_")));
							});
						setUserName(
							() -> {
								FullNameGenerator fullNameGenerator =
									FullNameGeneratorFactory.getInstance();

								return fullNameGenerator.getFullName(
									GetterUtil.getString(
										value.get("firstName")),
									GetterUtil.getString(
										value.get("middleName")),
									GetterUtil.getString(
										value.get("lastName")));
							});
					}
				}),
			pagination, totalCount);
	}

	private Map<String, Map<String, String>> _getActions(
		Map<String, Object> value) {

		try {
			if (!ArrayUtil.contains(
					contextUser.getRoleIds(),
					_roleLocalService.getRole(
						contextUser.getCompanyId(), "Testray Administrator"
					).getRoleId()) &&
				!ArrayUtil.contains(
					contextUser.getRoleIds(),
					_roleLocalService.getRole(
						contextUser.getCompanyId(), "Testray Lead"
					).getRoleId())) {

				return null;
			}
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}

		URI baseURI = contextUriInfo.getBaseUri();

		String href =
			baseURI.getScheme() + "://" + baseURI.getAuthority() +
				"/o/c/caseresults/" + value.get("c_caseResultId_");

		return new HashMap<>(
			HashMapBuilder.put(
				"delete",
				HashMapBuilder.put(
					"href", href
				).put(
					"method", "DELETE"
				).build()
			).put(
				"update",
				HashMapBuilder.put(
					"href", href
				).put(
					"method", "PUT"
				).build()
			).build());
	}

	@Reference
	private RoleLocalService _roleLocalService;

}