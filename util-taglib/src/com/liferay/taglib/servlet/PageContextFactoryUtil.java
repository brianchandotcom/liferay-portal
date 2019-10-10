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

import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

/**
 * @author Shuyang Zhou
 */
public class PageContextFactoryUtil {

	public static PageContext create(
		final HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		final ServletConfig servletConfig = new ServletConfig() {

			@Override
			public String getInitParameter(String name) {
				return null;
			}

			@Override
			public Enumeration<String> getInitParameterNames() {
				return Collections.emptyEnumeration();
			}

			@Override
			public ServletContext getServletContext() {
				return httpServletRequest.getServletContext();
			}

			@Override
			public String getServletName() {
				return "Page Context Servlet";
			}

		};

		PageContext pageContext = _jspFactory.getPageContext(
			new Servlet() {

				@Override
				public void destroy() {
				}

				@Override
				public ServletConfig getServletConfig() {
					return servletConfig;
				}

				@Override
				public String getServletInfo() {
					return servletConfig.getServletName();
				}

				@Override
				public void init(ServletConfig servletConfig) {
				}

				@Override
				public void service(
					ServletRequest httpServletRequest,
					ServletResponse httpServletResponse) {
				}

			},
			httpServletRequest, httpServletResponse, null, true, 0, false);

		pageContext.removeAttribute(
			AutoClosePageContextRegistry.AUTO_CLOSEABLE);

		return pageContext;
	}

	private static final JspFactory _jspFactory =
		JspFactory.getDefaultFactory();

}