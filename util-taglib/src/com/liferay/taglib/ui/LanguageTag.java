/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.taglib.util.IncludeTag;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class LanguageTag extends IncludeTag {

	public static final int LIST_ICON = 0;

	public static final int LIST_LONG_TEXT = 1;

	public static final int LIST_SHORT_TEXT = 2;

	public static final int SELECT_BOX = 3;

	public void setDisplayCurrentLocale(boolean displayCurrentLocale) {
		_displayCurrentLocale = displayCurrentLocale;
	}

	public void setDisplayStyle(int displayStyle) {
		_displayStyle = displayStyle;
	}

	public void setFormAction(String formAction) {
		_formAction = formAction;
	}

	public void setFormName(String formName) {
		_formName = formName;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	public void setLanguageIds(String[] languageIds) {
		_languageIds = languageIds;
	}

	public void setName(String name) {
		_name = name;
	}

	@Override
	protected void cleanUp() {
		_displayCurrentLocale = true;
		_displayStyle = LIST_ICON;
		_formAction = null;
		_formName = "fm";
		_languageId = null;
		_languageIds = null;
		_name = "languageId";
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute(
			"liferay-ui:language:displayCurrentLocale",
			String.valueOf(_displayCurrentLocale));
		request.setAttribute(
			"liferay-ui:language:displayStyle", String.valueOf(_displayStyle));
		request.setAttribute("liferay-ui:language:formAction", _formAction);
		request.setAttribute("liferay-ui:language:formName", _formName);
		request.setAttribute("liferay-ui:language:languageId", _languageId);

		Locale[] locales = null;

		if (ArrayUtil.isEmpty(_languageIds)) {
			ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
				WebKeys.THEME_DISPLAY);

			locales = LanguageUtil.getAvailableLocales(
				themeDisplay.getSiteGroupId());
		}
		else {
			locales = LocaleUtil.fromLanguageIds(_languageIds);
		}

		request.setAttribute("liferay-ui:language:locales", locales);
		request.setAttribute("liferay-ui:language:name", _name);
	}

	private static final String _PAGE = "/html/taglib/ui/language/page.jsp";

	private boolean _displayCurrentLocale = true;
	private int _displayStyle = LIST_ICON;
	private String _formAction;
	private String _formName = "fm";
	private String _languageId;
	private String[] _languageIds;
	private String _name = "languageId";

}