/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.testray.service.base.TestrayServiceBaseImpl;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Nilton Vieira
 */
@Component(
	property = {
		"json.web.service.context.name=testray",
		"json.web.service.context.path=Testray"
	},
	service = AopService.class
)
public class TestrayServiceImpl extends TestrayServiceBaseImpl {

	public List<Map<String, Object>> compareTestrayRuns(
			long companyId, String testrayCasePriorities, long testrayRun1Id,
			long testrayRun2Id, long testrayTeamId)
		throws Exception {

		return testrayLocalService.compareTestrayRuns(
			companyId, testrayCasePriorities, testrayRun1Id, testrayRun2Id,
			testrayTeamId);
	}

}