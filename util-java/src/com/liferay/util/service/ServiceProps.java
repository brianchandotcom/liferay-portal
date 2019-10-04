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

package com.liferay.util.service;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class ServiceProps {

	public static void addProperties(Properties properties) {
		_serviceProps._configuration.addProperties(properties);
	}

	public static boolean contains(String key) {
		return _serviceProps._configuration.contains(key);
	}

	public static String get(String key) {
		return _serviceProps._configuration.get(key);
	}

	public static String get(String key, Filter filter) {
		return _serviceProps._configuration.get(key, filter);
	}

	public static String[] getArray(String key) {
		return _serviceProps._configuration.getArray(key);
	}

	public static String[] getArray(String key, Filter filter) {
		return _serviceProps._configuration.getArray(key, filter);
	}

	public static Properties getProperties() {
		return _serviceProps._configuration.getProperties();
	}

	public static void removeProperties(Properties properties) {
		_serviceProps._configuration.removeProperties(properties);
	}

	public static void set(String key, String value) {
		_serviceProps._configuration.set(key, value);
	}

	private ServiceProps() {
		_configuration = ConfigurationFactoryUtil.getConfiguration(
			PortletClassLoaderUtil.getClassLoader(), "service");
	}

	private static final ServiceProps _serviceProps = new ServiceProps();

	private final Configuration _configuration;

}