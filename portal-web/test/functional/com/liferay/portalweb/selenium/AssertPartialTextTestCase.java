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
public class AssertPartialTextTestCase extends BaseSeleniumTestCase {

	@Test
	public void testAssertPartialText() throws Exception {
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.assertPartialText("//html/body/p[1]/a", "This i");
		selenium.clickAt("//html/body/p[1]/a", "");
		selenium.assertPartialText("//html/body/p[2]/a", "This is");
		selenium.clickAt("//html/body/p[2]/a", "");
		selenium.assertPartialText("//html/body/p[3]/a", "This is Si");
		selenium.clickAt("//html/body/p[3]/a", "");
		selenium.assertPartialText("//html/body/p[4]/a", "This is Sit");
		selenium.clickAt("//html/body/p[4]/a", "");
		selenium.assertPartialText("//html/body/p[5]/a", "Thi");
		selenium.clickAt("//html/body/p[5]/a", "");
		selenium.assertPartialText("//html/body/p[6]/a", "This is Site");
		selenium.clickAt("//html/body/p[6]/a", "");
		selenium.assertPartialText("//html/body/p[1]/a", "This is Site 1");
		selenium.clickAt("//html/body/p[1]/a", "");
	}

	@Test
	public void testFailAssertPartialText() throws Exception {
		String expectedException =
			"\"This is Site 1\" does not contain \"Blah Blah Blah\" " +
				"at \"//html/body/h1\"";

		if (SELENIUM_LOGGER_ENABLED) {
			expectedException =
				"Command failure \"assertPartialText\" with parameters " +
					"\"//html/body/h1\" \"Blah Blah Blah\" : " +
					expectedException;
		}

		try {
			selenium.assertPartialText("//html/body/h1", "Blah Blah Blah");
		}
		catch (Throwable t) {
			assertEquals(t.getMessage(), expectedException);
		}
	}

}