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

package com.liferay.taglib.portletext;

import com.liferay.portal.kernel.servlet.taglib.FileAvailabilityUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.taglib.ui.IconTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IconPortletTag extends IconTag {

	public void setPortlet(Portlet portlet) {
		_portlet = portlet;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_portlet = null;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		super.setAttributes(request);

		request.setAttribute("liferay-portlet:icon_portlet:portlet", _portlet);
	}

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(getServletContext(), _PAGE)) {
			return _PAGE;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)pageContext.getAttribute(
			"themeDisplay");

		String message = null;
		String src = null;

		if (_portlet != null) {
			message = PortalUtil.getPortletTitle(
				_portlet, pageContext.getServletContext(),
				themeDisplay.getLocale());
			src = _portlet.getStaticResourcePath().concat(_portlet.getIcon());
		}
		else {
			PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

			if (!portletDisplay.isShowPortletIcon()) {
				return null;
			}

			message = portletDisplay.getTitle();
			src = portletDisplay.getURLPortlet();
		}

		setAlt(StringPool.BLANK);
		setMessage(message);
		setSrc(src);

		return super.getPage();
	}

	private static final String _PAGE =
		"/html/taglib/portlet/icon_portlet/page.jsp";

	private Portlet _portlet;

}