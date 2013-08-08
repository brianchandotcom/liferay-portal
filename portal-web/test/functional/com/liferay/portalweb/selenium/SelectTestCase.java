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
import com.liferay.portalweb.portal.util.TestPropsValues;

import org.junit.Test;

/**
 * @author Kwang Lee
 */
public class SelectTestCase extends BaseTestCase {

	@Override
	public void tearDown() throws Exception {
	}

	@Test
	public void testSelect() throws Exception {
		selenium.select("//html/body/div[1]/select", "value=Test 1");
		selenium.select("//html/body/div[1]/select", "value=Test 2");
		selenium.select("//html/body/div[1]/select", "value=Test 3");
	}

	@Test
	public void testSelectFaile() throws Exception {
		String expectedException = null;

		if (TestPropsValues.SELENIUM_LOGGER_ENABLED) {
			expectedException =
				"Command failure \"select\" with parameters " +
					"\"//Does/Not/Exists\" \"value=Failure\" : null";
		}

		try {
			selenium.select("//Does/Not/Exists", "value=Failure");
		}
		catch (Throwable e) {
			assertEquals(e.getMessage(), expectedException);
		}
	}

}