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

package com.liferay.portalweb.portal.controlpanel.recyclebin.webcontent.movetorecyclebinwcwebcontentwcd;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewMoveToRecycleBinWCWebContentWCDRecycleBinTest
	extends BaseTestCase {
	public void testViewMoveToRecycleBinWCWebContentWCDRecycleBin()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Web Content Display Test Page",
			RuntimeVariables.replace("Web Content Display Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The selected web content no longer exists."),
			selenium.getText("//div[@class='portlet-msg-error']"));
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
		assertEquals(RuntimeVariables.replace(
				"Entries that have been in Recycle Bin for more than 30 days will be automatically deleted. Empty the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-message-info taglib-trash-empty']/form"));
		assertEquals(RuntimeVariables.replace("Empty the Recycle Bin"),
			selenium.getText("//a[@class='trash-empty-link']"));
		assertTrue(selenium.isVisible("//input[@title='Search Entries']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("WC WebContent Title"),
			selenium.getText(
				"//tr[contains(.,'WC WebContent Title')]/td[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[2]"));
		assertTrue(selenium.isVisible(
				"//tr[contains(.,'WC WebContent Title')]/td[3]"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//tr[contains(.,'WC WebContent Title')]/td[4]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'WC WebContent Title')]/td[5]/span/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
	}
}