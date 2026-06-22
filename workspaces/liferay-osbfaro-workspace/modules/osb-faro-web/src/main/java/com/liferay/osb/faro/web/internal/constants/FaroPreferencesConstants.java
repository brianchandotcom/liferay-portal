/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.web.internal.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class FaroPreferencesConstants {

	public static final String SCOPE_GROUP = "group";

	public static final String SCOPE_USER = "user";

	public static Map<String, Object> getScopes() {
		return _scopes;
	}

	private static final Map<String, Object> _scopes =
		HashMapBuilder.<String, Object>put(
			SCOPE_GROUP, SCOPE_GROUP
		).put(
			SCOPE_USER, SCOPE_USER
		).build();

}