/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.rest.filter.factory.FilterFactory;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.testray.rest.dto.v1_0.TestrayBuildAutofill;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.manager.TestrayManager;
import com.liferay.testray.rest.resource.v1_0.TestrayBuildAutofillResource;

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
	properties = "OSGI-INF/liferay/rest/v1_0/testray-build-autofill.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayBuildAutofillResource.class
)
public class TestrayBuildAutofillResourceImpl
	extends BaseTestrayBuildAutofillResourceImpl {

	@Override
	public TestrayBuildAutofill postTestrayBuildAutofill(
			Long testrayBuildId1, Long testrayBuildId2)
		throws Exception {

		TestrayBuildAutofill testrayBuildAutofill = new TestrayBuildAutofill();

		testrayBuildAutofill.setCaseAmount(
			_testrayManager.autofillTestrayBuilds(
				contextCompany.getCompanyId(), testrayBuildId1, testrayBuildId2,
				contextUser.getUserId()));

		JSONObject jsonObject = _getTestrayRunIdsJSONObject(
			testrayBuildId1, testrayBuildId2);

		testrayBuildAutofill.setTestrayRunId1(
			jsonObject.getLong("testrayRunId1"));
		testrayBuildAutofill.setTestrayRunId2(
			jsonObject.getLong("testrayRunId2"));

		return testrayBuildAutofill;
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
				"runid"
			)
		).put(
			"testrayRunId2",
			values.get(
				1
			).get(
				"runid"
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

	@Reference
	private TestrayManager _testrayManager;

}