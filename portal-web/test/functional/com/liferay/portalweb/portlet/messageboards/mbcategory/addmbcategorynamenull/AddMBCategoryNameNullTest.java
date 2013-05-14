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

package com.liferay.portalweb.portlet.messageboards.mbcategory.addmbcategorynamenull;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddMBCategoryNameNullTest extends BaseTestCase {
	public void testAddMBCategoryNameNull() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Add Category']",
			RuntimeVariables.replace("Add Category"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//textarea[@id='_19_description']",
			RuntimeVariables.replace("MB Category Description"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible(
			"//div[@class='form-validator-message required']");
		assertTrue(selenium.isVisible(
				"//div[@class='form-validator-message required']"));
		assertEquals(RuntimeVariables.replace("This field is required."),
			selenium.getText("//div[@class='form-validator-message required']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Message Boards Test Page",
			RuntimeVariables.replace("Message Boards Test Page"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isTextPresent("MB Category Description"));
		assertTrue(selenium.isElementNotPresent(
				"//span[@title='Actions']/ul/li/strong/a"));
	}
}