/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.controlpanel.recyclebin.messageboards.restorembcategoryrecyclebin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewRestoreMBRecycleBinCategoryTest extends BaseTestCase {
	public void testViewRestoreMBRecycleBinCategory() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Message Boards",
			RuntimeVariables.replace("Message Boards"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Message Boards Home"),
			selenium.getText("//ul[@class='aui-tabview-list']/li[1]"));
		assertEquals(RuntimeVariables.replace("Recent Posts"),
			selenium.getText("//ul[@class='aui-tabview-list']/li[2]"));
		assertEquals(RuntimeVariables.replace("Statistics"),
			selenium.getText("//ul[@class='aui-tabview-list']/li[3]"));
		assertEquals(RuntimeVariables.replace("Banned Users"),
			selenium.getText("//ul[@class='aui-tabview-list']/li[4]"));
		assertTrue(selenium.isVisible("//input[@title='Search Messages']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@value='Add Category']"));
		assertTrue(selenium.isVisible("//input[@value='Post New Thread']"));
		assertTrue(selenium.isVisible("//input[@value='Permissions']"));
		assertTrue(selenium.isVisible(
				"xpath=(//input[@value='Move to the Recycle Bin'])"));
		assertEquals(RuntimeVariables.replace("MB Category Name"),
			selenium.getText("//tr[contains(.,'MB Category Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[contains(.,'MB Category Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[contains(.,'MB Category Name')]/td[4]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[contains(.,'MB Category Name')]/td[5]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'MB Category Name')]/td[6]/span/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
	}
}