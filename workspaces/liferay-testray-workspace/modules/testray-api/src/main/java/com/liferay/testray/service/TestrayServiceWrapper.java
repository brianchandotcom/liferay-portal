/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link TestrayService}.
 *
 * @author Nilton Vieira
 * @see TestrayService
 * @generated
 */
public class TestrayServiceWrapper
	implements ServiceWrapper<TestrayService>, TestrayService {

	public TestrayServiceWrapper() {
		this(null);
	}

	public TestrayServiceWrapper(TestrayService testrayService) {
		_testrayService = testrayService;
	}

	@Override
	public java.util.List<java.util.Map<String, Object>> compareTestrayRuns(
			long companyId, String testrayCasePriorities, long testrayRun1Id,
			long testrayRun2Id, long testrayTeamId)
		throws Exception {

		return _testrayService.compareTestrayRuns(
			companyId, testrayCasePriorities, testrayRun1Id, testrayRun2Id,
			testrayTeamId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _testrayService.getOSGiServiceIdentifier();
	}

	@Override
	public TestrayService getWrappedService() {
		return _testrayService;
	}

	@Override
	public void setWrappedService(TestrayService testrayService) {
		_testrayService = testrayService;
	}

	private TestrayService _testrayService;

}