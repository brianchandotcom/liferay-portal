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
import com.liferay.testray.rest.internal.util.comparator.TestrayCaseResultsComparator;
import com.liferay.testray.rest.resource.v1_0.TestrayTestFlowResource;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

		StringBundler sb = new StringBundler(9);

		sb.append("select cr.c_caseResultId_, cr.dueStatus_, cr.errors_, ");
		sb.append("cr.issues_, c.priority_ , ");
		sb.append("cr.r_buildToCaseResult_c_buildId, ");
		sb.append("cr.r_caseToCaseResult_c_caseId from ");
		sb.append("lportal.O_[%COMPANY_ID%]_CaseResult cr, ");
		sb.append("O_[%COMPANY_ID%]_Case c where cr.errors_ is not null and ");
		sb.append("cr.errors_ != '' and cr.r_buildToCaseResult_c_buildId = ? ");
		sb.append("and cr.r_caseToCaseResult_c_caseId = c.c_caseId_ order by ");
		sb.append("cr.errors_ ");

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			StringUtil.replace(
				sb.toString(), "[%COMPANY_ID%]",
				String.valueOf(contextCompany.getCompanyId())),
			ListUtil.fromArray(
				_getTestrayBuildIdByTestrayTaskId(testrayTaskId)));

		String errors = null;
		Map<String, List<Map<String, Object>>> map = new HashMap<>();
		List<List<Map<String, Object>>> testrayCaseResultsList =
			new ArrayList<>();

		for (Map<String, Object> value : values) {
			if (!Objects.equals(errors, value.get("errors_"))) {
				testrayCaseResultsList.addAll(map.values());

				map = new HashMap<>();
			}

			List<Map<String, Object>> testrayCaseResultsIssues = map.get(
				String.valueOf(value.get("issues_")));

			if (testrayCaseResultsIssues == null) {
				testrayCaseResultsIssues = new ArrayList<>();

				map.put(
					String.valueOf(value.get("issues_")),
					testrayCaseResultsIssues);
			}

			testrayCaseResultsIssues.add(value);

			errors = String.valueOf(value.get("errors_"));
		}

		testrayCaseResultsList.addAll(map.values());

		int testraySubtasksAmount = 0;

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				contextCompany.getCompanyId(), "C_Subtask");

		for (List<Map<String, Object>> testrayCaseResults :
				ListUtil.sort(
					testrayCaseResultsList,
					new TestrayCaseResultsComparator())) {

			testraySubtasksAmount++;

			Map<String, Object> firstTestrayCaseResult = testrayCaseResults.get(
				0);

			ObjectEntry objectEntry = _objectEntryLocalService.addObjectEntry(
				contextUser.getUserId(), 0,
				objectDefinition.getObjectDefinitionId(),
				HashMapBuilder.<String, Serializable>put(
					"dueStatus", "OPEN"
				).put(
					"errors",
					String.valueOf(firstTestrayCaseResult.get("errors_"))
				).put(
					"name", "ST-" + testraySubtasksAmount
				).put(
					"number", testraySubtasksAmount
				).put(
					"r_taskToSubtasks_c_taskId", testrayTaskId
				).put(
					"score", _getTestraySubtaskScore(testrayCaseResults)
				).build(),
				_serviceContextHelper.getServiceContext());

			for (Map<String, Object> testrayCaseResult : testrayCaseResults) {
				testrayCaseResult.put(
					"r_subtaskToCaseResults_c_subtaskId",
					objectEntry.getObjectEntryId());

				Map<String, Serializable> serializableTestrayCaseResult =
					new HashMap<>();

				for (Map.Entry<String, Object> entry :
						testrayCaseResult.entrySet()) {

					serializableTestrayCaseResult.put(
						entry.getKey(), String.valueOf(entry.getValue()));
				}

				_objectEntryLocalService.updateObjectEntry(
					contextUser.getUserId(),
					GetterUtil.getLong(
						testrayCaseResult.get("c_caseResultId_")),
					serializableTestrayCaseResult,
					_serviceContextHelper.getServiceContext());
			}
		}

		TestrayTestFlow testrayTestFlow = new TestrayTestFlow();

		testrayTestFlow.setTestraySubtasksAmount(testraySubtasksAmount);

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

		int rows = TestrayUtil.executeUpdate(
			StringUtil.replace(
				sb.toString(), "[%COMPANY_ID%]",
				String.valueOf(contextCompany.getCompanyId())),
			params);

		EntityCacheUtil.clearCache();

		testrayTestFlow.setCaseResultAmount(rows);

		return testrayTestFlow;
	}

	private long _getTestrayBuildIdByTestrayTaskId(long testrayTaskId)
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

		return GetterUtil.getLong(testrayBuild.get("c_buildId"));
	}

	private int _getTestraySubtaskScore(
			List<Map<String, Object>> testrayCaseResults)
		throws Exception {

		int testraySubtaskScore = 0;

		for (Map<String, Object> testrayCaseResult : testrayCaseResults) {
			testraySubtaskScore += GetterUtil.getInteger(
				testrayCaseResult.get("priority_"));
		}

		return testraySubtaskScore;
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