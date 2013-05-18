/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPropsValues extends com.liferay.portal.util.TestPropsValues {

	public static final String BROWSER_COMMANDS_DIR = TestPropsUtil.get(
		"browser.commands.dir");

	public static final String BROWSER_TYPE = TestPropsUtil.get("browser.type");

	public static final String CLUSTER_NODE_1 = TestPropsUtil.get(
		"cluster.node1");

	public static final String CLUSTER_NODE_2 = TestPropsUtil.get(
		"cluster.node2");

	public static final String OUTPUT_DIR = TestPropsUtil.get("output.dir");

	public static final String PORTAL_URL = TestPropsUtil.get("portal.url");

	public static final boolean SAVE_SCREENSHOT = GetterUtil.getBoolean(
		TestPropsUtil.get("save.screenshot"));

	public static final boolean SAVE_SOURCE = GetterUtil.getBoolean(
		TestPropsUtil.get("save.source"));

	public static final String SELENIUM_EXECUTABLE_DIR = TestPropsUtil.get(
		"selenium.executable.dir");

	public static final String SELENIUM_HOST = TestPropsUtil.get(
		"selenium.host");

	public static final String SELENIUM_IMPLEMENTATION = TestPropsUtil.get(
		"selenium.implementation");

	public static final boolean SELENIUM_LOGGER = GetterUtil.getBoolean(
		TestPropsUtil.get("selenium.logger"));

	public static final int SELENIUM_PORT = GetterUtil.getInteger(
		TestPropsUtil.get("selenium.port"));

	public static final boolean TEST_DATABASE_MINIMAL = GetterUtil.getBoolean(
		TestPropsUtil.get("test.database.minimal"));

	public static final String[] THEME_IDS = StringUtil.split(
		TestPropsUtil.get("theme.ids"));

	public static final int TIMEOUT_EXPLICIT_WAIT = GetterUtil.getInteger(
		TestPropsUtil.get("timeout.explicit.wait"));

	public static final int TIMEOUT_IMPLICIT_WAIT = GetterUtil.getInteger(
		TestPropsUtil.get("timeout.implicit.wait"));

	public static final String VM_HOST = TestPropsUtil.get("vm.host");

}