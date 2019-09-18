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

package com.liferay.taglib.portletext;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.FileAvailabilityUtil;
import com.liferay.taglib.ui.IconTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class IconPortletTag extends IconTag {

	public Portlet getPortlet() {
		return _portlet;
	}

	public void setPortlet(Portlet portlet) {
		_portlet = portlet;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_portlet = null;
	}

	@Override
	protected String getPage() {
		if (FileAvailabilityUtil.isAvailable(getServletContext(), _PAGE)) {
			return _PAGE;
		}

		HttpServletRequest httpServletRequest = getRequest();

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String message = null;
		String src = null;

		if (_portlet != null) {
			message = PortalUtil.getPortletTitle(
				_portlet, pageContext.getServletContext(),
				themeDisplay.getLocale());

			if (Validator.isNotNull(_portlet.getIcon())) {
				String staticResourcePath = _portlet.getStaticResourcePath();

				src = staticResourcePath.concat(_portlet.getIcon());
			}
			else {
				src = themeDisplay.getPathContext() + "/html/icons/default.png";
			}
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
		setMessage(HtmlUtil.escape(message));
		setSrc(src);

		return super.getPage();
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		super.setAttributes(httpServletRequest);

		httpServletRequest.setAttribute(
			"liferay-portlet:icon_portlet:portlet", _portlet);
	}

	private static final String _PAGE =
		"/html/taglib/portlet/icon_portlet/page.jsp";

	private Portlet _portlet;

}