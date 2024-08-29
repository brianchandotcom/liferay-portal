/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.testray.rest.manager;

import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.testray.rest.dto.v1_0.TestrayCache;

import java.time.OffsetDateTime;

import java.util.Map;

import org.w3c.dom.Document;

/**
 * @author José Abelenda
 */
public interface TestrayManager {

	public Map<String, Object> fetchTestrayCaseFlakyParameters(
			long companyId, OffsetDateTime offsetDateTime, long testrayCaseId)
		throws Exception;

	public void loadTestrayCache(
			long companyId, TestrayCache testrayCache, long userId)
		throws Exception;

	public void processArchive(
			long companyId, byte[] bytes, String fileName,
			ServiceContext serviceContext, long userId)
		throws Exception;

	public void processDocument(
			long companyId, Document document, String fileName, long fileSize,
			ServiceContext serviceContext, TestrayCache testrayCache,
			long userId)
		throws Exception;

	public void updateTestrayBuildSummary(
			long companyId, long testrayBuildId, long userId)
		throws Exception;

}