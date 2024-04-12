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
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.testray.rest.dto.v1_0.TestrayCaseResult;
import com.liferay.testray.rest.dto.v1_0.TestrayRunComparison;
import com.liferay.testray.rest.resource.v1_0.TestrayRunComparisonResource;

import java.io.Serializable;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

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

		Set<TestrayCaseResult> set = _getMergedTestrayCaseResults(
			ParamUtil.getString(contextHttpServletRequest, "filter"), null,
			null, null, null, null, null, testrayRunId1, testrayRunId2);

		TestrayRunComparison testrayRunComparison = new TestrayRunComparison();

		testrayRunComparison.setResults(
			ListUtil.fromArray(
				HashMapBuilder.<String, Object>put(
					"Components", _getTestrayComponentComparisons(set)
				).put(
					"Runs", _getTestrayRunComparisons(set)
				).put(
					"Teams",
					_getTestrayTeamComparisons(
						_getObjectEntriesMap(
							_getComponentFilterString(
								"teamToComponents/", testrayRunId1,
								testrayRunId2),
							"c_teamId", "Team"),
						set)
				).build()
			).toArray());

		return testrayRunComparison;
	}

	@Override
	public TestrayRunComparison getTestrayRunComparisonDetail(
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
						_getMergedTestrayCaseResults(
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

	private void _compareTestrayCaseResultStatus(
		Map<String, Map<String, Integer>> entityComparisonsMap,
		TestrayCaseResult testrayCaseResult) {

		Map<String, Integer> map = entityComparisonsMap.get(
			testrayCaseResult.getStatus1());

		if (map == null) {
			map = new HashMap<>();

			entityComparisonsMap.put(
				String.valueOf(testrayCaseResult.getStatus1()), map);
		}

		Integer count = map.get(testrayCaseResult.getStatus2());

		if (count == null) {
			count = 0;
		}

		map.put(testrayCaseResult.getStatus2(), count + 1);
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

	private Set<TestrayCaseResult> _getMergedTestrayCaseResults(
			String filter, String testrayCaseResultError1,
			String testrayCaseResultError2, String testrayCaseResultIssue1,
			String testrayCaseResultIssue2, String testrayCaseResultStatus1,
			String testrayCaseResultStatus2, long testrayRunId1,
			long testrayRunId2)
		throws Exception {

		Set<TestrayCaseResult> set = new HashSet<>();

		Map<String, Map<String, Serializable>> testrayCaseResultsMap1 =
			_getObjectEntriesMap(
				_merge(
					ListUtil.fromArray(
						"runId eq '" + testrayRunId1 + "'", filter,
						testrayCaseResultError1, testrayCaseResultIssue1,
						testrayCaseResultStatus1),
					" and "),
				"r_caseToCaseResult_c_caseId", "CaseResult");
		Map<String, Map<String, Serializable>> testrayCaseResultsMap2 =
			_getObjectEntriesMap(
				_merge(
					ListUtil.fromArray(
						"runId eq '" + testrayRunId2 + "'", filter,
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

			set.add(
				_mergeTestrayCaseResults(
					entry.getValue(),
					testrayCaseResultsMap2.remove(entry.getKey()),
					testrayComponentsMap));
		}

		for (Map.Entry<String, Map<String, Serializable>> entry :
				testrayCaseResultsMap2.entrySet()) {

			set.add(
				_mergeTestrayCaseResults(
					null, entry.getValue(), testrayComponentsMap));
		}

		return set;
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
			objectDefinition.getObjectDefinitionId(),
			_filterFactory.create(filterString, objectDefinition), null, -1, -1,
			null
		).forEach(
			entry -> map.put(String.valueOf(entry.get(key)), entry)
		);

		return map;
	}

	private long _getTestrayCaseResultId(
		Map<String, Serializable> testrayCaseResultMap) {

		if (testrayCaseResultMap == null) {
			return 0;
		}

		return GetterUtil.getLong(testrayCaseResultMap.get("c_caseResultId"));
	}

	private String _getTestrayCaseResultStatus(
		Map<String, Serializable> testrayCaseResultMap) {

		if (testrayCaseResultMap == null) {
			return "DIDNOTRUN";
		}

		String dueStatus = String.valueOf(
			testrayCaseResultMap.get("dueStatus"));

		if (Objects.equals(dueStatus, "UNTESTED")) {
			return "DIDNOTRUN";
		}

		return dueStatus;
	}

	private Map<String, Map<String, Map<String, Integer>>>
		_getTestrayComponentComparisons(Set<TestrayCaseResult> set) {

		Map<String, Map<String, Map<String, Integer>>>
			testrayComponentComparisonsMap = new HashMap<>();

		for (TestrayCaseResult testrayCaseResult : set) {
			Map<String, Map<String, Integer>> entityComparisonsMap =
				testrayComponentComparisonsMap.get(
					testrayCaseResult.getTestrayComponentName());

			if (entityComparisonsMap == null) {
				entityComparisonsMap = new HashMap<>();

				testrayComponentComparisonsMap.put(
					String.valueOf(testrayCaseResult.getTestrayComponentName()),
					entityComparisonsMap);
			}

			_compareTestrayCaseResultStatus(
				entityComparisonsMap, testrayCaseResult);
		}

		return testrayComponentComparisonsMap;
	}

	private Map<String, Map<String, Integer>> _getTestrayRunComparisons(
		Set<TestrayCaseResult> set) {

		Map<String, Map<String, Integer>> map = new HashMap<>();

		for (TestrayCaseResult testrayCaseResult : set) {
			_compareTestrayCaseResultStatus(map, testrayCaseResult);
		}

		return map;
	}

	private Map<String, Map<String, Map<String, Integer>>>
		_getTestrayTeamComparisons(
			Map<String, Map<String, Serializable>> testrayTeamsMap,
			Set<TestrayCaseResult> set) {

		Map<String, Map<String, Map<String, Integer>>>
			testrayTeamComparisonsMap = new HashMap<>();

		for (TestrayCaseResult testrayCaseResult : set) {
			Map<String, Serializable> testrayTeam = testrayTeamsMap.get(
				String.valueOf(testrayCaseResult.getTestrayTeamId()));

			Map<String, Map<String, Integer>> testrayTeamComparison =
				testrayTeamComparisonsMap.get(testrayTeam.get("name"));

			if (testrayTeamComparison == null) {
				testrayTeamComparison = new HashMap<>();

				testrayTeamComparisonsMap.put(
					String.valueOf(testrayTeam.get("name")),
					testrayTeamComparison);
			}

			_compareTestrayCaseResultStatus(
				testrayTeamComparison, testrayCaseResult);
		}

		return testrayTeamComparisonsMap;
	}

	private String _merge(Collection<?> col, String delimiter) {
		StringBundler sb = new StringBundler(2 * col.size());

		for (Object object : col) {
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

	private TestrayCaseResult _mergeTestrayCaseResults(
		Map<String, Serializable> testrayCaseResultMap1,
		Map<String, Serializable> testrayCaseResultMap2,
		Map<String, Map<String, Serializable>> testrayComponentsMap) {

		Map<String, Serializable> map = testrayCaseResultMap1;

		if (testrayCaseResultMap1 == null) {
			map = testrayCaseResultMap2;
		}

		TestrayCaseResult testrayCaseResult = new TestrayCaseResult();

		testrayCaseResult.setStatus1("DIDNOTRUN");
		testrayCaseResult.setStatus2("DIDNOTRUN");

		if (testrayCaseResultMap1 != null) {
			testrayCaseResult.setError1(
				GetterUtil.getString(testrayCaseResultMap1.get("errors")));
			testrayCaseResult.setIssue1(
				GetterUtil.getString(testrayCaseResultMap1.get("issues")));
			testrayCaseResult.setId1(
				GetterUtil.getLong(
					testrayCaseResultMap1.get("c_caseResultId")));

			String dueStatus = String.valueOf(
				testrayCaseResultMap1.get("dueStatus"));

			if (!Objects.equals(dueStatus, "UNTESTED")) {
				testrayCaseResult.setStatus1(dueStatus);
			}
		}

		if (testrayCaseResultMap2 != null) {
			testrayCaseResult.setError2(
				GetterUtil.getString(testrayCaseResultMap2.get("errors")));
			testrayCaseResult.setIssue2(
				GetterUtil.getString(testrayCaseResultMap2.get("issues")));
			testrayCaseResult.setId2(
				GetterUtil.getLong(
					testrayCaseResultMap2.get("c_caseResultId")));

			String dueStatus = String.valueOf(
				testrayCaseResultMap2.get("dueStatus"));

			if (!Objects.equals(dueStatus, "UNTESTED")) {
				testrayCaseResult.setStatus2(dueStatus);
			}
		}

		testrayCaseResult.setTestrayCaseId(
			GetterUtil.getLong(map.get("r_caseToCaseResult_c_caseId")));
		testrayCaseResult.setTestrayComponentName(
			String.valueOf(
				testrayComponentsMap.get(
					String.valueOf(
						map.get("r_componentToCaseResult_c_componentId"))
				).get(
					"name"
				)));
		testrayCaseResult.setTestrayTeamId(
			GetterUtil.getLong(
				testrayComponentsMap.get(
					String.valueOf(
						map.get("r_componentToCaseResult_c_componentId"))
				).get(
					"r_teamToComponents_c_teamId"
				)));

		return testrayCaseResult;
	}

	@Reference(
		target = "(filter.factory.key=" + ObjectDefinitionConstants.STORAGE_TYPE_DEFAULT + ")"
	)
	private FilterFactory<Predicate> _filterFactory;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

}