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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.util.ContextReplace;

import java.io.File;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Brian Wing Shun Chan
 */
public class RuntimeVariables {

	public static String evaluateLocator(
			String locator, Map<String, String> context)
		throws Exception {

		String locatorValue = locator;

		if (locatorValue.contains("${") && locatorValue.contains("}")) {
			String regex = "\\$\\{([^}]*?)\\}";

			Pattern pattern = Pattern.compile(regex);

			Matcher matcher = pattern.matcher(locatorValue);

			while (matcher.find()) {
				String variableKey = matcher.group(1);

				if (context.containsKey(variableKey)) {
					locatorValue = locatorValue.replaceFirst(
						regex, context.get(variableKey));
				}
				else {
					throw new Exception(
						"Variable \"" + variableKey + "\" found in \"" +
							locator + "\" is not set");
				}
			}
		}

		return locatorValue;
	}

	public static String evaluateVariable(
		String value, Map<String, String> context) {

		String varValue = value;

		Pattern pattern = Pattern.compile("\\$\\{([^}]*?)\\}");

		Matcher matcher = pattern.matcher(varValue);

		Pattern statementPattern = Pattern.compile(
			"(.*)\\?(.*)\\(([^\\)]*?)\\)");

		while (matcher.find()) {
			String statement = matcher.group(1);

			Matcher statementMatcher = statementPattern.matcher(statement);

			if (statementMatcher.find()) {
				String operand = statementMatcher.group(1);

				if (!context.containsKey(operand)) {
					continue;
				}

				String[] arguments = StringUtil.split(
					statementMatcher.group(3), "'");

				List<String> argumentsList = new ArrayList<String>();

				for (int i = 1; i < arguments.length; i++) {
					if ((i % 2) == 1) {
						argumentsList.add(arguments[i]);
					}
				}

				String method = statementMatcher.group(2);

				String operandValue = context.get(operand);

				if ((_forParameterName != null) &&
					operand.equals(_forParameterName)) {

					operandValue = _forParameterValue;
				}

				String replaceRegex = "\\$\\{([^}]*?)\\}";

				String result = "";

				if (method.startsWith("getFirstNumber")) {
					result = operandValue.replaceFirst("\\D*(\\d*).*", "$1");
				}
				else if (method.startsWith("increment")) {
					int i = GetterUtil.getInteger(operandValue) + 1;

					result = String.valueOf(i);
				}
				else if (method.startsWith("length")) {
					result = String.valueOf(operandValue.length());
				}
				else if (method.startsWith("lowercase")) {
					result = StringUtil.toLowerCase(operandValue);
				}
				else if (method.startsWith("replace")) {
					result = operandValue.replace(
						argumentsList.get(0), argumentsList.get(1));
				}
				else if (method.startsWith("uppercase")) {
					result = StringUtil.toUpperCase(operandValue);
				}

				varValue = varValue.replaceFirst(replaceRegex, result);
			}
			else {
				String varName = statement;

				if (!context.containsKey(varName) &&
					!varName.equals(_forParameterName)) {

					continue;
				}

				String replaceRegex = "\\$\\{([^}]*?)\\}";

				String result = context.get(varName);

				if ((_forParameterName != null) &&
					_forParameterName.equals(varName)) {

					result = _forParameterValue;
				}

				result = Matcher.quoteReplacement(result);

				varValue = varValue.replaceFirst(replaceRegex, result);
			}
		}

		varValue = varValue.replace("\\$", "$");
		varValue = varValue.replace("\\{", "{");
		varValue = varValue.replace("\\}", "}");

		return varValue;
	}

	public static String getValue(String key) {
		return _instance._getValue(key);
	}

	public static String replace(String text) {
		return _instance._replace(text);
	}

	public static void resetForParameter() {
		_forParameterName = null;

		_forParameterValue = null;
	}

	public static void setForParameter(String name, String value) {
		_forParameterName = name;

		_forParameterValue = value;
	}

	public static void setValue(String key, String value) {
		_instance._setValue(key, value);
	}

	public static boolean variableExists(
		String name, Map<String, String> context) {

		return (context.containsKey(name) || name.equals(_forParameterName));
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

	private static String _forParameterName;
	private static String _forParameterValue;

	private ContextReplace _contextReplace;
	private Map<String, String> _runtimeVariables =
		new HashMap<String, String>();
	private String _sourceDir;

}