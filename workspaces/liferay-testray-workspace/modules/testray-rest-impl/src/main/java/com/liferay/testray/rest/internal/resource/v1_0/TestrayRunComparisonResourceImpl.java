/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.internal.resource.v1_0;

import com.liferay.testray.rest.dto.v1_0.TestrayRunComparison;
import com.liferay.testray.rest.resource.v1_0.TestrayRunComparisonResource;

import org.osgi.service.component.annotations.Component;
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
			Long testrayRun1Id, Long testrayRun2Id,
			String testrayCasePriorities, Long testrayTeamId)
		throws Exception {

		return new TestrayRunComparison();
	}

}