/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link TestrayLocalService}.
 *
 * @author Nilton Vieira
 * @see TestrayLocalService
 * @generated
 */
public class TestrayLocalServiceWrapper
	implements ServiceWrapper<TestrayLocalService>, TestrayLocalService {

	public TestrayLocalServiceWrapper() {
		this(null);
	}

	public TestrayLocalServiceWrapper(TestrayLocalService testrayLocalService) {
		_testrayLocalService = testrayLocalService;
	}

	@Override
	public java.util.List<java.util.Map<String, Object>> compareTestrayRuns(
			long companyId, String testrayCasePriorities, long testrayRun1Id,
			long testrayRun2Id, long testrayTeamId)
		throws Exception {

		return _testrayLocalService.compareTestrayRuns(
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
		return _testrayLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public TestrayLocalService getWrappedService() {
		return _testrayLocalService;
	}

	@Override
	public void setWrappedService(TestrayLocalService testrayLocalService) {
		_testrayLocalService = testrayLocalService;
	}

	private TestrayLocalService _testrayLocalService;

}