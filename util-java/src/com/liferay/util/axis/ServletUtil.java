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

package com.liferay.util.axis;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.axis.AxisEngine;
import org.apache.axis.MessageContext;
import org.apache.axis.transport.http.HTTPConstants;

/**
 * @author Brian Wing Shun Chan
 */
public class ServletUtil {

	public static HttpServletRequest getRequest() {
		MessageContext messageContext = AxisEngine.getCurrentMessageContext();

		return (HttpServletRequest)messageContext.getProperty(
			HTTPConstants.MC_HTTP_SERVLETREQUEST);
	}

	public static HttpServlet getServlet() {
		MessageContext messageContext = AxisEngine.getCurrentMessageContext();

		return (HttpServlet)messageContext.getProperty(
			HTTPConstants.MC_HTTP_SERVLET);
	}

	public static ServletContext getServletContext() {
		return getServlet().getServletContext();
	}

	public static HttpSession getSession() {
		HttpServletRequest httpServletRequest = getRequest();

		return httpServletRequest.getSession();
	}

}