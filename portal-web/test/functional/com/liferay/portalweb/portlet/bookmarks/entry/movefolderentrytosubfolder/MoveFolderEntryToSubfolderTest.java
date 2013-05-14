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

package com.liferay.portalweb.portlet.bookmarks.entry.movefolderentrytosubfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MoveFolderEntryToSubfolderTest extends BaseTestCase {
	public void testMoveFolderEntryToSubfolder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace("Bookmarks Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bookmark Folder Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong"));
		selenium.clickAt("//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong",
			RuntimeVariables.replace("Bookmark Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bookmark Name"),
			selenium.getText("//tr[contains(.,'Bookmark Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("http://www.liferay.com"),
			selenium.getText("//tr[contains(.,'Bookmark Name')]/td[2]/a"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//td[5]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//td[5]/span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Bookmarks");
		selenium.waitForVisible(
			"//tr[contains(.,'Bookmark Subfolder Name')]/td[1]/a");
		assertEquals(RuntimeVariables.replace("Bookmark Subfolder Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Subfolder Name')]/td[1]/a"));
		selenium.click("//input[@value='Choose']");
		selenium.selectWindow("null");
		assertEquals(RuntimeVariables.replace("Bookmark Subfolder Name"),
			selenium.getText("//a[@id='_28_folderName']"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"There are no bookmarks in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("Bookmark Name"));
		assertFalse(selenium.isTextPresent("http://www.liferay.com"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace("Bookmarks Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bookmark Folder Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong"));
		selenium.clickAt("//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong",
			RuntimeVariables.replace("Bookmark Folder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bookmark Subfolder Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Subfolder Name')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Bookmark Subfolder Name')]/td[1]/a",
			RuntimeVariables.replace("Bookmark Subfolder Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bookmark Name"),
			selenium.getText("//tr[contains(.,'Bookmark Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("http://www.liferay.com"),
			selenium.getText("//tr[contains(.,'Bookmark Name')]/td[2]/a"));
	}
}