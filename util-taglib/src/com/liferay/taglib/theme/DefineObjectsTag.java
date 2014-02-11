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

package com.liferay.taglib.theme;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.PortletSettings;
import com.liferay.portlet.PortletSettingsFactoryUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * @author Brian Wing Shun Chan
 */
public class DefineObjectsTag extends TagSupport {

	@Override
	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay == null) {
			return SKIP_BODY;
		}

		pageContext.setAttribute("themeDisplay", themeDisplay);
		pageContext.setAttribute("company", themeDisplay.getCompany());
		pageContext.setAttribute("account", themeDisplay.getAccount());
		pageContext.setAttribute("user", themeDisplay.getUser());
		pageContext.setAttribute("realUser", themeDisplay.getRealUser());
		pageContext.setAttribute("contact", themeDisplay.getContact());

		if (themeDisplay.getLayout() != null) {
			pageContext.setAttribute("layout", themeDisplay.getLayout());
		}

		if (themeDisplay.getLayouts() != null) {
			pageContext.setAttribute("layouts", themeDisplay.getLayouts());
		}

		pageContext.setAttribute("plid", new Long(themeDisplay.getPlid()));

		if (themeDisplay.getLayoutTypePortlet() != null) {
			pageContext.setAttribute(
				"layoutTypePortlet", themeDisplay.getLayoutTypePortlet());
		}

		pageContext.setAttribute(
			"scopeGroupId", new Long(themeDisplay.getScopeGroupId()));
		pageContext.setAttribute(
			"permissionChecker", themeDisplay.getPermissionChecker());
		pageContext.setAttribute("locale", themeDisplay.getLocale());
		pageContext.setAttribute("timeZone", themeDisplay.getTimeZone());
		pageContext.setAttribute("theme", themeDisplay.getTheme());
		pageContext.setAttribute("colorScheme", themeDisplay.getColorScheme());

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		pageContext.setAttribute("portletDisplay", portletDisplay);

		String portletId = portletDisplay.getId();

		try {
			PortletSettings portletCompanyPortletSettings =
				PortletSettingsFactoryUtil.getCompanyPortletSettings(
					themeDisplay.getCompanyId(), portletId);

			pageContext.setAttribute(
				"portletCompanyPortletSettings", portletCompanyPortletSettings);

			PortletSettings portletInstancePortletSettings =
				PortletSettingsFactoryUtil.getPortletInstancePortletSettings(
					themeDisplay.getLayout(), portletId);

			pageContext.setAttribute(
				"portletInstancePortletSettings",
				portletInstancePortletSettings);

			PortletSettings portletGroupPortletSettings =
				PortletSettingsFactoryUtil.getGroupPortletSettings(
					themeDisplay.getSiteGroupId(), portletId);

			pageContext.setAttribute(
				"portletGroupPortletSettings", portletGroupPortletSettings);
		}
		catch (SystemException se) {
			throw new RuntimeException(se);
		}
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}

		// Deprecated

		pageContext.setAttribute(
			"portletGroupId", new Long(themeDisplay.getScopeGroupId()));

		return SKIP_BODY;
	}

}