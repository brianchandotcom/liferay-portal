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
public class MouseOverTestCase extends BaseSeleniumTestCase {

	@Test
	public void testFailMouseOver() throws Exception {
		String expectedException = null;

		if (SELENIUM_LOGGER_ENABLED) {
			expectedException =
				"Command failure \"mouseOver\" with parameter " +
					"\"//Does/Not/Exists\" : null";
		}

		try {
			selenium.mouseOver("//Does/Not/Exists");
		}
		catch (Throwable t) {
			assertEquals(t.getMessage(), expectedException);
		}
	}

	@Test
	public void testMouseOver() throws Exception {
		selenium.mouseOver("//html/body/a/img");
		selenium.waitForVisible("//html/body/a/img[@src='Cat.jpg']");
	}

}