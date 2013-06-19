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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.ContextReplace;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class RuntimeVariables {

	public static String getValue(String key) {
		return _instance._getValue(key);
	}

	public static String replace(String text) {
		return _instance._replace(text);
	}

	public static void setValue(String key, String value) {
		_instance._setValue(key, value);
	}

	private RuntimeVariables() {
		File file = new File(StringPool.PERIOD);

		String absolutePath = file.getAbsolutePath();

		if (absolutePath.endsWith(StringPool.PERIOD)) {
			absolutePath = absolutePath.substring(0, absolutePath.length() - 1);

			_sourceDir = absolutePath;
		}
	}

	private String _getValue(String key) {
		return _runtimeVariables.get(key);
	}

	private String _replace(String text) {

		// Email Addresses

		text = StringUtil.replace(
			text, "liferay-mailinglist@googlegroups.com",
			"mainlinglist@liferay.com");

		text = StringUtil.replace(
			text, "liferay.qa.server.trunk@gmail.com", "serverea@liferay.com");

		text = StringUtil.replace(
			text, "liferay.qa.testing.trunk@gmail.com", "userea@liferay.com");

		// Portal URL

		text = StringUtil.replace(
			text, "http://localhost:8080", TestPropsValues.PORTAL_URL);

		// Root directory

		text = StringUtil.replace(text, "L:\\portal\\build\\", _sourceDir);

		// Theme output directory

		text = StringUtil.replace(
			text, "\\test-output\\brochure\\",
			"\\test-output\\" + SeleniumUtil.getTimestamp() + "\\" +
				ThemeIds.getThemeId() + "\\");

		// Select theme

		text = StringUtil.replace(
			text, "//a[contains(@href, 'brochure_WAR_brochuretheme')]",
			"//a[contains(@href, '" + ThemeIds.getThemeId() + "')]");

		if (TestPropsValues.TEST_DATABASE_MINIMAL) {
			text = StringUtil.replace(text, "Bloggs", "Test");
			text = StringUtil.replace(text, "Joe", "Test");
			text = StringUtil.replace(text, "joebloggs", "test");
		}

		if (Validator.isNotNull(TestPropsValues.CLUSTER_NODE_1)) {
			text = StringUtil.replace(
				text, "[$CLUSTER_NODE_1$]", TestPropsValues.CLUSTER_NODE_1);
		}

		if (Validator.isNotNull(TestPropsValues.CLUSTER_NODE_2)) {
			text = StringUtil.replace(
				text, "[$CLUSTER_NODE_2$]", TestPropsValues.CLUSTER_NODE_2);
		}

		if (Validator.isNotNull(TestPropsValues.VM_HOST)) {
			text = StringUtil.replace(
				text, "[$VM_HOST$]", TestPropsValues.VM_HOST);
		}

		if (_contextReplace == null) {
			return text;
		}
		else {
			return _contextReplace.replace(text);
		}
	}

	private void _setValue(String key, String value) {
		_runtimeVariables.put(key, value);

		_contextReplace = new ContextReplace(_runtimeVariables);
	}

	private static RuntimeVariables _instance = new RuntimeVariables();

	private ContextReplace _contextReplace;
	private Map<String, String> _runtimeVariables =
		new HashMap<String, String>();
	private String _sourceDir;

}