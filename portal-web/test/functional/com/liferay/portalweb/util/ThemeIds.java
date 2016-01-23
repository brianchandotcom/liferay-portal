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

package com.liferay.portalweb.util;

import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.model.Theme;
import com.liferay.portal.service.http.ThemeServiceHttp;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeIds {

	public static int getCount() {
		return _instance._getCount();
	}

	public static String getThemeId() {
		return _instance._getThemeId();
	}

	public static void iterate() {
		_instance._iterate();
	}

	private ThemeIds() {
		String[] themeIds = new String[0];

		try {
			if (TestPropsValues.THEME_IDS.length > 0) {
				themeIds = TestPropsValues.THEME_IDS;
			}
			else {
				HttpPrincipal httpPrincipal = new HttpPrincipal(
					TestPropsValues.PORTAL_URL);

				List<String> themeIdList = new ArrayList<>();

				List<Theme> themes = ThemeServiceHttp.getThemes(
					httpPrincipal, TestPropsValues.getCompanyId());

				for (Theme theme : themes) {
					if (!theme.isWapTheme()) {
						themeIdList.add(theme.getThemeId());
					}
				}

				themeIds = themeIdList.toArray(new String[themeIdList.size()]);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		_themeIds = themeIds;
	}

	private int _getCount() {
		return _themeIds.length;
	}

	private String _getThemeId() {
		return _themeIds[_pos];
	}

	private void _iterate() {
		_pos++;
	}

	private static final ThemeIds _instance = new ThemeIds();

	private int _pos;
	private final String[] _themeIds;

}