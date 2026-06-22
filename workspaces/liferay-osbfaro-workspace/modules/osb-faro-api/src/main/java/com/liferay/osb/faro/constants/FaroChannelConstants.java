/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.constants;

import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author André Miranda
 */
public class FaroChannelConstants {

	public static final int PERMISSION_ALL_USERS = 0;

	public static final int PERMISSION_SELECT_USERS = 1;

	public static Map<String, Integer> getPermissionTypes() {
		return _permissionTypes;
	}

	private static final Map<String, Integer> _permissionTypes =
		HashMapBuilder.put(
			"allUsers", PERMISSION_ALL_USERS
		).put(
			"selectUsers", PERMISSION_SELECT_USERS
		).build();

}