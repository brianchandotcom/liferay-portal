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

package com.liferay.portalweb.portal.tags.tagsadmin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddAmpersandTagTest extends BaseTestCase {
	public void testAddAmpersandTag() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Tags", RuntimeVariables.replace("Tags"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@id='_99_addTagButton']",
			RuntimeVariables.replace("Add Tag"));
		selenium.waitForVisible("//input[@id='_99_name']");
		selenium.type("//input[@id='_99_name']",
			RuntimeVariables.replace("&test"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForVisible("//div[@id='portletMessages']");
		assertEquals(RuntimeVariables.replace(
				"Tag names cannot be an empty string or contain characters such as: \n , = > / < [ { % | + # ? \" ; / * ~."),
			selenium.getText("//div[@id='portletMessages']"));
		assertTrue(selenium.isElementNotPresent("link=&test"));
	}
}