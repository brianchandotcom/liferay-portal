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

import com.liferay.portal.kernel.servlet.DirectServletRegistryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspEngineInfo;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

/**
 * @author Shuyang Zhou
 */
public class JspFactoryWrapper extends JspFactory {

	public JspFactoryWrapper(JspFactory jspFactory) {
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

		if (autoflush) {
			buffer = _JSP_WRITER_BUFFER_SIZE;
		}

		PageContext pageContext = _jspFactory.getPageContext(
			servlet, servletRequest, servletResponse, errorPageURL,
			needsSession, buffer, autoflush);

		if (_DIRECT_SERVLET_CONTEXT_ENABLED) {
			String servletPath = (String)servletRequest.getAttribute(
				WebKeys.SERVLET_PATH);

			if (servletPath != null) {
				servletRequest.removeAttribute(WebKeys.SERVLET_PATH);

				ServletContext servletContext = pageContext.getServletContext();

				String contextPath = servletContext.getContextPath();

				DirectServletRegistryUtil.putServlet(
					contextPath.concat(servletPath), servlet);
			}
		}

		return new PageContextWrapper(pageContext);
	}

	@Override
	public void releasePageContext(PageContext pageContext) {
		if (pageContext instanceof PageContextWrapper) {
			PageContextWrapper pageContextWrapper =
				(PageContextWrapper)pageContext;

			pageContext = pageContextWrapper.getWrappedPageContext();
		}

		_jspFactory.releasePageContext(pageContext);
	}

	private static final boolean _DIRECT_SERVLET_CONTEXT_ENABLED =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.DIRECT_SERVLET_CONTEXT_ENABLED));

	private static final int _JSP_WRITER_BUFFER_SIZE = GetterUtil.getInteger(
		PropsUtil.get(PropsKeys.JSP_WRITER_BUFFER_SIZE));

	private final JspFactory _jspFactory;

}