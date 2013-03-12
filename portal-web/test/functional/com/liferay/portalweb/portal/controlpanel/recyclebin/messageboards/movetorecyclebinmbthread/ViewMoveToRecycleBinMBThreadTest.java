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

package com.liferay.portalweb.portal.controlpanel.recyclebin.messageboards.movetorecyclebinmbthread;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMoveToRecycleBinMBThreadTest extends BaseTestCase {
	public void testViewMoveToRecycleBinMBThread() throws Exception {
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
		selenium.clickAt("link=Recycle Bin",
			RuntimeVariables.replace("Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Recycle Bin"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace(
				"Entries that have been in the Recycle Bin for more than 1 month will be automatically deleted. Empty the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-message-info taglib-trash-empty']/form"));
		assertEquals(RuntimeVariables.replace("Empty the Recycle Bin"),
			selenium.getText("//a[@class='trash-empty-link']"));
		assertTrue(selenium.isVisible("//input[@title='Search Entries']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("MB Thread Message Subject"),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Message Boards Thread"),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[2]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'MB Thread Message Subject')]/td[3]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[4]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'MB Thread Message Subject')]/td[5]/span/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
	}
}