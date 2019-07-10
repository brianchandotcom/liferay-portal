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

import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.webserver.WebServerServletTokenUtil;
import com.liferay.taglib.ui.MessageTag;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Mueller (7.2.x)
 */
@Deprecated
public class LayoutIconTag extends IncludeTag implements BodyTag {

	public static void doTag(Layout layout, PageContext pageContext)
		throws JspException {

		if ((layout == null) || !layout.isIconImage()) {
			return;
		}

		JspWriter jspWriter = pageContext.getOut();

		try {
			jspWriter.write("<img alt=\"");

			MessageTag.doTag(
				null, false, true, "page-icon", false, false, false,
				pageContext);

			jspWriter.write("\" class=\"layout-logo-");
			jspWriter.write(String.valueOf(layout.getPlid()));
			jspWriter.write("\" src=\"");

			ThemeDisplay themeDisplay = (ThemeDisplay)pageContext.getAttribute(
				WebKeys.THEME_DISPLAY);

			if (themeDisplay == null) {
				ServletRequest servletRequest = pageContext.getRequest();

				themeDisplay = (ThemeDisplay)servletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);
			}

			jspWriter.write(themeDisplay.getPathImage());

			jspWriter.write("/layout_icon?img_id=");
			jspWriter.write(String.valueOf(layout.getIconImageId()));
			jspWriter.write("&t=");
			jspWriter.write(
				WebServerServletTokenUtil.getToken(layout.getIconImageId()));
			jspWriter.write("\" />");
		}
		catch (Exception e) {
			throw new JspException(e);
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #doTag(Layout,
	 *             PageContext)}
	 */
	@Deprecated
	public static void doTag(
			Layout layout, ServletContext servletContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		doTag(
			_PAGE, layout, servletContext, httpServletRequest,
			httpServletResponse);
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link #doTag(Layout,
	 *             PageContext)}
	 */
	@Deprecated
	public static void doTag(
			String page, Layout layout, ServletContext servletContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		setRequestAttributes(httpServletRequest, layout);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(httpServletRequest, httpServletResponse);
	}

	public static void setRequestAttributes(
		HttpServletRequest httpServletRequest, Layout layout) {

		httpServletRequest.setAttribute(
			"liferay-theme:layout-icon:layout", layout);
	}

	@Override
	public int doStartTag() throws JspException {
		doTag(_layout, pageContext);

		return SKIP_BODY;
	}

	public Layout getLayout() {
		return _layout;
	}

	public void setLayout(Layout layout) {
		_layout = layout;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	private static final String _PAGE =
		"/html/taglib/theme/layout_icon/page.jsp";

	private Layout _layout;

}