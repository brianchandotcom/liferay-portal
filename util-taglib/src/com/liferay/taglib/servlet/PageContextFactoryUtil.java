/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		return _jspFactory.getPageContext(
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
	}

	private static final JspFactory _jspFactory =
		JspFactory.getDefaultFactory();

}