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

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;
import com.liferay.portalweb.portal.util.TestPropsValues;

import org.junit.Test;

/**
 * @author Kwang Lee
 */

public class AssertPartialTextTestCase extends BaseTestCase {

	@Override
	public void tearDown() throws Exception {
	}

	@Test
	public void testAssertPartialNotText() throws Exception {
		String expectedException =
			"\"This is Site 1\" does not contain \"Blah Blah Blah\" " +
				"at \"//html/body/h1\"";

		if (TestPropsValues.SELENIUM_LOGGER_ENABLED) {
			expectedException =
				"Command failure \"assertPartialText\" with parameters " +
					"\"//html/body/h1\" \"Blah Blah Blah\" : " +
						"\"This is Site 1\" does not contain " +
							"\"Blah Blah Blah\" at \"//html/body/h1\"";
		}

		try {
			selenium.assertPartialText("//html/body/h1", "Blah Blah Blah");
		}
		catch (Throwable e) {
			assertEquals(e.getMessage(), expectedException);
		}
	}

	@Test
	public void testAssertPartialText() throws Exception {
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.assertPartialText("//html/body/p[1]/a", "This i");
		selenium.clickAt("//html/body/p[1]/a", RuntimeVariables.replace(""));
		selenium.assertPartialText("//html/body/p[1]/a", "This is");
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[2]/a", RuntimeVariables.replace(""));
		selenium.assertPartialText("//html/body/p[1]/a", "This is Si");
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[3]/a", RuntimeVariables.replace(""));
		selenium.assertPartialText("//html/body/p[1]/a", "This is Sit");
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[4]/a", RuntimeVariables.replace(""));
		selenium.assertPartialText("//html/body/p[1]/a", "Thi");
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[5]/a", RuntimeVariables.replace(""));
		selenium.assertPartialText("//html/body/p[1]/a", "This is Site");
		selenium.waitForVisible("//html/body/p[1]/a");
		selenium.clickAt("//html/body/p[1]/a", RuntimeVariables.replace(""));

	}

}