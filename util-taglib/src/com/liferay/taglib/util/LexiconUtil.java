/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.taglib.util;

import com.liferay.portal.kernel.model.User;

/**
 * @author Eudaldo Alonso
 */
public class LexiconUtil {

	public static String getUserColorCssClass(User user) {
		if (user == null) {
			return "user-icon-default";
		}

		return "user-icon-color-".concat(
			_NUMBERS[(int)(user.getUserId() % 10)]);
	}

	private static final String[] _NUMBERS = {
		"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"
	};

}