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

package com.liferay.taglib.theme;

import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineObjectsTag extends TagSupport {

	@Override
	public int doStartTag() {
		HttpServletRequest httpServletRequest =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return SKIP_BODY;
		}

		if (!GetterUtil.getBoolean(
				pageContext.getAttribute(WebKeys.THEME_DEFINE_OBJECTS), true)) {

			pageContext.setAttribute("themeDisplay", themeDisplay);

			return SKIP_BODY;
		}

		pageContext.setAttribute("account", themeDisplay.getAccount());
		pageContext.setAttribute("colorScheme", themeDisplay.getColorScheme());
		pageContext.setAttribute("company", themeDisplay.getCompany());
		pageContext.setAttribute("contact", themeDisplay.getContact());

		if (themeDisplay.getLayout() != null) {
			pageContext.setAttribute("layout", themeDisplay.getLayout());
		}

		if (themeDisplay.getLayouts() != null) {
			pageContext.setAttribute("layouts", themeDisplay.getLayouts());
		}

		if (themeDisplay.getLayoutTypePortlet() != null) {
			pageContext.setAttribute(
				"layoutTypePortlet", themeDisplay.getLayoutTypePortlet());
		}

		pageContext.setAttribute("locale", themeDisplay.getLocale());
		pageContext.setAttribute(
			"permissionChecker", themeDisplay.getPermissionChecker());
		pageContext.setAttribute("plid", Long.valueOf(themeDisplay.getPlid()));
		pageContext.setAttribute(
			"portletDisplay", themeDisplay.getPortletDisplay());
		pageContext.setAttribute("realUser", themeDisplay.getRealUser());
		pageContext.setAttribute(
			"scopeGroupId", Long.valueOf(themeDisplay.getScopeGroupId()));
		pageContext.setAttribute("theme", themeDisplay.getTheme());
		pageContext.setAttribute("themeDisplay", themeDisplay);
		pageContext.setAttribute("timeZone", themeDisplay.getTimeZone());
		pageContext.setAttribute("user", themeDisplay.getUser());

		// Deprecated

		pageContext.setAttribute(
			"portletGroupId", themeDisplay.getScopeGroupId());

		return SKIP_BODY;
	}

}