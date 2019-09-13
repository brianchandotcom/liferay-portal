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

package com.liferay.taglib.servlet;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspEngineInfo;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

/**
 * @author Matthew Tambara
 */
public class AutoCloseJspFactoryWrapper extends JspFactory {

	public AutoCloseJspFactoryWrapper(JspFactory jspFactory) {
		_jspFactory = jspFactory;
	}

	@Override
	public JspEngineInfo getEngineInfo() {
		return _jspFactory.getEngineInfo();
	}

	@Override
	public JspApplicationContext getJspApplicationContext(
		ServletContext servletContext) {

		return _jspFactory.getJspApplicationContext(servletContext);
	}

	@Override
	public PageContext getPageContext(
		Servlet servlet, ServletRequest servletRequest,
		ServletResponse servletResponse, String errorPageURL,
		boolean needsSession, int buffer, boolean autoflush) {

		PageContext pageContext = _jspFactory.getPageContext(
			servlet, servletRequest, servletResponse, errorPageURL,
			needsSession, buffer, autoflush);

		pageContext.setAttribute(
			AutoClosePageContextRegistry.AUTO_CLOSEABLE, Boolean.TRUE);

		return pageContext;
	}

	@Override
	public void releasePageContext(PageContext pageContext) {
		AutoClosePageContextRegistry.runAutoCloseRunnables(pageContext);

		_jspFactory.releasePageContext(pageContext);
	}

	private final JspFactory _jspFactory;

}