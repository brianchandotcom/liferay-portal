/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.testray.rest.dto.v1_0.TestrayBuildAutofill;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.resource.v1_0.TestrayBuildAutofillResource;

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
	properties = "OSGI-INF/liferay/rest/v1_0/testray-build-autofill.properties",
	scope = ServiceScope.PROTOTYPE, service = TestrayBuildAutofillResource.class
)
public class TestrayBuildAutofillResourceImpl
	extends BaseTestrayBuildAutofillResourceImpl {

	@Override
	public TestrayBuildAutofill postTestrayBuildAutofill(
			Long testrayBuildId1, Long testrayBuildId2)
		throws Exception {

		StringBundler sb = new StringBundler(24);

		sb.append("select cr1.c_caseResultId_ as c_caseResultId_1,");
		sb.append("cr1.dueStatus_ as dueStatus_1, cr1.errors_ as errors_1,");
		sb.append("cr1.issues_ as issues_1, cr1.r_userToCaseResults_userId ");
		sb.append("as r_userToCaseResults_userId_1,");
		sb.append("cr2.c_caseResultId_ as c_caseResultId_2,");
		sb.append("cr2.dueStatus_ as dueStatus_2, cr2.errors_ as errors_2,");
		sb.append("cr2.issues_ as issues_2, cr2.r_userToCaseResults_userId ");
		sb.append("as r_userToCaseResults_userId_2 FROM ");
		sb.append("O_[%COMPANY_ID%]_Build b1, O_[%COMPANY_ID%]_Build b2, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr1, ");
		sb.append("O_[%COMPANY_ID%]_CaseResult cr2, O_[%COMPANY_ID%]_Case ");
		sb.append("c1, O_[%COMPANY_ID%]_Case c2 WHERE b1.c_buildId_ = ");
		sb.append("cr1.r_buildToCaseResult_c_buildId AND c1.c_caseId_ = ");
		sb.append("cr1.r_caseToCaseResult_c_caseId AND b1.c_buildId_ = ? AND ");
		sb.append("b2.c_buildId_ = ? AND b2.c_buildId_ = ");
		sb.append("cr2.r_buildToCaseResult_c_buildId AND c2.c_caseId_ = ");
		sb.append("cr2.r_caseToCaseResult_c_caseId AND c1.c_caseId_ = ");
		sb.append("c2.c_caseId_ AND cr1.errors_ = cr2.errors_ AND ( ");
		sb.append("((cr1.issues_ != '') AND (cr2.issues_ = '')) OR ");
		sb.append("((cr1.r_userToCaseResults_userId != 0) AND ");
		sb.append("(cr2.r_userToCaseResults_userId = 0)) OR ((cr1.issues_ = ");
		sb.append("'') AND (cr2.issues_ != '')) OR ");
		sb.append("((cr1.r_userToCaseResults_userId = 0) AND ");
		sb.append("(cr2.r_userToCaseResults_userId != 0)))");

		String sql = sb.toString();

		List<Object> params = new ArrayList<>();

		params.add(testrayBuildId1);
		params.add(testrayBuildId2);

		List<Map<String, Object>> values = TestrayUtil.runSQL(
			StringUtil.replace(
				sql, "[%COMPANY_ID%]",
				String.valueOf(contextCompany.getCompanyId())),
			params);

		for (Map<String, Object> map : values) {
			_autofillTestrayCaseResult(map);
		}

		TestrayBuildAutofill testrayBuildAutofill = new TestrayBuildAutofill();

		testrayBuildAutofill.setCaseAmount(values.size());

		return testrayBuildAutofill;
	}

	private void _autofillTestrayCaseResult(Map<String, Object> map)
		throws Exception {

		int sourceCaseResultIndex = 1;
		int targetCaseResultIndex = 2;

		if ((GetterUtil.getString(map.get("issues_1")) == null) ||
			(GetterUtil.getLong(map.get("r_userToCaseResults_userId_1")) ==
				0)) {

			sourceCaseResultIndex = 2;
			targetCaseResultIndex = 1;
		}

		ObjectEntry targetObjectEntry = _objectEntryLocalService.getObjectEntry(
			GetterUtil.getLong(
				map.get("c_caseResultId_" + targetCaseResultIndex)));

		Map<String, Serializable> values = targetObjectEntry.getValues();

		values.put(
			"dueStatus",
			GetterUtil.getString(
				map.get("dueStatus_" + sourceCaseResultIndex)));
		values.put(
			"issues",
			GetterUtil.getString(map.get("issues_" + sourceCaseResultIndex)));
		values.put(
			"r_userToCaseResults_userId",
			GetterUtil.getLong(
				map.get(
					"r_userToCaseResults_userId_" + sourceCaseResultIndex)));

		_objectEntryLocalService.updateObjectEntry(
			contextUser.getUserId(),
			GetterUtil.getLong(
				map.get("c_caseResultId_" + targetCaseResultIndex)),
			values, _serviceContextHelper.getServiceContext());
	}

	@Reference
	private ObjectEntryLocalService _objectEntryLocalService;

	@Reference
	private ServiceContextHelper _serviceContextHelper;

}