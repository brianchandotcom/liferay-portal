/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.controller;

import com.liferay.osb.faro.web.internal.model.display.FaroResultsDisplay;
import com.liferay.osb.faro.web.internal.search.FaroSearchContext;

/**
 * @author Shinn Lok
 */
public interface FaroController {

	public int[] getEntityTypes();

	public FaroResultsDisplay search(
			long groupId, FaroSearchContext faroSearchContext)
		throws Exception;

}