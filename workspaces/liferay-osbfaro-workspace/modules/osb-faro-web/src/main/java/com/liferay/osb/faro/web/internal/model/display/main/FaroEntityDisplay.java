/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.model.display.main;

import java.util.List;
import java.util.Map;

/**
 * @author Shinn Lok
 */
public interface FaroEntityDisplay {

	public void addProperties(List<String> propertyNames);

	public String getId();

	public String getName();

	public Map<String, Object> getProperties();

	public int getType();

}