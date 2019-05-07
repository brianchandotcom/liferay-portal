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

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Preston Crary
 */
public class FriendlyURLUtil {

	public static String getFriendlyURL(
		HttpServletRequest httpServletRequest, ServletConfig servletConfig) {

		boolean privateParam = GetterUtil.getBoolean(
			servletConfig.getInitParameter("private"));

		String proxyPath = PortalUtil.getPathProxy();

		String friendlyURLPathPrefix = null;

		if (privateParam) {
			boolean userParam = GetterUtil.getBoolean(
				servletConfig.getInitParameter("user"));

			if (userParam) {
				friendlyURLPathPrefix =
					PortalUtil.getPathFriendlyURLPrivateUser();
			}
			else {
				friendlyURLPathPrefix =
					PortalUtil.getPathFriendlyURLPrivateGroup();
			}
		}
		else {
			friendlyURLPathPrefix = PortalUtil.getPathFriendlyURLPublic();
		}

		int pathInfoOffset =
			friendlyURLPathPrefix.length() - proxyPath.length();

		String pathInfo = null;

		String requestURI = httpServletRequest.getRequestURI();

		int pos = requestURI.indexOf(Portal.JSESSIONID);

		if (pos == -1) {
			pathInfo = requestURI.substring(pathInfoOffset);
		}
		else {
			pathInfo = requestURI.substring(pathInfoOffset, pos);
		}

		if (Validator.isNotNull(pathInfo)) {
			return friendlyURLPathPrefix.concat(pathInfo);
		}

		return friendlyURLPathPrefix;
	}

}