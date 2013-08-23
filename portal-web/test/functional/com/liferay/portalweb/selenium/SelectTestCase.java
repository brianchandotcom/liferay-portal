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

package com.liferay.portalweb.selenium;

import org.junit.Test;

/**
 * @author Kwang Lee
 */
public class SelectTestCase extends BaseSeleniumTestCase {

	@Test
	public void testFailSelect() throws Exception {
		String expectedException = null;

		if (SELENIUM_LOGGER_ENABLED) {
			expectedException =
				"Command failure \"select\" with parameters " +
					"\"//Does/Not/Exists\" \"value=Failure\" : null";
		}

		try {
			selenium.select("//Does/Not/Exists", "value=Failure");
		}
		catch (Throwable t) {
			assertEquals(t.getMessage(), expectedException);
		}
	}

	@Test
	public void testSelect() throws Exception {
		String locator = "//html/body/div[1]/select";

		selenium.select(locator, "index=3");

		selenium.assertSelectedLabel(locator, "Test 3");

		selenium.select(locator, "Test 1");

		selenium.assertSelectedLabel(locator, "Test 1");

		selenium.select(locator, "label=regexp:.*2.*");

		selenium.assertSelectedLabel(locator, "Test 2");

		selenium.select(locator, "label=Test 3");

		selenium.assertSelectedLabel(locator, "Test 3");

		selenium.select(locator, "value=regexp:.*1.*");

		selenium.assertSelectedLabel(locator, "Test 1");

		selenium.select(locator, "value=Test 2");

		selenium.assertSelectedLabel(locator, "Test 2");
	}

}