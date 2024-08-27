/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.asset.kernel.exception.NoSuchEntryException;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.testray.rest.dto.v1_0.TestraySubtask;
import com.liferay.testray.rest.dto.v1_0.TestrayTestFlow;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.resource.v1_0.TestrayTestFlowResource;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Nilton Vieira
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/testray-test-flow.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayTestFlowResource.class
)
public class TestrayTestFlowResourceImpl
	extends BaseTestrayTestFlowResourceImpl {

	@Override
	public Page<TestraySubtask> getTestrayTestFlowTestraySubtaskPage(
			String error, String issues, String name, Boolean noIssues,
			String status, String testrayComponentIds, Long testrayTaskId,
			String testrayTeamIds, String testrayUserId, Pagination pagination)
		throws Exception {

		StringBundler sb = new StringBundler(69);

		sb.append("select * from ( select count( ");
		sb.append("cr.r_subtaskToCaseResults_c_subtaskId) as ");
		sb.append("caseResultAmount, s.c_subtaskId_, s.dueStatus_, s.errors_,");
		sb.append("s.issues_, s.score_, s.name_, null as firstName, null as ");
		sb.append("userId, null as lastName, null as middleName, null as ");
		sb.append("uuid_, null as portraitId, ta.c_taskId_ from ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult_x crx, ");
		sb.append("O_[%COMPANY_ID%]_Component c, O_[%COMPANY_ID%]_Team t, ");
		sb.append("O_[%COMPANY_ID%]_Task ta, O_[%COMPANY_ID%]_Subtask s  ");
		sb.append("where cr.c_caseResultId_ = crx.c_caseResultId_ and ");
		sb.append("cr.r_componentToCaseResult_c_componentId = ");
		sb.append("c.c_componentId_ and c.r_teamToComponents_c_teamId = ");
		sb.append("t.c_teamId_ and cr.r_subtaskToCaseResults_c_subtaskId = ");
		sb.append("s.c_subtaskId_ and s.dueStatus_ <> 'merged' and ");
		sb.append("s.r_userToSubtasks_userId = 0 and ta.c_taskId_ = ");
		sb.append("s.r_taskToSubtasks_c_taskId ");

		List<Object> params = new ArrayList<>();

		if (Validator.isNotNull(error)) {
			sb.append("and s.errors_ like ? ");
			params.add("%" + error + "%");
		}

		if (Validator.isNotNull(issues)) {
			sb.append("and s.issues_ like ? ");
			params.add("%" + issues + "%");
		}

		if (Validator.isNotNull(name)) {
			sb.append("and s.name_ = ? ");
			params.add(name);
		}

		if (noIssues != null) {
			sb.append("and (s.issues_ is null or s.issues_ = '') ");
		}

		if (Validator.isNotNull(status)) {
			sb.append("and s.dueStatus_ in (");
			sb.append(TestrayUtil.interpolateParams(params, status));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayComponentIds)) {
			sb.append("and c.c_componentId_ in (");
			sb.append(
				TestrayUtil.interpolateParams(params, testrayComponentIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayTaskId)) {
			sb.append("and ta.c_taskId_ = ? ");
			params.add(testrayTaskId);
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and t.c_teamId_ in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayUserId)) {
			sb.append("and s.r_userToSubtasks_userId = ? ");
			params.add(testrayUserId);
		}

		sb.append("group by s.c_subtaskId_, s.dueStatus_, s.errors_, ");
		sb.append("s.issues_, s.score_, s.name_ union all select ");
		sb.append("count(cr.r_subtaskToCaseResults_c_subtaskId) as ");
		sb.append("caseResultAmount, s.c_subtaskId_, s.dueStatus_, ");
		sb.append("s.errors_, s.issues_, s.score_, s.name_, u.firstName, ");
		sb.append("u.userId, u.lastName, u.middleName, u.uuid_, ");
		sb.append("u.portraitId, ta.c_taskId_ from ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult_x crx, ");
		sb.append("O_[%COMPANY_ID%]_Component c, O_[%COMPANY_ID%]_Team t, ");
		sb.append("O_[%COMPANY_ID%]_Task ta, O_[%COMPANY_ID%]_Subtask s, ");
		sb.append("User_ u where crx.c_caseResultId_ = cr.c_caseResultId_ ");
		sb.append("and cr.r_componentToCaseResult_c_componentId = ");
		sb.append("c.c_componentId_ and c.r_teamToComponents_c_teamId = ");
		sb.append("t.c_teamId_ and cr.r_subtaskToCaseResults_c_subtaskId = ");
		sb.append("s.c_subtaskId_ and s.dueStatus_ <> 'merged' and ");
		sb.append("ta.c_taskId_ = s.r_taskToSubtasks_c_taskId and u.userId = ");
		sb.append("s.r_userToSubtasks_userId ");

		if (Validator.isNotNull(error)) {
			sb.append("and s.errors_ like ? ");
			params.add("%" + error + "%");
		}

		if (Validator.isNotNull(issues)) {
			sb.append("and s.issues_ like ? ");
			params.add("%" + issues + "%");
		}

		if (Validator.isNotNull(name)) {
			sb.append("and s.name_ = ? ");
			params.add(name);
		}

		if (noIssues != null) {
			sb.append("and (s.issues_ is null or s.issues_ = '') ");
		}

		if (Validator.isNotNull(status)) {
			sb.append("and s.dueStatus_ in (");
			sb.append(TestrayUtil.interpolateParams(params, status));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayComponentIds)) {
			sb.append("and c.c_componentId_ in (");
			sb.append(
				TestrayUtil.interpolateParams(params, testrayComponentIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayTaskId)) {
			sb.append("and ta.c_taskId_ = ? ");
			params.add(testrayTaskId);
		}

		if (Validator.isNotNull(testrayTeamIds)) {
			sb.append("and t.c_teamId_ in (");
			sb.append(TestrayUtil.interpolateParams(params, testrayTeamIds));
			sb.append(") ");
		}

		if (Validator.isNotNull(testrayUserId)) {
			sb.append("and s.r_userToSubtasks_userId = ? ");
			params.add(testrayUserId);
		}

		sb.append("group by s.c_subtaskId_, s.dueStatus_, s.errors_, ");
		sb.append("s.issues_, s.score_, s.name_, u.firstName, u.lastName, ");
		sb.append("u.middleName, u.userId, u.uuid_, u.portraitId ) as ");
		sb.append("subtasks order by c_subtaskId_ asc ");

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
				value -> new TestraySubtask() {
					{
						caseResultAmount = GetterUtil.getLong(
							value.get("caseresultamount"));
						error = GetterUtil.getString(value.get("errors_"));
						id = GetterUtil.getLong(value.get("c_subtaskid_"));
						issues = GetterUtil.getString(value.get("issues_"));
						name = GetterUtil.getString(value.get("name_"));
						score = GetterUtil.getLong(value.get("score_"));
						status = GetterUtil.getString(value.get("duestatus_"));
						testrayTaskId = GetterUtil.getLong(
							value.get("c_taskid_"));
						userId = GetterUtil.getLong(value.get("userid"));

						setUserName(
							() -> {
								FullNameGenerator fullNameGenerator =
									FullNameGeneratorFactory.getInstance();

								return fullNameGenerator.getFullName(
									GetterUtil.getString(
										value.get("firstname")),
									GetterUtil.getString(
										value.get("middlename")),
									GetterUtil.getString(
										value.get("lastname")));
							});
						setUserPortraitUrl(
							() -> {
								long portraitId = GetterUtil.getLong(
									value.get("portraitid"));

								if (portraitId == 0) {
									return null;
								}

								return UserConstants.getPortraitURL(
									"/image", true, portraitId,
									GetterUtil.getString(value.get("uuid_")));
							});
					}
				}),
			pagination, totalCount);
	}

	@Override
	public TestrayTestFlow postTestrayTestFlow(Long testrayTaskId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				contextCompany.getCompanyId(), "C_Build");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, contextCompany.getCompanyId(), contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(), null,
				_filterFactory.create(
					"buildToTasks/id eq '" + testrayTaskId + "'",
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		if (ListUtil.isEmpty(valuesList)) {
			throw new NoSuchEntryException();
		}

		Map<String, Serializable> testrayBuild = valuesList.get(0);

		StringBundler sb = new StringBundler(9);

		sb.append("select cr.errors_ , sum(c.priority_) as score from ");
		sb.append("lportal.O_[%COMPANY_ID%]_CaseResult cr, ");
		sb.append("lportal.O_[%COMPANY_ID%]_Case c where cr.errors_ is not ");
		sb.append("null and cr.errors_ != '' and ");
		sb.append("cr.r_caseToCaseResult_c_caseId = c.c_caseId_ and ");
		sb.append("cr.r_buildToCaseResult_c_buildId = ? group by cr.errors_ ");
		sb.append("order by score desc");

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			StringUtil.replace(
				sb.toString(), "[%COMPANY_ID%]",
				String.valueOf(contextCompany.getCompanyId())),
			ListUtil.fromArray(
				GetterUtil.getLong(testrayBuild.get("c_buildid"))));

		objectDefinition = _objectDefinitionLocalService.getObjectDefinition(
			contextCompany.getCompanyId(), "C_Subtask");
		int testraySubtasksAmount = 0;

		for (Map<String, Object> value : values) {
			testraySubtasksAmount++;

			ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
				contextUser.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"dueStatus", "OPEN"
				).put(
					"errors", String.valueOf(value.get("errors_"))
				).put(
					"name", "ST-" + testraySubtasksAmount
				).put(
					"number", testraySubtasksAmount
				).put(
					"r_taskToSubtasks_c_taskId", testrayTaskId
				).put(
					"score", String.valueOf(value.get("score"))
				).build(),
				_serviceContextHelper.getServiceContext());

			sb = new StringBundler();

			sb.append("update O_[%COMPANY_ID%]_CaseResult set ");
			sb.append("r_subtaskToCaseResults_c_subtaskId = ? where ");
			sb.append("r_buildToCaseResult_c_buildId = ? and errors_ = ?");

			TestrayUtil.executeUpdate(
				StringUtil.replace(
					sb.toString(), "[%COMPANY_ID%]",
					String.valueOf(contextCompany.getCompanyId())),
				ListUtil.fromArray(
					objectEntry.getObjectEntryId(),
					GetterUtil.getLong(testrayBuild.get("c_buildid")),
					String.valueOf(value.get("errors_"))));
		}

		TestrayTestFlow testrayTestFlow = new TestrayTestFlow();

		testrayTestFlow.setSubtaskAmount(testraySubtasksAmount);

		return testrayTestFlow;
	}

	@Override
	public TestrayTestFlow putTestrayTestFlowByTestraySubtaskIdTestraySubtask(
			Long testraySubtaskId, TestrayTestFlow testrayTestFlow)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("update O_[%COMPANY_ID%]_CaseResult set ");
		sb.append("r_userToCaseResults_userId = ? ");

		List<Object> params = new ArrayList<>();

		params.add(testrayTestFlow.getUserId());

		if (Validator.isNotNull(testrayTestFlow.getDueStatus())) {
			sb.append(", dueStatus_ = ? ");
			params.add(testrayTestFlow.getDueStatus());
		}

		if (Validator.isNotNull(testrayTestFlow.getIssues())) {
			sb.append(", issues_ = ? ");
			params.add(testrayTestFlow.getIssues());
		}

		if (Validator.isNotNull(testrayTestFlow.getComment())) {
			sb.append(", comment_ = ?, mbMessageId_ = ?, mbThreadId_ = ? ");
			params.add(testrayTestFlow.getComment());
			params.add(testrayTestFlow.getMbMessageId());
			params.add(testrayTestFlow.getMbThreadId());
		}

		sb.append("where r_subtaskToCaseResults_c_subtaskId = ?");

		params.add(testraySubtaskId);

		testrayTestFlow.setCaseResultAmount(
			TestrayUtil.executeUpdate(
				StringUtil.replace(
					sb.toString(), "[%COMPANY_ID%]",
					String.valueOf(contextCompany.getCompanyId())),
				params));

		EntityCacheUtil.clearCache();

		return testrayTestFlow;
	}

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}