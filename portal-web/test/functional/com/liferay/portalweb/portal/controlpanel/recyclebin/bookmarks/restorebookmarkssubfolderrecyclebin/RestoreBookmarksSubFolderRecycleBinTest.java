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

package com.liferay.portalweb.portal.controlpanel.recyclebin.bookmarks.restorebookmarkssubfolderrecyclebin;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class RestoreBookmarksSubFolderRecycleBinTest extends BaseTestCase {
	public void testRestoreBookmarksSubFolderRecycleBin()
		throws Exception {
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
		assertEquals(RuntimeVariables.replace("Bookmark Subfolder Name"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Subfolder Name')]/td[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span	"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span	",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Restore')]");
		assertEquals(RuntimeVariables.replace("Restore"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Restore')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Restore')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"The item has been restored to Home \u00bb Bookmark Folder Name"),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"Entries that have been in Recycle Bin for more than 30 days will be automatically deleted."),
			selenium.getText(
				"//div[@class='lfr-message-info taglib-trash-empty']"));
		assertEquals(RuntimeVariables.replace("The Recycle Bin is empty."),
			selenium.getText("//div[@class='portlet-msg-info']"));
	}
}