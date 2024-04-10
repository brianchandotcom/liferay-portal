/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.testray.rest.dto.v1_0.TestrayBuildAutofill;
import com.liferay.testray.rest.internal.util.TestrayUtil;
import com.liferay.testray.rest.resource.v1_0.TestrayBuildAutofillResource;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
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

		sb.append("SELECT cr1.c_caseResultId_ AS c_caseResultId_1,");
		sb.append("cr1.dueStatus_ AS dueStatus_1, cr1.errors_ AS errors_1,");
		sb.append("cr1.issues_ AS issues_1, cr1.r_userToCaseResults_userId ");
		sb.append("AS r_userToCaseResults_userId_1,");
		sb.append("cr2.c_caseResultId_ AS c_caseResultId_2,");
		sb.append("cr2.dueStatus_ AS dueStatus_2, cr2.errors_ AS errors_2,");
		sb.append("cr2.issues_ AS issues_2, cr2.r_userToCaseResults_userId ");
		sb.append("AS r_userToCaseResults_userId_2 FROM ");
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
			_autofillCaseResult(map);
		}

		TestrayBuildAutofill testrayBuildAutofill = new TestrayBuildAutofill();

		testrayBuildAutofill.setCaseAmount(values.size());

		return testrayBuildAutofill;
	}

}