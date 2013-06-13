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

/**
 * @author Michael Hashimoto
 */
public class BaseAction {

	public BaseAction(LiferaySelenium liferaySelenium) {
		this.liferaySelenium = liferaySelenium;
	}

	protected String getLocator(String locator, String locatorKey) {
		if (locator != null) {
			return locator;
		}

		if (paths.containsKey(locatorKey)) {
			return paths.get(locatorKey);
		}

		return locatorKey;
	}

	protected LiferaySelenium liferaySelenium;
	protected Map<String, String> paths = new HashMap<String, String>();

}