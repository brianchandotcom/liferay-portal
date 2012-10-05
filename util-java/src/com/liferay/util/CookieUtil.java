/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.util;

import com.liferay.portal.kernel.util.CookieKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @deprecated {@link com.liferay.portal.kernel.util.CookieKeys}

 */
public class CookieUtil {

	public static String get(HttpServletRequest request, String name) {
		return get(request, name, true);
	}

	public static String get(
		HttpServletRequest request, String name, boolean toUpperCase) {

		return CookieKeys.get(request, name, toUpperCase);
	}

}