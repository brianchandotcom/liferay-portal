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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.testray.rest.dto.v1_0.TestrayBuildAutofill;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.resource.v1_0.TestrayBuildAutofillResource;

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
	properties = "OSGI-INF/liferay/rest/v1_0/testray-build-autofill.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayBuildAutofillResource.class
)
public class TestrayBuildAutofillResourceImpl
	extends BaseTestrayBuildAutofillResourceImpl {

	@Override
	public TestrayBuildAutofill postTestrayBuildAutofill(
			Long testrayBuildId1, Long testrayBuildId2)
		throws Exception {

		int caseAmount = 0;

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				contextCompany.getCompanyId(), "C_CaseResult");

		Map<Long, List<Map<String, Serializable>>>
			testrayCaseResultsGroupedByTestrayCase1 =
				_getTestrayCaseResultsByTestrayBuildGroupedByTestrayCase(
					objectDefinition, testrayBuildId1);
		Map<Long, List<Map<String, Serializable>>>
			testrayCaseResultsGroupedByTestrayCase2 =
				_getTestrayCaseResultsByTestrayBuildGroupedByTestrayCase(
					objectDefinition, testrayBuildId2);

		for (Map.Entry<Long, List<Map<String, Serializable>>> entry :
				testrayCaseResultsGroupedByTestrayCase1.entrySet()) {

			List<Map<String, Serializable>> testrayCaseResults2 =
				testrayCaseResultsGroupedByTestrayCase2.get(entry.getKey());

			if (testrayCaseResults2 == null) {
				continue;
			}

			List<Map<String, Serializable>> testrayCaseResults1 =
				entry.getValue();

			for (Map<String, Serializable> testrayCaseResult1 :
					testrayCaseResults1) {

				for (Map<String, Serializable> testrayCaseResult2 :
						testrayCaseResults2) {

					if (!Objects.equals(
							String.valueOf(testrayCaseResult1.get("errors")),
							String.valueOf(testrayCaseResult2.get("errors")))) {

						continue;
					}

					ObjectEntry objectEntry = _autofillTestrayCaseResult(
						testrayCaseResult1, testrayCaseResult2);

					if (objectEntry != null) {
						caseAmount++;
					}
				}
			}
		}

		TestrayBuildAutofill testrayBuildAutofill = new TestrayBuildAutofill();

		testrayBuildAutofill.setCaseAmount(caseAmount);

		JSONObject jsonObject = _getTestrayRunIdsJSONObject(
			testrayBuildId1, testrayBuildId2);

		testrayBuildAutofill.setTestrayRunId1(
			jsonObject.getLong("testrayRunId1"));
		testrayBuildAutofill.setTestrayRunId2(
			jsonObject.getLong("testrayRunId2"));

		return testrayBuildAutofill;
	}

	private ObjectEntry _autofillTestrayCaseResult(
			Map<String, Serializable> testrayCaseResult1,
			Map<String, Serializable> testrayCaseResult2)
		throws Exception {

		Map<String, Serializable> targetTestrayCaseResult = null;
		Map<String, Serializable> sourceTestrayCaseResult = null;

		if (((Long)testrayCaseResult1.get("r_userToCaseResults_userId") > 0) &&
			Validator.isNotNull(testrayCaseResult1.get("issues")) &&
			((Long)testrayCaseResult2.get("r_userToCaseResults_userId") <= 0) &&
			Validator.isNull(testrayCaseResult2.get("issues"))) {

			sourceTestrayCaseResult = testrayCaseResult1;
			targetTestrayCaseResult = testrayCaseResult2;
		}
		else if (((Long)testrayCaseResult1.get("r_userToCaseResults_userId") <=
					0) &&
				 Validator.isNull(testrayCaseResult1.get("issues")) &&
				 ((Long)testrayCaseResult1.get("r_userToCaseResults_userId") >
					 0) &&
				 Validator.isNotNull(testrayCaseResult2.get("issues"))) {

			sourceTestrayCaseResult = testrayCaseResult2;
			targetTestrayCaseResult = testrayCaseResult1;
		}

		if (targetTestrayCaseResult == null) {
			return null;
		}

		targetTestrayCaseResult.put(
			"dueStatus", sourceTestrayCaseResult.get("dueStatus"));
		targetTestrayCaseResult.put(
			"r_userToCaseResults_userId",
			sourceTestrayCaseResult.get("r_userToCaseResults_userId"));
		targetTestrayCaseResult.put(
			"issues", String.valueOf(sourceTestrayCaseResult.get("issues")));

		return _objectEntryLocalService.updateObjectEntry(
			contextUser.getUserId(),
			GetterUtil.getLong(targetTestrayCaseResult.get("c_caseResultId")),
			targetTestrayCaseResult, _serviceContextHelper.getServiceContext());
	}

	private Map<Long, List<Map<String, Serializable>>>
			_getTestrayCaseResultsByTestrayBuildGroupedByTestrayCase(
				ObjectDefinition objectDefinition, long testrayBuildId1)
		throws Exception {

		Map<Long, List<Map<String, Serializable>>>
			testrayCaseResultsGroupedByTestrayCase = new HashMap<>();

		for (Map<String, Serializable> values :
				_objectEntryLocalService.getValuesList(
					0, contextCompany.getCompanyId(), contextUser.getUserId(),
					objectDefinition.getObjectDefinitionId(), null,
					_filterFactory.create(
						"buildId eq '" + testrayBuildId1 + "' and errors ne ''",
						objectDefinition),
					null, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			long testrayCaseId = (Long)values.get(
				"r_caseToCaseResult_c_caseId");

			List<Map<String, Serializable>> testrayCaseResults =
				testrayCaseResultsGroupedByTestrayCase.get(testrayCaseId);

			if (testrayCaseResults == null) {
				testrayCaseResults = new ArrayList<>();

				testrayCaseResultsGroupedByTestrayCase.put(
					testrayCaseId, testrayCaseResults);
			}

			testrayCaseResults.add(values);
		}

		return testrayCaseResultsGroupedByTestrayCase;
	}

	private JSONObject _getTestrayRunIdsJSONObject(
			Long testrayBuildId1, Long testrayBuildId2)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append("select (select cr.r_runToCaseResult_c_runId from ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr where ");
		sb.append("cr.r_buildToCaseResult_c_buildId = b.c_buildId_ group by ");
		sb.append("cr.r_runToCaseResult_c_runId order by ");
		sb.append("count(cr.c_caseResultId_) desc limit 1) as runId from ");
		sb.append("O_[%COMPANY_ID%]_Build b where b.c_buildId_ in (?, ?)");

		List<Object> params = new ArrayList<>();

		params.add(testrayBuildId1);
		params.add(testrayBuildId2);

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			sql, params);

		if (ListUtil.isEmpty(values) || (values.size() < 2)) {
			throw new Exception("Unable to find more than one run");
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"testrayRunId1",
			values.get(
				0
			).get(
				"runId"
			)
		).put(
			"testrayRunId2",
			values.get(
				1
			).get(
				"runId"
			)
		);

		return jsonObject;
	}

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}