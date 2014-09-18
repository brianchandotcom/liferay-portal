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

package com.liferay.portalweb.portal.util.block.action;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class BaseAction {

	public BaseAction(LiferaySelenium liferaySelenium) {
		this.liferaySelenium = liferaySelenium;
	}

	protected String getDescription(
			String description, String paramCount, String locator,
			String locatorKey, String value, Map<String, String> variables)
		throws Exception {

		Pattern pattern = Pattern.compile(
			".*(\\$\\{locator" + paramCount + "}).*");

		Matcher matcher = pattern.matcher(description);

		if ((locatorKey != null) && pathDescriptions.containsKey(locatorKey)) {
			while (matcher.find()) {
				description = StringUtil.replace(
					description, matcher.group(1),
					"<b>" + pathDescriptions.get(locatorKey) + "</b>");
			}
		}

		if (locator != null) {
			while (matcher.find()) {
				description = StringUtil.replace(
					description, matcher.group(1), "<b>" + locator + "</b>");
			}
		}

		pattern = Pattern.compile(".*(\\$\\{value" + paramCount + "}).*");

		matcher = pattern.matcher(description);

		while (matcher.find()) {
			description = StringUtil.replace(
				description, matcher.group(1), "<b>" + value + "</b>");
		}

		return description;
	}

	protected String getLocator(
			String locator, String locatorKey, Map<String, String> variables)
		throws Exception {

		if (locator != null) {
			return locator;
		}

		if (pathLocators.containsKey(locatorKey)) {
			String locatorValue = pathLocators.get(locatorKey);

			if (locatorValue.contains("${") && locatorValue.contains("}")) {
				String regex = "\\$\\{[^}]*?\\}";

				Pattern pattern = Pattern.compile(regex);

				Matcher matcher = pattern.matcher(locatorValue);

				while (matcher.find()) {
					String variable = matcher.group();

					int x = variable.indexOf("${");
					int y = variable.indexOf("}");

					String variableKey = variable.substring(x + 2, y);

					if (variables.containsKey(variableKey)) {
						locatorValue = locatorValue.replaceFirst(
							regex, variables.get(variableKey));
					}
					else {
						throw new Exception(
							"Variable \"" + variableKey + "\" found in \"" +
								pathLocators.get(locatorKey) + "\" is not set");
					}
				}
			}

			return locatorValue;
		}

		return locatorKey;
	}

	protected LiferaySelenium liferaySelenium;
	protected Map<String, String> pathDescriptions =
		new HashMap<String, String>();
	protected Map<String, String> pathLocators = new HashMap<String, String>();

}