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

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import com.thoughtworks.selenium.SeleneseTestBase;
import com.thoughtworks.selenium.SeleneseTestCase;

/**
 * @author Brian Wing Shun Chan
 */
@SuppressWarnings("deprecation")
public class LiferaySeleneseTestCase extends SeleneseTestCase {

	public static void assertEquals(String expected, String actual) {
		SeleneseTestBase.assertEquals(
			RuntimeVariables.replace(expected),
			RuntimeVariables.replace(actual));
	}

	public static void assertNotEquals(String expected, String actual) {
		SeleneseTestBase.assertNotEquals(
			RuntimeVariables.replace(expected),
			RuntimeVariables.replace(actual));
	}

	public LiferaySeleneseTestCase() {
	}

	public LiferaySeleneseTestCase(String name) {
		super(name);
	}

	protected LiferaySelenium selenium;

}