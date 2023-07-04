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

package com.liferay.portal.ee.license;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.taglib.BaseDynamicInclude;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Tina Tian
 */
public class LicenseBottomJSPDynamicInclude extends BaseDynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ServletContext servletContext = ServletContextPool.get(
			ServletContextClassLoaderPool.getServletContextName(
				PortalClassLoaderUtil.getClassLoader()));

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(_JSP_PATH);

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException servletException) {
			_log.error(
				"Unable to include JSP " + _JSP_PATH, servletException);

			throw new IOException(
				"Unable to include JSP " + _JSP_PATH, servletException);
		}
	}

	@Override
	public void register(
		DynamicIncludeRegistry dynamicIncludeRegistry) {

		dynamicIncludeRegistry.register(
			"/html/common/themes/bottom.jsp#pre");
	}

	private static String _JSP_PATH = "/html/portal/dynamic_include/view.jsp";

	private static Log _log = LogFactoryUtil.getLog(
		LicenseBottomJSPDynamicInclude.class);

}
