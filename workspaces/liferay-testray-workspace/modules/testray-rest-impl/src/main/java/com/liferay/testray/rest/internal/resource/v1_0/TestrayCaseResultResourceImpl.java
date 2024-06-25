/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
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

	public Page<TestrayCaseResult> getTestrayCaseResultHistoryTestrayCasePage(
			Long testrayCaseId, String error, String issues,
			String maxExecutionDate, String minExecutionDate, Boolean noError,
			Boolean noIssues, String status, String testrayProductVersionIds,
			String testrayRoutineIds, String testrayRunName,
			String testrayTeamIds, String testrayUserId, String warning,
			Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(44);

		sb.append("select bd.gitHash_, bd.c_buildId_, cr.c_caseResultId_, ");
		sb.append("cr.dueStatus_, cr.errors_, cr.issues_, cr.warnings_, ");
		sb.append("oe.createDate as executionDate, pv.name_ as ");
		sb.append("productVersion, rn.name_ as runName, rt.name_ as ");
		sb.append("routineName, rt.c_routineId_, tm.name_ as teamName from ");
		sb.append("O_[%COMPANY_ID%]_Build bd, O_[%COMPANY_ID%]_CaseResult ");
		sb.append("cr, O_[%COMPANY_ID%]_Case cs, O_[%COMPANY_ID%]_CaseType ");
		sb.append("ct, O_[%COMPANY_ID%]_Component cp, O_[%COMPANY_ID%]_Run ");
		sb.append("rn, O_[%COMPANY_ID%]_Routine rt, ");
		sb.append("O_[%COMPANY_ID%]_ProductVersion pv, O_[%COMPANY_ID%]_Team ");
		sb.append("tm, ObjectEntry oe where cs.c_caseId_ = ? and ");
		sb.append("cr.r_buildToCaseResult_c_buildId = bd.c_buildId_ and ");
		sb.append("cs.c_caseId_ = cr.r_caseToCaseResult_c_caseId and ");
		sb.append("ct.c_caseTypeId_ = cs.r_caseTypeToCases_c_caseTypeId and ");
		sb.append("cp.c_componentId_ = ");
		sb.append("cr.r_componentToCaseResult_c_componentId and rn.c_runId_ ");
		sb.append("= cr.r_runToCaseResult_c_runId and tm.c_teamId_ = ");
		sb.append("cp.r_teamToComponents_c_teamId and oe.objectEntryId = ");
		sb.append("cr.c_caseResultId_ and rt.c_routineId_ = ");
		sb.append("bd.r_routineToBuilds_c_routineId and ");
		sb.append("pv.c_productVersionId_ =");
		sb.append("bd.r_productVersionToBuilds_c_productVersionId ");

		List<Object> params = new ArrayList<>();

		params.add(testrayCaseId);

		if (Validator.isNotNull(error)) {
			sb.append("and cr.errors_ like ? ");
			params.add("%" + error + "%");
		}

		if (Validator.isBoolean(String.valueOf(noError))) {
			sb.append("and (cr.errors_ is null or cr.errors_ = '') ");
		}

		if (Validator.isNotNull(issues)) {
			sb.append("and cr.issues_ like ? ");
			params.add("%" + issues + "%");
		}

		if (Validator.isBoolean(String.valueOf(noIssues))) {
			sb.append("and (cr.issues_ is null or cr.issues_ = '') ");
		}

		if (Validator.isNotNull(status)) {
			sb.append("and cr.dueStatus_ in (");
			sb.append(TestrayUtil.interpolateParams(params, status));
			sb.append(") ");
		}

		if (Validator.isNotNull(maxExecutionDate)) {
			sb.append("and oe.createDate < ?");
			params.add(maxExecutionDate);
		}

		if (Validator.isNotNull(minExecutionDate)) {
			sb.append("and oe.createDate > ?");
			params.add(minExecutionDate);
		}

		if (Validator.isNotNull(testrayProductVersionIds)) {
			sb.append("and pv.c_productVersionId_ in (");
			sb.append(
				TestrayUtil.interpolateParams(
					params, testrayProductVersionIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayRoutineIds)) {
			sb.append("and rt.c_routineId_ in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayRoutineIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayRunName)) {
			sb.append("and rn.name_ ? ");
			params.add("%" + testrayRunName + "%");
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and cp.r_teamToComponents_c_teamId in (");
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

		sb.append("group by cr.c_caseResultId_ order by oe.createDate desc ");

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
				value -> {
					TestrayCaseResult testrayCaseResult =
						new TestrayCaseResult();

					if (value.get("executionDate") != null) {
						testrayCaseResult.setExecutionDate(
							value.get(
								"executionDate"
							).toString());
					}

					testrayCaseResult.setError(
						GetterUtil.getString(value.get("errors_")));
					testrayCaseResult.setIssues(
						GetterUtil.getString(value.get("issues_")));
					testrayCaseResult.setStatus(
						GetterUtil.getString(value.get("dueStatus_")));
					testrayCaseResult.setTestrayBuildId(
						GetterUtil.getLong(value.get("c_buildId_")));
					testrayCaseResult.setTestrayCaseResultId(
						GetterUtil.getLong(value.get("c_caseResultId_")));
					testrayCaseResult.setGitHash(
						GetterUtil.getString(value.get("gitHash_")));
					testrayCaseResult.setTestrayProductVersionName(
						GetterUtil.getString(value.get("productVersion")));
					testrayCaseResult.setTestrayRoutineId(
						GetterUtil.getLong(value.get("c_routineId_")));
					testrayCaseResult.setTestrayRoutineName(
						GetterUtil.getString(value.get("routineName")));
					testrayCaseResult.setTestrayRunName(
						GetterUtil.getString(value.get("runName")));
					testrayCaseResult.setTestrayTeamName(
						GetterUtil.getString(value.get("teamName")));
					testrayCaseResult.setUserName(
						GetterUtil.getString(value.get("name")));
					testrayCaseResult.setWarning(
						GetterUtil.getInteger(value.get("warnings_")));

					return testrayCaseResult;
				}),
			pagination, totalCount);
	}

	public Page<TestrayCaseResult> getTestrayCaseResultsTestrayBuildPage(
			Long testrayBuildId, String comment, String error, String issues,
			Boolean noComment, Boolean noError, Boolean noIssues,
			String priority, String status, String testrayCaseName,
			String testrayCaseTypeIds, String testrayComponentIds,
			String testrayRunId, String testrayRunName, String testrayTeamIds,
			String testrayUserId, Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(48);

		sb.append("select cr.c_caseResultId_, cr.comment_, cr.dueStatus_, ");
		sb.append("cr.errors_, cr.issues_, ct.name_ as caseTypeName, ");
		sb.append("cs.name_ as caseName, cs.priority_, rn.name_ as runName, ");
		sb.append("rn.number_ as runNumber, cp.name_ as componentName, ");
		sb.append("tm.name_ as teamName,us.firstName, ");
		sb.append("us.lastName,us.middleName, us.uuid_, us.portraitId from ");
		sb.append("O_[%COMPANY_ID%]_Build bd, O_[%COMPANY_ID%]_CaseResult cr ");
		sb.append("left outer join User_ us on us.userId = ");
		sb.append("cr.r_userToCaseResults_userId, O_[%COMPANY_ID%]_Case ");
		sb.append("cs,O_[%COMPANY_ID%]_CaseType ct, ");
		sb.append("O_[%COMPANY_ID%]_Component cp, O_[%COMPANY_ID%]_Run rn, ");
		sb.append("O_[%COMPANY_ID%]_Team tm,ObjectEntry oe where ");
		sb.append("bd.c_buildId_ = ? and cr.r_buildToCaseResult_c_buildId = ");
		sb.append("bd.c_buildId_ and cs.c_caseId_ = ");
		sb.append("cr.r_caseToCaseResult_c_caseId and ct.c_caseTypeId_ = ");
		sb.append("cs.r_caseTypeToCases_c_caseTypeId and cp.c_componentId_ = ");
		sb.append("cr.r_componentToCaseResult_c_componentId and rn.c_runId_ ");
		sb.append("= cr.r_runToCaseResult_c_runId and tm.c_teamId_ = ");
		sb.append("cp.r_teamToComponents_c_teamId and oe.objectEntryId = ");
		sb.append("cr.c_caseResultId_ ");

		List<Object> params = new ArrayList<>();

		params.add(testrayBuildId);

		if (Validator.isNotNull(comment)) {
			sb.append("and cr.comment_ like ? ");
			params.add("%" + comment + "%");
		}

		if (Validator.isBoolean(String.valueOf(noComment))) {
			sb.append("and (cr.comment_ is null or cr.comment_ = '') ");
		}

		if (Validator.isNotNull(error)) {
			sb.append("and cr.errors_ like ? ");
			params.add("%" + error + "%");
		}

		if (Validator.isBoolean(String.valueOf(noError))) {
			sb.append("and (cr.errors_ is null or cr.errors_ = '') ");
		}

		if (Validator.isNotNull(issues)) {
			sb.append("and cr.issues_ like ? ");
			params.add("%" + issues + "%");
		}

		if (Validator.isBoolean(String.valueOf(noIssues))) {
			sb.append("and (cr.issues_ is null or cr.issues_ = '') ");
		}

		if (Validator.isNotNull(priority)) {
			sb.append("and cs.priority_ in (");
			sb.append(TestrayUtil.interpolateParams(params, priority));
			sb.append(") ");
		}

		if (Validator.isNotNull(status)) {
			sb.append("and cr.dueStatus_ in (");
			sb.append(TestrayUtil.interpolateParams(params, status));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayCaseName)) {
			sb.append("and cs.name_ like ? ");
			params.add("%" + testrayCaseName + "%");
		}

		if (Validator.isNotNull(testrayCaseTypeIds)) {
			sb.append("and cs.r_caseTypeToCases_c_caseTypeId in (");
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
			sb.append("and rn.name_ ? ");
			params.add("%" + testrayRunName + "%");
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and cp.r_teamToComponents_c_teamId in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayUserId)) {
			sb.append("and cr.r_userToCaseResults_userId  = ? ");
			params.add(testrayUserId);
		}

		sb.append("group by cr.c_caseResultId_, us.firstName, us.lastName, ");
		sb.append("us.middleName, us.uuid_, us.portraitId order by ");
		sb.append("cr.dueStatus_ asc, cr.errors_ asc");

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
				value -> {
					TestrayCaseResult testrayCaseResult =
						new TestrayCaseResult();

					if (ListUtil.fromArray(
							contextUser.getRoleIds()
						).contains(
							_roleLocalService.getRole(
								contextUser.getCompanyId(),
								"Testray Administrator"
							).getRoleId()
						) ||
						ListUtil.fromArray(
							contextUser.getRoleIds()
						).contains(
							_roleLocalService.getRole(
								contextUser.getCompanyId(), "Testray Lead"
							).getRoleId()
						)) {

						URI baseURI = contextUriInfo.getBaseUri();

						testrayCaseResult.setActions(
							new HashMap<>(
								HashMapBuilder.put(
									"delete",
									HashMapBuilder.put(
										"href",
										baseURI.getScheme() + "://" +
											baseURI.getAuthority() +
												"/o/c/caseresults/" +
													value.get("c_caseResultId_")
									).put(
										"method", "DELETE"
									).build()
								).put(
									"update",
									HashMapBuilder.put(
										"href",
										baseURI.getScheme() + "://" +
											baseURI.getAuthority() +
												"/o/c/caseresults/" +
													value.get("c_caseResultId_")
									).put(
										"method", "PUT"
									).build()
								).build()));
					}

					testrayCaseResult.setComment(
						GetterUtil.getString(value.get("comment_")));
					testrayCaseResult.setError(
						GetterUtil.getString(value.get("errors_")));
					testrayCaseResult.setIssues(
						GetterUtil.getString(value.get("issues_")));
					testrayCaseResult.setPriority(
						GetterUtil.getLong(value.get("priority_")));
					testrayCaseResult.setStatus(
						GetterUtil.getString(value.get("dueStatus_")));
					testrayCaseResult.setTestrayCaseName(
						GetterUtil.getString(value.get("caseName")));
					testrayCaseResult.setTestrayCaseResultId(
						GetterUtil.getLong(value.get("c_caseResultId_")));
					testrayCaseResult.setTestrayCaseTypeName(
						GetterUtil.getString(value.get("caseTypeName")));
					testrayCaseResult.setTestrayCaseTypeName(
						GetterUtil.getString(value.get("caseTypeName")));
					testrayCaseResult.setTestrayRunName(
						GetterUtil.getString(value.get("runName")));
					testrayCaseResult.setTestrayRunNumber(
						GetterUtil.getLong(value.get("runNumber")));
					testrayCaseResult.setTestrayComponentName(
						GetterUtil.getString(value.get("componentName")));
					testrayCaseResult.setTestrayTeamName(
						GetterUtil.getString(value.get("teamName")));

					if (Validator.isNotNull(value.get("uuid_"))) {
						FullNameGenerator fullNameGenerator =
							FullNameGeneratorFactory.getInstance();

						testrayCaseResult.setUserName(
							fullNameGenerator.getFullName(
								GetterUtil.getString(value.get("firstName")),
								GetterUtil.getString(value.get("middleName")),
								GetterUtil.getString(value.get("lastName"))));

						if (Validator.isNotNull(value.get("portraitId"))) {
							testrayCaseResult.setUserImgUrl(
								UserConstants.getPortraitURL(
									"/image", true,
									GetterUtil.getLong(value.get("portraitId")),
									GetterUtil.getString(value.get("uuid_"))));
						}
					}

					return testrayCaseResult;
				}),
			pagination, totalCount);
	}

	@Reference
	private RoleLocalService _roleLocalService;

}