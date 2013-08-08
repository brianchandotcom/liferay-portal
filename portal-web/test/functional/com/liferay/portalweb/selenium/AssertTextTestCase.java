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
public class AssertTextTestCase extends BaseTestCase {

	@Override
	public void tearDown() throws Exception {
	}

	@Test
	public void testAssertNotText() throws Exception {
		String expectedException =
			"Pattern \"This is Site 2\" does not match \"This is Site 1\" " +
				"at \"//html/body/h1\"";

		if (TestPropsValues.SELENIUM_LOGGER_ENABLED) {
			expectedException =
				"Command failure \"assertText\" with parameters " +
					"\"//html/body/h1\" \"This is Site 2\" : Pattern " +
						"\"This is Site 2\" does not match " +
							"\"This is Site 1\" at \"//html/body/h1\"";
		}

		try {
			selenium.assertText("//html/body/h1", "This is Site 2");
		}
		catch (Throwable e) {
			assertEquals(e.getMessage(), expectedException);
		}
	}

	@Test
	public void testAssertText() throws Exception {
		System.out.println("testAssertText running");
		selenium.assertText("//html/body/h1", "This is Site 1");
		selenium.clickAt("//html/body/p[1]/a", RuntimeVariables.replace(""));
		selenium.assertText("//html/body/h1", "This is Site 2");
		selenium.clickAt("//html/body/p[2]/a", RuntimeVariables.replace(""));
		selenium.assertText("//html/body/h1", "This is Site 3");
		selenium.clickAt("//html/body/p[3]/a", RuntimeVariables.replace(""));
		selenium.assertText("//html/body/h1", "This is Site 4");
		selenium.clickAt("//html/body/p[4]/a", RuntimeVariables.replace(""));
		selenium.assertText("//html/body/h1", "This is Site 5");
		selenium.clickAt("//html/body/p[5]/a", RuntimeVariables.replace(""));
		selenium.assertText("//html/body/h1", "This is Site 6");
		selenium.clickAt("//html/body/p[1]/a", RuntimeVariables.replace(""));
	}

}