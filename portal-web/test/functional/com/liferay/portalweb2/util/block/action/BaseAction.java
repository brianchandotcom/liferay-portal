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

package com.liferay.portalweb2.util.block.action;

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Michael Hashimoto
 */
public class BaseAction {

	public BaseAction(LiferaySelenium liferaySelenium) {
		this.liferaySelenium = liferaySelenium;
	}

	protected String getLocator(
		String locator, String locatorKey,
		Map<String, String> commandScopeVariables) {

		if (locator != null) {
			return locator;
		}

		if (paths.containsKey(locatorKey)) {
			String locatorValue = paths.get(locatorKey);

			if (locatorValue.contains("${") && locatorValue.contains("}")) {
				String regex = "\\$\\{[^}]*?\\}";

				Pattern p = Pattern.compile(regex);

				Matcher m = p.matcher(locatorValue);

				while (m.find()) {
					String varKey = m.group();

					int x = varKey.indexOf("{");
					int y = varKey.indexOf("}");

					varKey = varKey.substring(x + 1, y);

					Set<String> varKeys = commandScopeVariables.keySet();

					if (varKeys.contains(varKey)) {
						String varValue = commandScopeVariables.get(varKey);

						locatorValue = locatorValue.replaceFirst(
							regex, varValue);
					}
					else {
						return null;
					}
				}

				return locatorValue;
			}
			else {
				return locatorValue;
			}
		}

		return locatorKey;
	}

	protected LiferaySelenium liferaySelenium;
	protected Map<String, String> paths = new HashMap<String, String>();

}