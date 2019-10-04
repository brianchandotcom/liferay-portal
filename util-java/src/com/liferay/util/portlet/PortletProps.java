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

package com.liferay.util.portlet;

import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;

import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 */
public class PortletProps {

	public static void addProperties(Properties properties) {
		_portletProps._configuration.addProperties(properties);
	}

	public static boolean contains(String key) {
		return _portletProps._configuration.contains(key);
	}

	public static String get(String key) {
		return _portletProps._configuration.get(key);
	}

	public static String get(String key, Filter filter) {
		return _portletProps._configuration.get(key, filter);
	}

	public static String[] getArray(String key) {
		return _portletProps._configuration.getArray(key);
	}

	public static String[] getArray(String key, Filter filter) {
		return _portletProps._configuration.getArray(key, filter);
	}

	public static Properties getProperties() {
		return _portletProps._configuration.getProperties();
	}

	public static void removeProperties(Properties properties) {
		_portletProps._configuration.removeProperties(properties);
	}

	public static void set(String key, String value) {
		_portletProps._configuration.set(key, value);
	}

	private PortletProps() {
		_configuration = ConfigurationFactoryUtil.getConfiguration(
			PortletClassLoaderUtil.getClassLoader(), "portlet");
	}

	private static final PortletProps _portletProps = new PortletProps();

	private final Configuration _configuration;

}