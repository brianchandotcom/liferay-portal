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

package com.liferay.taglib.util;

import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.servlet.PipingServletResponse;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class PortalIncludeUtil {

	public static void include(
			PageContext pageContext, HTMLRenderer htmlRenderer)
		throws IOException, ServletException {

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		htmlRenderer.renderHTML(
			request, new PipingServletResponse(response, pageContext.getOut()));
	}

	public static void include(PageContext pageContext, String path)
		throws IOException, ServletException {

		HttpServletRequest request =
			(HttpServletRequest)pageContext.getRequest();
		HttpServletResponse response =
			(HttpServletResponse)pageContext.getResponse();

		ServletContext servletContext = (ServletContext)request.getAttribute(
			WebKeys.CTX);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(path);

		requestDispatcher.include(
			request, new PipingServletResponse(response, pageContext.getOut()));
	}

	public interface HTMLRenderer {

		public void renderHTML(
				HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException;

	}

}