/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.stream.hub.utils;

import java.util.List;

/**
 * @author Mahmoud Hussein Tayem
 */
public class ListUtils {

	public static boolean isEmpty(List<?> list) {
		try {
			return list.isEmpty();
		}
		catch (Exception exception) {
			System.out.println(exception.getMessage());

			return false;
		}
	}

}