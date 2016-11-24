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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.BodyTag;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class LayoutIconTag
	extends com.liferay.taglib.util.IncludeTag implements BodyTag {

	public static void doTag(
			Layout layout, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		doTag(_PAGE, layout, servletContext, request, response);
	}

	public static void doTag(
			String page, Layout layout, ServletContext servletContext,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		setRequestAttributes(request, layout);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(page);

		requestDispatcher.include(request, response);
	}

	public static void setRequestAttributes(
		HttpServletRequest request, Layout layout) {

		request.setAttribute("liferay-theme:layout-icon:layout", layout);
	}

	@Override
	public int doStartTag() {
		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();

		setRequestAttributes(request, _layout);

		return EVAL_BODY_BUFFERED;
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