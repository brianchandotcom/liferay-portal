/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class CookieUtil {

	public static String get(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap =
			(Map<String, Cookie>)request.getAttribute(_COOKIE_MAP);

		if (cookieMap == null) {
			Cookie[] cookies = request.getCookies();

			if (cookies == null) {
				cookieMap = Collections.emptyMap();
			}
			else {
				cookieMap = new HashMap<String, Cookie>(cookies.length * 4 / 3);

				for (Cookie cookie : cookies) {
					String cookieName =
						GetterUtil.getString(cookie.getName()).toUpperCase();

					cookieMap.put(cookieName, cookie);
				}
			}

			request.setAttribute(_COOKIE_MAP, cookieMap);
		}

		Cookie cookie = cookieMap.get(name.toUpperCase());

		if (cookie == null) {
			return null;
		}
		else {
			return cookie.getValue();
		}
	}

	private static final String _COOKIE_MAP = "_COOKIE_MAP";

}