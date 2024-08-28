/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.testray.rest.dto.v1_0.TestrayCaseResultComparison;
import com.liferay.testray.rest.dto.v1_0.TestrayRunComparison;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.internal.util.comparator.TestrayCaseResultComparisonComparator;
import com.liferay.testray.rest.resource.v1_0.TestrayRunComparisonResource;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
	properties = "OSGI-INF/liferay/rest/v1_0/testray-run-comparison.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayRunComparisonResource.class
)
public class TestrayRunComparisonResourceImpl
	extends BaseTestrayRunComparisonResourceImpl {

	@Override
	public TestrayRunComparison getTestrayRunComparison(
			Long testrayRunId1, Long testrayRunId2, Filter filter)
		throws Exception {

		TestrayRunComparison testrayRunComparison = new TestrayRunComparison();

		List<TestrayCaseResultComparison> testrayCaseResultComparisons =
			_getTestrayCaseResultComparisons(
				ParamUtil.getString(contextHttpServletRequest, "filter"), null,
				null, null, null, null, null, testrayRunId1, testrayRunId2);

		testrayRunComparison.setResults(
			ListUtil.fromArray(
				HashMapBuilder.<String, Object>put(
					"Components",
					_getTestrayComponentComparisons(
						testrayCaseResultComparisons)
				).put(
					"Runs",
					_getTestrayRunComparisons(testrayCaseResultComparisons)
				).put(
					"Teams",
					_getTestrayTeamComparisons(
						_getObjectEntriesMap(
							_getComponentFilterString(
								"teamToComponents/", testrayRunId1,
								testrayRunId2),
							"c_teamId", "Team"),
						testrayCaseResultComparisons)
				).build()
			).toArray());

		return testrayRunComparison;
	}

	@Override
	public Object getTestrayRunComparisonByTestrayRoutineIdTestrayRoutine(
			Long testrayRoutineId)
		throws Exception {

		StringBundler sb = new StringBundler(8);

		sb.append("select (select cr.r_runToCaseResult_c_runId from ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr where ");
		sb.append("cr.r_buildToCaseResult_c_buildId = b.c_buildId_ group by ");
		sb.append("cr.r_runToCaseResult_c_runId order by ");
		sb.append("count(cr.c_caseResultId_) desc limit 1) as id from ");
		sb.append("O_[%COMPANY_ID%]_Build b where ");
		sb.append("b.r_routineToBuilds_c_routineId = ? order by b.dueDate_ ");
		sb.append("desc limit 2");

		String sql = StringUtil.replace(
			sb.toString(), "[%COMPANY_ID%]",
			String.valueOf(contextCompany.getCompanyId()));

		List<Map<String, Object>> values = TestrayUtil.executeQuery(
			sql, Collections.singletonList(testrayRoutineId));

		if (ListUtil.isEmpty(values) || (values.size() < 2)) {
			throw new Exception("Unable to find more than one run");
		}

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		jsonObject.put(
			"run1", values.get(0)
		).put(
			"run2", values.get(1)
		);

		return jsonObject;
	}

	@Override
	public TestrayRunComparison getTestrayRunComparisonRun(
			Long testrayRunId1, Long testrayRunId2,
			String testrayCaseResultError1, String testrayCaseResultError2,
			String testrayCaseResultIssue1, String testrayCaseResultIssue2,
			String testrayCaseResultStatus1, String testrayCaseResultStatus2,
			Filter filter)
		throws Exception {

		TestrayRunComparison testrayRunComparison = new TestrayRunComparison();

		testrayRunComparison.setResults(
			ListUtil.fromArray(
				HashMapBuilder.<String, Object>put(
					"Runs",
					_getTestrayRunComparisons(
						_getTestrayCaseResultComparisons(
							ParamUtil.getString(
								contextHttpServletRequest, "filter"),
							testrayCaseResultError1, testrayCaseResultError2,
							testrayCaseResultIssue1, testrayCaseResultIssue2,
							testrayCaseResultStatus1, testrayCaseResultStatus2,
							testrayRunId1, testrayRunId2))
				).build()
			).toArray());

		return testrayRunComparison;
	}

	@Override
	public Page<TestrayCaseResultComparison>
			getTestrayRunComparisonTestrayCaseResultComparisonsPage(
				Long testrayRunId1, Long testrayRunId2,
				String testrayCaseResultError1, String testrayCaseResultError2,
				String testrayCaseResultIssue1, String testrayCaseResultIssue2,
				String testrayCaseResultStatus1,
				String testrayCaseResultStatus2, Filter filter,
				Pagination pagination)
		throws Exception {

		Map<String, Map<String, Serializable>> testrayCaseMap =
			_getObjectEntriesMap(
				_getCaseFilterString(testrayRunId1, testrayRunId2), "c_caseId",
				"Case");

		List<TestrayCaseResultComparison> testrayCaseResultComparisons =
			_getTestrayCaseResultComparisons(
				ParamUtil.getString(contextHttpServletRequest, "filter"),
				testrayCaseResultError1, testrayCaseResultError2,
				testrayCaseResultIssue1, testrayCaseResultIssue2,
				testrayCaseResultStatus1, testrayCaseResultStatus2,
				testrayRunId1, testrayRunId2);

		for (TestrayCaseResultComparison testrayCaseResultComparison :
				testrayCaseResultComparisons) {

			Map<String, Serializable> testrayCase = testrayCaseMap.get(
				String.valueOf(testrayCaseResultComparison.getTestrayCaseId()));

			testrayCaseResultComparison.setName(
				String.valueOf(testrayCase.get("name")));

			testrayCaseResultComparison.setPriority(
				GetterUtil.getInteger(testrayCase.get("priority")));
		}

		return Page.of(
			ListUtil.fromArray(
				ArrayUtil.subset(
					ListUtil.sort(
						testrayCaseResultComparisons,
						new TestrayCaseResultComparisonComparator()
					).toArray(
						new TestrayCaseResultComparison[0]
					),
					pagination.getStartPosition(),
					Math.min(
						pagination.getEndPosition(),
						testrayCaseResultComparisons.size()))),
			pagination, testrayCaseResultComparisons.size());
	}

	private void _compareTestrayCaseResultStatus(
		Map<String, Map<String, Integer>> entityComparisonsMap,
		TestrayCaseResultComparison testrayCaseResultComparison) {

		Map<String, Integer> map = entityComparisonsMap.get(
			testrayCaseResultComparison.getStatus1());

		if (map == null) {
			map = new HashMap<>();

			entityComparisonsMap.put(
				String.valueOf(testrayCaseResultComparison.getStatus1()), map);
		}

		Integer count = map.get(testrayCaseResultComparison.getStatus2());

		if (count == null) {
			count = 0;
		}

		map.put(testrayCaseResultComparison.getStatus2(), count + 1);
	}

	private String _getCaseFilterString(
		long testrayRunId1, long testrayRunId2) {

		StringBundler sb = new StringBundler(5);

		sb.append("caseToCaseResult/r_runToCaseResult_c_runId eq '");
		sb.append(String.valueOf(testrayRunId1));
		sb.append("' or caseToCaseResult/r_runToCaseResult_c_runId eq '");
		sb.append(String.valueOf(testrayRunId2));
		sb.append("'");

		return sb.toString();
	}

	private String _getComponentFilterString(
		String prefix, long testrayRunId1, long testrayRunId2) {

		StringBundler sb = new StringBundler(8);

		sb.append(prefix);
		sb.append("componentToCaseResult/r_runToCaseResult_c_runId eq '");
		sb.append(testrayRunId1);
		sb.append("' or ");
		sb.append(prefix);
		sb.append("componentToCaseResult/r_runToCaseResult_c_runId eq '");
		sb.append(testrayRunId2);
		sb.append("'");

		return sb.toString();
	}

	private Map<String, Map<String, Serializable>> _getObjectEntriesMap(
			String filterString, String key, String objectDefinitionShortName)
		throws Exception {

		Map<String, Map<String, Serializable>> map = new HashMap<>();

		ObjectDefinition objectDefinition =
			_objectDefinitionLocalService.getObjectDefinition(
				contextCompany.getCompanyId(),
				"C_" + objectDefinitionShortName);

		_objectEntryLocalService.getValuesList(
			0, contextCompany.getCompanyId(), contextUser.getUserId(),
			objectDefinition.getObjectDefinitionId(), null,
			_filterFactory.create(filterString, objectDefinition), null, -1, -1,
			null
		).forEach(
			entry -> map.put(String.valueOf(entry.get(key)), entry)
		);

		return map;
	}

	private TestrayCaseResultComparison _getTestrayCaseResultComparison(
		Map<String, Serializable> testrayCaseResultMap1,
		Map<String, Serializable> testrayCaseResultMap2,
		Map<String, Map<String, Serializable>> testrayComponentsMap) {

		TestrayCaseResultComparison testrayCaseResultComparison =
			new TestrayCaseResultComparison();

		Map<String, Serializable> map = testrayCaseResultMap1;

		if (testrayCaseResultMap1 == null) {
			map = testrayCaseResultMap2;
		}

		testrayCaseResultComparison.setStatus1("DIDNOTRUN");
		testrayCaseResultComparison.setStatus2("DIDNOTRUN");

		if (testrayCaseResultMap1 != null) {
			testrayCaseResultComparison.setError1(
				GetterUtil.getString(testrayCaseResultMap1.get("errors")));
			testrayCaseResultComparison.setIssue1(
				GetterUtil.getString(testrayCaseResultMap1.get("issues")));
			testrayCaseResultComparison.setId1(
				GetterUtil.getLong(
					testrayCaseResultMap1.get("c_caseResultId")));
			testrayCaseResultComparison.setStatus1(
				String.valueOf(testrayCaseResultMap1.get("dueStatus")));
		}

		if (testrayCaseResultMap2 != null) {
			testrayCaseResultComparison.setError2(
				GetterUtil.getString(testrayCaseResultMap2.get("errors")));
			testrayCaseResultComparison.setIssue2(
				GetterUtil.getString(testrayCaseResultMap2.get("issues")));
			testrayCaseResultComparison.setId2(
				GetterUtil.getLong(
					testrayCaseResultMap2.get("c_caseResultId")));
			testrayCaseResultComparison.setStatus2(
				String.valueOf(testrayCaseResultMap2.get("dueStatus")));
		}

		testrayCaseResultComparison.setTestrayCaseId(
			GetterUtil.getLong(map.get("r_caseToCaseResult_c_caseId")));
		testrayCaseResultComparison.setTestrayComponentName(
			String.valueOf(
				testrayComponentsMap.get(
					String.valueOf(
						map.get("r_componentToCaseResult_c_componentId"))
				).get(
					"name"
				)));
		testrayCaseResultComparison.setTestrayTeamId(
			GetterUtil.getLong(
				testrayComponentsMap.get(
					String.valueOf(
						map.get("r_componentToCaseResult_c_componentId"))
				).get(
					"r_teamToComponents_c_teamId"
				)));

		return testrayCaseResultComparison;
	}

	private List<TestrayCaseResultComparison> _getTestrayCaseResultComparisons(
			String filterString, String testrayCaseResultError1,
			String testrayCaseResultError2, String testrayCaseResultIssue1,
			String testrayCaseResultIssue2, String testrayCaseResultStatus1,
			String testrayCaseResultStatus2, long testrayRunId1,
			long testrayRunId2)
		throws Exception {

		List<TestrayCaseResultComparison> testrayCaseResultComparisons =
			new ArrayList<>();

		Map<String, Map<String, Serializable>> testrayCaseResultsMap1 =
			_getObjectEntriesMap(
				_merge(
					ListUtil.fromArray(
						"runId eq '" + testrayRunId1 + "'", filterString,
						testrayCaseResultError1, testrayCaseResultIssue1,
						testrayCaseResultStatus1),
					" and "),
				"r_caseToCaseResult_c_caseId", "CaseResult");
		Map<String, Map<String, Serializable>> testrayCaseResultsMap2 =
			_getObjectEntriesMap(
				_merge(
					ListUtil.fromArray(
						"runId eq '" + testrayRunId2 + "'", filterString,
						testrayCaseResultError2, testrayCaseResultIssue2,
						testrayCaseResultStatus2),
					" and "),
				"r_caseToCaseResult_c_caseId", "CaseResult");
		Map<String, Map<String, Serializable>> testrayComponentsMap =
			_getObjectEntriesMap(
				_getComponentFilterString("", testrayRunId1, testrayRunId2),
				"c_componentId", "Component");

		for (Map.Entry<String, Map<String, Serializable>> entry :
				testrayCaseResultsMap1.entrySet()) {

			Map<String, Serializable> testrayCaseResult =
				testrayCaseResultsMap2.remove(entry.getKey());

			if ((Validator.isNotNull(testrayCaseResultStatus1) &&
				 testrayCaseResultStatus1.contains("DIDNOTRUN")) ||
				((testrayCaseResult != null) &&
				 Validator.isNotNull(testrayCaseResultStatus2) &&
				 !testrayCaseResultStatus2.contains(
					 String.valueOf(testrayCaseResult.get("dueStatus")))) ||
				((testrayCaseResult == null) &&
				 (Validator.isNotNull(testrayCaseResultError2) ||
				  Validator.isNotNull(testrayCaseResultIssue2) ||
				  (Validator.isNotNull(testrayCaseResultStatus2) &&
				   !testrayCaseResultStatus2.contains("DIDNOTRUN"))))) {

				continue;
			}

			testrayCaseResultComparisons.add(
				_getTestrayCaseResultComparison(
					entry.getValue(), testrayCaseResult, testrayComponentsMap));
		}

		for (Map.Entry<String, Map<String, Serializable>> entry :
				testrayCaseResultsMap2.entrySet()) {

			if ((Validator.isNotNull(testrayCaseResultStatus2) &&
				 testrayCaseResultStatus2.contains("DIDNOTRUN")) ||
				Validator.isNotNull(testrayCaseResultError1) ||
				Validator.isNotNull(testrayCaseResultIssue1) ||
				(Validator.isNotNull(testrayCaseResultStatus1) &&
				 !testrayCaseResultStatus1.contains("DIDNOTRUN"))) {

				continue;
			}

			testrayCaseResultComparisons.add(
				_getTestrayCaseResultComparison(
					null, entry.getValue(), testrayComponentsMap));
		}

		return testrayCaseResultComparisons;
	}

	private Map<String, Map<String, Map<String, Integer>>>
		_getTestrayComponentComparisons(
			List<TestrayCaseResultComparison> testrayCaseResultComparisons) {

		Map<String, Map<String, Map<String, Integer>>>
			testrayComponentComparisonsMap = new HashMap<>();

		for (TestrayCaseResultComparison testrayCaseResultComparison :
				testrayCaseResultComparisons) {

			Map<String, Map<String, Integer>> entityComparisonsMap =
				testrayComponentComparisonsMap.get(
					testrayCaseResultComparison.getTestrayComponentName());

			if (entityComparisonsMap == null) {
				entityComparisonsMap = new HashMap<>();

				testrayComponentComparisonsMap.put(
					String.valueOf(
						testrayCaseResultComparison.getTestrayComponentName()),
					entityComparisonsMap);
			}

			_compareTestrayCaseResultStatus(
				entityComparisonsMap, testrayCaseResultComparison);
		}

		return testrayComponentComparisonsMap;
	}

	private Map<String, Map<String, Integer>> _getTestrayRunComparisons(
		List<TestrayCaseResultComparison> testrayCaseResultComparisons) {

		Map<String, Map<String, Integer>> map = new HashMap<>();

		for (TestrayCaseResultComparison testrayCaseResultComparison :
				testrayCaseResultComparisons) {

			_compareTestrayCaseResultStatus(map, testrayCaseResultComparison);
		}

		return map;
	}

	private Map<String, Map<String, Map<String, Integer>>>
		_getTestrayTeamComparisons(
			Map<String, Map<String, Serializable>> testrayTeamsMap,
			List<TestrayCaseResultComparison> testrayCaseResultComparisons) {

		Map<String, Map<String, Map<String, Integer>>>
			testrayTeamComparisonsMap = new HashMap<>();

		for (TestrayCaseResultComparison testrayCaseResultComparison :
				testrayCaseResultComparisons) {

			Map<String, Serializable> testrayTeam = testrayTeamsMap.get(
				String.valueOf(testrayCaseResultComparison.getTestrayTeamId()));

			Map<String, Map<String, Integer>> testrayTeamComparison =
				testrayTeamComparisonsMap.get(testrayTeam.get("name"));

			if (testrayTeamComparison == null) {
				testrayTeamComparison = new HashMap<>();

				testrayTeamComparisonsMap.put(
					String.valueOf(testrayTeam.get("name")),
					testrayTeamComparison);
			}

			_compareTestrayCaseResultStatus(
				testrayTeamComparison, testrayCaseResultComparison);
		}

		return testrayTeamComparisonsMap;
	}

	private String _merge(Collection<?> collection, String delimiter) {
		StringBundler sb = new StringBundler(2 * collection.size());

		for (Object object : collection) {
			if (Validator.isNull(object)) {
				continue;
			}

			String objectString = String.valueOf(object);

			sb.append(objectString.trim());

			sb.append(delimiter);
		}

		if (!delimiter.isEmpty()) {
			sb.setIndex(sb.index() - 1);
		}

		return sb.toString();
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

}