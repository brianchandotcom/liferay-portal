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
public class ClickAtTestCase extends BaseSeleniumTestCase {

	@Test
	public void testClickAt() throws Exception {
		selenium.clickAt("//html/body/p[1]/a", "");
		selenium.clickAt("//html/body/p[2]/a", "");
		selenium.clickAt("//html/body/p[3]/a", "");
		selenium.clickAt("//html/body/p[4]/a", "");
		selenium.clickAt("//html/body/p[5]/a", "");
		selenium.clickAt("//html/body/p[6]/a", "");
		selenium.clickAt("//html/body/p[1]/a", "");
	}

	@Test
	public void testFailClickAt() throws Exception {
		String expectedException = null;

		if (SELENIUM_LOGGER_ENABLED) {
			expectedException =
				"Command failure \"clickAt\" with parameters " +
					"\"//Does/Not/Exists\" \"\" : null";
		}

		try {
			selenium.clickAt("//Does/Not/Exists", "");
		}
		catch (Throwable t) {
			assertEquals(t.getMessage(), expectedException);
		}
	}

}