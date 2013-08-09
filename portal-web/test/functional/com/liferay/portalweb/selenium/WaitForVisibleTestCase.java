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
public class WaitForVisibleTestCase extends BaseSeleniumTestCase {

	@Test
	public void testFailWaitForVisible() throws Exception {
		String expectedException =
			"Element is not present at \"//Does/Not/Exists\"";

		if (SELENIUM_LOGGER_ENABLED) {
			expectedException =
				"Command failure \"waitForVisible\" with parameter " +
					"\"//Does/Not/Exists\" : " + expectedException;
		}

		try {
			selenium.waitForVisible("//Does/Not/Exists");
		}
		catch (Throwable t) {
			assertEquals(t.getMessage(), expectedException);
		}
	}

	@Test
	public void testWaitForVisible() throws Exception {
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[1]/a", "");
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[2]/a", "");
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[3]/a", "");
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[4]/a", "");
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[5]/a", "");
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[1]/a", "");
	}

}