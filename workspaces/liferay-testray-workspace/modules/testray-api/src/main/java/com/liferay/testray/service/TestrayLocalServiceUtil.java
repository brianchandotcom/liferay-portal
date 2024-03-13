/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.service;

import java.util.List;
import java.util.Map;

/**
 * Provides the local service utility for Testray. This utility wraps
 * <code>com.liferay.testray.service.impl.TestrayLocalServiceImpl</code> and
 * is an access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Nilton Vieira
 * @see TestrayLocalService
 * @generated
 */
public class TestrayLocalServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.testray.service.impl.TestrayLocalServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
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

	public static TestrayLocalService getService() {
		return _service;
	}

	public static void setService(TestrayLocalService service) {
		_service = service;
	}

	private static volatile TestrayLocalService _service;

}