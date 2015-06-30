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

package com.liferay.taglib;

import com.liferay.portal.kernel.servlet.PortalWebResourcesUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;

import java.net.URL;

import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class FileAvailabilityUtil {

	public static void clearAvailabilities() {
		String servletContextName = PortalUtil.getServletContextName();

		ServletContext servletContext = ServletContextPool.get(
			servletContextName);

		Map<String, Boolean> availabilities =
			(Map<String, Boolean>)servletContext.getAttribute(
				FileAvailabilityUtil.class.getName());

		if (availabilities != null) {
			servletContext.removeAttribute(
				FileAvailabilityUtil.class.getName());
		}
	}

	public static boolean isAvailable(
		ServletContext servletContext, String path) {

		if (Validator.isNull(path)) {
			return false;
		}

		if (path.charAt(0) != CharPool.SLASH) {
			return true;
		}

		Map<String, Boolean> availabilities = _getAvailabilities(
			servletContext);

		Boolean available = availabilities.get(path);

		if (available != null) {
			return available;
		}

		URL url = null;

		try {
			url = AccessController.doPrivileged(
				new ResourcePrivilegedExceptionAction(servletContext, path));
		}
		catch (Exception e) {
		}

		if ((url == null) && !PortalWebResourcesUtil.isAvailable(path)) {
			available = Boolean.FALSE;
		}
		else {
			available = Boolean.TRUE;
		}

		availabilities.put(path, available);

		return available;
	}

	private static Map<String, Boolean> _getAvailabilities(
		ServletContext servletContext) {

		Map<String, Boolean> availabilities =
			(Map<String, Boolean>)servletContext.getAttribute(
				FileAvailabilityUtil.class.getName());

		if (availabilities == null) {
			availabilities = new ConcurrentHashMap<>();

			servletContext.setAttribute(
				FileAvailabilityUtil.class.getName(), availabilities);
		}

		return availabilities;
	}

	private static class ResourcePrivilegedExceptionAction
		implements PrivilegedExceptionAction<URL> {

		public ResourcePrivilegedExceptionAction(
			ServletContext servletContext, String path) {

			_servletContext = servletContext;
			_path = path;
		}

		@Override
		public URL run() throws Exception {
			return _servletContext.getResource(_path);
		}

		private final String _path;
		private final ServletContext _servletContext;

	}

}