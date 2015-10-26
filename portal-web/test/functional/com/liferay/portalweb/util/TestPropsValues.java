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

package com.liferay.portalweb.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class TestPropsValues
	extends com.liferay.portal.kernel.test.util.TestPropsValues {

	public static final String APP_SERVER_TYPE = TestPropsUtil.get(
		"app.server.type");

	public static final String BROWSER_COMMANDS_DIR_NAME = TestPropsUtil.get(
		"browser.commands.dir");

	public static final String BROWSER_TYPE = TestPropsUtil.get("browser.type");

	public static final String CLUSTER_NODE_1 = TestPropsUtil.get(
		"cluster.node1");

	public static final String CLUSTER_NODE_2 = TestPropsUtil.get(
		"cluster.node2");

	public static final String DATABASE_DB2_DRIVER = TestPropsUtil.get(
		"database.db2.driver");

	public static final String DATABASE_DB2_PASSWORD = TestPropsUtil.get(
		"database.db2.password");

	public static final String DATABASE_DB2_URL = TestPropsUtil.get(
		"database.db2.url");

	public static final String DATABASE_DB2_USERNAME = TestPropsUtil.get(
		"database.db2.username");

	public static final String DATABASE_HYPERSONIC_DRIVER = TestPropsUtil.get(
		"database.hypersonic.driver");

	public static final String DATABASE_HYPERSONIC_PASSWORD = TestPropsUtil.get(
		"database.hypersonic.password");

	public static final String DATABASE_HYPERSONIC_URL = TestPropsUtil.get(
		"database.hypersonic.url");

	public static final String DATABASE_HYPERSONIC_USERNAME = TestPropsUtil.get(
		"database.hypersonic.username");

	public static final String DATABASE_MYSQL_DRIVER = TestPropsUtil.get(
		"database.mysql.driver");

	public static final String DATABASE_MYSQL_PASSWORD = TestPropsUtil.get(
		"database.mysql.password");

	public static final String DATABASE_MYSQL_URL = TestPropsUtil.get(
		"database.mysql.url");

	public static final String DATABASE_MYSQL_USERNAME = TestPropsUtil.get(
		"database.mysql.username");

	public static final String DATABASE_ORACLE_DRIVER = TestPropsUtil.get(
		"database.oracle.driver");

	public static final String DATABASE_ORACLE_PASSWORD = TestPropsUtil.get(
		"database.oracle.password");

	public static final String DATABASE_ORACLE_URL = TestPropsUtil.get(
		"database.oracle.url");

	public static final String DATABASE_ORACLE_USERNAME = TestPropsUtil.get(
		"database.oracle.username");

	public static final String DATABASE_POSTGRESQL_DRIVER = TestPropsUtil.get(
		"database.postgresql.driver");

	public static final String DATABASE_POSTGRESQL_PASSWORD = TestPropsUtil.get(
		"database.postgresql.password");

	public static final String DATABASE_POSTGRESQL_URL = TestPropsUtil.get(
		"database.postgresql.url");

	public static final String DATABASE_POSTGRESQL_USERNAME = TestPropsUtil.get(
		"database.postgresql.username");

	public static final String DATABASE_SYBASE_DRIVER = TestPropsUtil.get(
		"database.sybase.driver");

	public static final String DATABASE_SYBASE_PASSWORD = TestPropsUtil.get(
		"database.sybase.password");

	public static final String DATABASE_SYBASE_URL = TestPropsUtil.get(
		"database.sybase.url");

	public static final String DATABASE_SYBASE_USERNAME = TestPropsUtil.get(
		"database.sybase.username");

	public static final String DATABASE_TYPE = TestPropsUtil.get(
		"database.type");

	public static final String EMAIL_ADDRESS_1 = TestPropsUtil.get(
		"email.address.1");

	public static final String EMAIL_ADDRESS_2 = TestPropsUtil.get(
		"email.address.2");

	public static final String EMAIL_ADDRESS_3 = TestPropsUtil.get(
		"email.address.3");

	public static final String EMAIL_ADDRESS_4 = TestPropsUtil.get(
		"email.address.4");

	public static final String EMAIL_ADDRESS_5 = TestPropsUtil.get(
		"email.address.5");

	public static final String EMAIL_PASSWORD_1 = TestPropsUtil.get(
		"email.password.1");

	public static final String EMAIL_PASSWORD_2 = TestPropsUtil.get(
		"email.password.2");

	public static final String EMAIL_PASSWORD_3 = TestPropsUtil.get(
		"email.password.3");

	public static final String EMAIL_PASSWORD_4 = TestPropsUtil.get(
		"email.password.4");

	public static final String EMAIL_PASSWORD_5 = TestPropsUtil.get(
		"email.password.5");

	public static final String[] FIXED_ISSUES = StringUtil.split(
		TestPropsUtil.get("fixed.issues"));

	public static final String IGNORE_ERRORS = TestPropsUtil.get(
		"ignore.errors");

	public static final String IGNORE_ERRORS_DELIMITER = TestPropsUtil.get(
		"ignore.errors.delimiter");

	public static final String LCS_EMAIL_ADDRESS_1 = TestPropsUtil.get(
		"lcs.email.address.1");

	public static final String LCS_EMAIL_ADDRESS_2 = TestPropsUtil.get(
		"lcs.email.address.2");

	public static final String LCS_EMAIL_ADDRESS_3 = TestPropsUtil.get(
		"lcs.email.address.3");

	public static final String LCS_EMAIL_ID_1 = TestPropsUtil.get(
		"lcs.email.id.1");

	public static final String LCS_EMAIL_ID_2 = TestPropsUtil.get(
		"lcs.email.id.2");

	public static final String LCS_EMAIL_ID_3 = TestPropsUtil.get(
		"lcs.email.id.3");

	public static final String LCS_EMAIL_PASSWORD_1 = TestPropsUtil.get(
		"lcs.email.password.1");

	public static final String LCS_EMAIL_PASSWORD_2 = TestPropsUtil.get(
		"lcs.email.password.2");

	public static final String LCS_EMAIL_PASSWORD_3 = TestPropsUtil.get(
		"lcs.email.password.3");

	public static final String LIFERAY_PORTAL_BRANCH = TestPropsUtil.get(
		"liferay.portal.branch");

	public static final String LIFERAY_PORTAL_BUNDLE = TestPropsUtil.get(
		"liferay.portal.bundle");

	public static final String MARKETPLACE_EMAIL_ADDRESS_1 = TestPropsUtil.get(
		"marketplace.email.address.1");

	public static final String MARKETPLACE_EMAIL_ADDRESS_2 = TestPropsUtil.get(
		"marketplace.email.address.2");

	public static final String MARKETPLACE_EMAIL_ADDRESS_3 = TestPropsUtil.get(
		"marketplace.email.address.3");

	public static final String MARKETPLACE_EMAIL_ADDRESS_4 = TestPropsUtil.get(
		"marketplace.email.address.4");

	public static final String MARKETPLACE_EMAIL_ADDRESS_5 = TestPropsUtil.get(
		"marketplace.email.address.5");

	public static final String MARKETPLACE_EMAIL_PASSWORD_1 = TestPropsUtil.get(
		"marketplace.email.password.1");

	public static final String MARKETPLACE_EMAIL_PASSWORD_2 = TestPropsUtil.get(
		"marketplace.email.password.2");

	public static final String MARKETPLACE_EMAIL_PASSWORD_3 = TestPropsUtil.get(
		"marketplace.email.password.3");

	public static final String MARKETPLACE_EMAIL_PASSWORD_4 = TestPropsUtil.get(
		"marketplace.email.password.4");

	public static final String MARKETPLACE_EMAIL_PASSWORD_5 = TestPropsUtil.get(
		"marketplace.email.password.5");

	public static final String MOBILE_ANDROID_HOME = TestPropsUtil.get(
		"mobile.android.home");

	public static final boolean MOBILE_DEVICE_ENABLED = GetterUtil.getBoolean(
		TestPropsUtil.get("mobile.device.enabled"));

	public static final String OSB_LCS_PORTLET_HOST_NAME = TestPropsUtil.get(
		"osb.lcs.portlet.host.name");

	public static final String OUTPUT_DIR_NAME = TestPropsUtil.get(
		"output.dir");

	public static final String PORTAL_URL = TestPropsUtil.get("portal.url");

	public static final boolean SAVE_SCREENSHOT = GetterUtil.getBoolean(
		TestPropsUtil.get("save.screenshot"));

	public static final boolean SAVE_SOURCE = GetterUtil.getBoolean(
		TestPropsUtil.get("save.source"));

	public static final String SELENIUM_CHROME_DRIVER_EXECUTABLE =
		TestPropsUtil.get("selenium.chrome.driver.executable");

	public static final String SELENIUM_EXECUTABLE_DIR_NAME = TestPropsUtil.get(
		"selenium.executable.dir");

	public static final String SELENIUM_HOST = TestPropsUtil.get(
		"selenium.host");

	public static final String SELENIUM_IE_DRIVER_EXECUTABLE =
		TestPropsUtil.get("selenium.ie.driver.executable");

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

	public static final boolean TEST_ASSERT_JAVASCRIPT_ERRORS =
		GetterUtil.getBoolean(
			TestPropsUtil.get("test.assert.javascript.errors"));

	public static final boolean TEST_ASSERT_LIFERAY_ERRORS =
		GetterUtil.getBoolean(TestPropsUtil.get("test.assert.liferay.errors"));

	public static final String TEST_BASE_DIR_NAME = TestPropsUtil.get(
		"test.base.dir.name");

	public static final String TEST_CLASS_COMMAND_NAME = TestPropsUtil.get(
		"test.class.command.name");

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