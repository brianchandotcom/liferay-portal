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

package com.liferay.portalweb.portlet.bookmarks.folder.deletefolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class DeleteFolderTest extends BaseTestCase {
	public void testDeleteFolder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace("Bookmarks Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bookmark Folder Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]");
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move to the Recycle Bin')]",
			RuntimeVariables.replace("Move to the Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isPartialText(
				"//div[@class='portlet-msg-success taglib-trash-undo']",
				"The selected item was moved to the Recycle Bin."));
		assertEquals(RuntimeVariables.replace(
				"There are no bookmarks in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertFalse(selenium.isTextPresent("Bookmark Folder Name"));
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
		assertEquals(RuntimeVariables.replace("Empty the Recycle Bin"),
			selenium.getText("//a[@class='trash-empty-link']"));
		selenium.clickAt("//a[@class='trash-empty-link']",
			RuntimeVariables.replace("Empty the Recycle Bin"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForConfirmation(
			"Are you sure you want to empty the Recycle Bin?");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("The Recycle Bin is empty."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}