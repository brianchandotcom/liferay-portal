/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.service;

import java.util.List;
import java.util.Map;

/**
 * Provides the remote service utility for Testray. This utility wraps
 * <code>com.liferay.testray.service.impl.TestrayServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Nilton Vieira
 * @see TestrayService
 * @generated
 */
public class TestrayServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.testray.service.impl.TestrayServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static List<Map<String, Object>> compareTestrayRuns(
			long companyId, String testrayCasePriorities, long testrayRun1Id,
			long testrayRun2Id, long testrayTeamId)
		throws Exception {

		return getService().compareTestrayRuns(
			companyId, testrayCasePriorities, testrayRun1Id, testrayRun2Id,
			testrayTeamId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static TestrayService getService() {
		return _service;
	}

	public static void setService(TestrayService service) {
		_service = service;
	}

	private static volatile TestrayService _service;

}