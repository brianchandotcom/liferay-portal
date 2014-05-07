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

package com.liferay.portalweb.portal.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPropsValues extends com.liferay.portal.util.TestPropsValues {

	public static final String BROWSER_COMMANDS_DIR_NAME = TestPropsUtil.get(
		"browser.commands.dir");

	public static final String BROWSER_TYPE = TestPropsUtil.get("browser.type");

	public static final String CLUSTER_NODE_1 = TestPropsUtil.get(
		"cluster.node1");

	public static final String CLUSTER_NODE_2 = TestPropsUtil.get(
		"cluster.node2");

	public static final String EMAIL_ADDRESS_1 = TestPropsUtil.get(
		"email.address.1");

	public static final String EMAIL_ADDRESS_2 = TestPropsUtil.get(
		"email.address.2");

	public static final String EMAIL_PASSWORD_1 = TestPropsUtil.get(
		"email.password.1");

	public static final String EMAIL_PASSWORD_2 = TestPropsUtil.get(
		"email.password.2");

	public static final String[] FIXED_ISSUES = StringUtil.split(
		TestPropsUtil.get("fixed.issues"));

	public static final String IGNORE_ERROR = TestPropsUtil.get("ignore.error");

	public static final String LIFERAY_PORTAL_BRANCH = TestPropsUtil.get(
		"liferay.portal.branch");

	public static final String LIFERAY_PORTAL_BUNDLE = TestPropsUtil.get(
		"liferay.portal.bundle");

	public static final boolean MOBILE_DEVICE_ENABLED = GetterUtil.getBoolean(
		TestPropsUtil.get("mobile.device.enabled"));

	public static final String MOBILE_DEVICE_RESOLUTION = TestPropsUtil.get(
		"mobile.device.resolution");

	public static final String MOBILE_DEVICE_USER_AGENT = TestPropsUtil.get(
		"mobile.device.user.agent");

	public static final String OUTPUT_DIR_NAME = TestPropsUtil.get(
		"output.dir");

	public static final String PORTAL_URL = TestPropsUtil.get("portal.url");

	public static final boolean SAVE_SCREENSHOT = GetterUtil.getBoolean(
		TestPropsUtil.get("save.screenshot"));

	public static final boolean SAVE_SOURCE = GetterUtil.getBoolean(
		TestPropsUtil.get("save.source"));

	public static final String SELENIUM_EXECUTABLE_DIR_NAME = TestPropsUtil.get(
		"selenium.executable.dir");

	public static final String SELENIUM_HOST = TestPropsUtil.get(
		"selenium.host");

	public static final String SELENIUM_IMPLEMENTATION = TestPropsUtil.get(
		"selenium.implementation");

	public static final boolean SELENIUM_LOGGER_ENABLED = GetterUtil.getBoolean(
		TestPropsUtil.get("selenium.logger.enabled"));

	public static final int SELENIUM_PORT = GetterUtil.getInteger(
		TestPropsUtil.get("selenium.port"));

	public static final String TCAT_ADMIN_REPOSITORY = TestPropsUtil.get(
		"tcat.admin.repository");

	public static final boolean TCAT_ENABLED = GetterUtil.getBoolean(
		TestPropsUtil.get("tcat.enabled"));

	public static final boolean TEAR_DOWN_BEFORE_TEST = GetterUtil.getBoolean(
		TestPropsUtil.get("tear.down.before.test"));

	public static final boolean TEST_ASSERT_LIFERAY_ERRORS =
		GetterUtil.getBoolean(TestPropsUtil.get("test.assert.liferay.errors"));

	public static final boolean TEST_ASSSERT_JAVASCRIPT_ERRORS =
		GetterUtil.getBoolean(
			TestPropsUtil.get("test.assert.javascript.errors"));

	public static final String TEST_BASE_DIR_NAME = TestPropsUtil.get(
		"test.basedir");

	public static final boolean TEST_DATABASE_MINIMAL = GetterUtil.getBoolean(
		TestPropsUtil.get("test.database.minimal"));

	public static final boolean TEST_SKIP_TEAR_DOWN = GetterUtil.getBoolean(
		TestPropsUtil.get("test.skip.tear.down"));

	public static final boolean TESTING_CLASS_METHOD = GetterUtil.getBoolean(
		TestPropsUtil.get("testing.class.method"));

	public static final String[] THEME_IDS = StringUtil.split(
		TestPropsUtil.get("theme.ids"));

	public static final int TIMEOUT_EXPLICIT_WAIT = GetterUtil.getInteger(
		TestPropsUtil.get("timeout.explicit.wait"));

	public static final int TIMEOUT_IMPLICIT_WAIT = GetterUtil.getInteger(
		TestPropsUtil.get("timeout.implicit.wait"));

	public static final String VM_HOST = TestPropsUtil.get("vm.host");

}