/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
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
	public TestrayTestFlow postTestrayTestFlow(Long testrayTaskId)
		throws Exception {

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				contextCompany.getCompanyId(), "C_Build");

		List<Map<String, Serializable>> valuesList =
			_objectEntryLocalService.getValuesList(
				0, contextCompany.getCompanyId(), contextUser.getUserId(),
				objectDefinition.getObjectDefinitionId(),
				_filterFactory.create(
					"buildToTasks/id eq '" + testrayTaskId + "'",
					objectDefinition),
				null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Map<String, Serializable> testrayBuild = valuesList.get(0);

		long testrayBuildId = GetterUtil.getLong(testrayBuild.get("c_buildId"));

		StringBundler sb = new StringBundler(9);

		sb.append("select cr.errors_ , sum(c.priority_) as score from ");
		sb.append("lportal.O_[%COMPANY_ID%]_CaseResult cr, ");
		sb.append("lportal.O_[%COMPANY_ID%]_Case c where cr.errors_ is not ");
		sb.append("null and cr.errors_ != '' and cr.");
		sb.append("r_caseToCaseResult_c_caseId = c.c_caseId_ and cr.");
		sb.append("r_buildToCaseResult_c_buildId = ? group by cr.errors_ ");
		sb.append("order by score desc");

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			StringUtil.replace(
				sb.toString(), "[%COMPANY_ID%]",
				String.valueOf(contextCompany.getCompanyId())),
			ListUtil.fromArray(testrayBuildId));

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
					objectEntry.getObjectEntryId(), testrayBuildId,
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