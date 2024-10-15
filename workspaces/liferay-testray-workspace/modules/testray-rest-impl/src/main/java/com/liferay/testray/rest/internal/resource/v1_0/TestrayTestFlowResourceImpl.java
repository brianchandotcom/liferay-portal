/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.asset.kernel.exception.NoSuchEntryException;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.testray.rest.dto.v1_0.TestraySubtask;
import com.liferay.testray.rest.dto.v1_0.TestrayTestFlow;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.manager.TestrayManager;
import com.liferay.testray.rest.resource.v1_0.TestrayTestFlowResource;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Comparator;
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

		StringBundler sb = new StringBundler(34);

		sb.append("select count(cr.r_subtaskToCaseResults_c_subtaskId) as ");
		sb.append("caseResultAmount, s.c_subtaskId_, s.dueStatus_, s.errors_,");
		sb.append("s.issues_, s.score_, s.name_, u.firstName, u.userId, ");
		sb.append("u.lastName, u.middleName, u.uuid_, u.portraitId, ");
		sb.append("ta.c_taskId_ from o_[%COMPANY_ID%]_subtask s inner join ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr on ");
		sb.append("cr.r_subtaskToCaseResults_c_subtaskId = s.c_subtaskId_ ");
		sb.append("inner join o_[%COMPANY_ID%]_component c on ");
		sb.append("c.c_componentId_ = cr.");
		sb.append("r_componentToCaseResult_c_componentId inner join ");
		sb.append("o_[%COMPANY_ID%]_team t on c.r_teamToComponents_c_teamId ");
		sb.append("= t.c_teamId_ inner join o_[%COMPANY_ID%]_task ta on ta.");
		sb.append("c_taskId_ = s.r_taskToSubtasks_c_taskId left join User_ u ");
		sb.append("on u.userId = s.r_userToSubtasks_userId where ");
		sb.append("s.dueStatus_ != 'MERGED' ");

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
			params.add(GetterUtil.getLong(testrayUserId));
		}

		sb.append("group by s.c_subtaskId_, s.dueStatus_, s.errors_, ");
		sb.append("s.issues_, s.score_, s.name_, ta.c_taskid_, u.firstName, ");
		sb.append("u.lastName, u.middleName, u.userId, u.uuid_, u.portraitId ");
		sb.append("order by s.score_ desc, s.c_subtaskid_ ");

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

		TestrayTestFlow testrayTestFlow = new TestrayTestFlow();

		testrayTestFlow.setSubtaskAmount(
			_testrayManager.createTestraySubtasks(
				contextCompany.getCompanyId(),
				GetterUtil.getLong(testrayBuild.get("c_buildId")),
				testrayTaskId, contextUser.getUserId()));

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

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			testraySubtaskId);

		Map<String, Serializable> values = objectEntry.getValues();

		objectEntry = _objectEntryLocalService.getObjectEntry(
			GetterUtil.getLong(values.get("r_taskToSubtasks_c_taskId")));

		values = objectEntry.getValues();

		_testrayManager.updateTestrayBuildSummary(
			contextCompany.getCompanyId(),
			GetterUtil.getInteger(values.get("r_buildToTasks_c_buildId")),
			contextUser.getUserId());

		return testrayTestFlow;
	}

	@Override
	public Page<TestraySubtask> putTestrayTestFlowTestraySubtaskMergePage(
			TestraySubtask[] testraySubtasks)
		throws Exception {

		if (testraySubtasks.length < 2) {
			return Page.of(ListUtil.fromArray(testraySubtasks));
		}

		List<TestraySubtask> sortedTestraySubtasks = ListUtil.sort(
			ListUtil.fromArray(testraySubtasks),
			new Comparator<TestraySubtask>() {

				@Override
				public int compare(
					TestraySubtask testraySubtask1,
					TestraySubtask testraySubtask2) {

					return GetterUtil.getInteger(
						testraySubtask2.getScore() -
							testraySubtask1.getScore());
				}

			});

		TestraySubtask parentTestraySubtask = sortedTestraySubtasks.get(0);

		long totalScore = parentTestraySubtask.getScore();

		for (int i = 1; i < sortedTestraySubtasks.size(); i++) {
			TestraySubtask testraySubtask = sortedTestraySubtasks.get(i);

			totalScore += testraySubtask.getScore();

			Map<String, Serializable> values =
				_objectEntryLocalService.getValues(testraySubtask.getId());

			values.put("dueStatus", "MERGED");
			values.put(
				"r_mergedToTestraySubtask_c_subtaskId",
				parentTestraySubtask.getId());
			values.put("score", 0);

			if (Validator.isNull(values.get("issues"))) {
				values.put("issues", "");
			}

			_objectEntryLocalService.updateObjectEntry(
				contextUser.getUserId(), testraySubtask.getId(), values,
				new ServiceContext());

			List<Object> params = new ArrayList<>();

			StringBundler sb = new StringBundler(4);

			sb.append("update O_[%COMPANY_ID%]_CaseResult set ");
			sb.append("r_subtasktocaseresults_c_subtaskid = ? ");

			params.add(parentTestraySubtask.getId());

			if (Validator.isNotNull(parentTestraySubtask.getIssues())) {
				sb.append(", issues_ = ? ");
				params.add(parentTestraySubtask.getIssues());
			}

			sb.append("where r_subtasktocaseresults_c_subtaskid = ?");

			params.add(GetterUtil.getLong(values.get("c_subtaskId")));

			TestrayUtil.executeUpdate(
				StringUtil.replace(
					sb.toString(), "[%COMPANY_ID%]",
					String.valueOf(contextCompany.getCompanyId())),
				params);
		}

		Map<String, Serializable> values = _objectEntryLocalService.getValues(
			parentTestraySubtask.getId());

		values.put("score", totalScore);

		if (Validator.isNull(values.get("issues"))) {
			values.put("issues", "");
		}

		_objectEntryLocalService.updateObjectEntry(
			contextUser.getUserId(), parentTestraySubtask.getId(), values,
			new ServiceContext());

		EntityCacheUtil.clearCache();

		return Page.of(sortedTestraySubtasks);
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
	private TestrayManager _testrayManager;

}