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

package com.liferay.portalweb.portlet.bookmarks.folder.editfolder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewEditFolderTest extends BaseTestCase {
	public void testViewEditFolder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Bookmarks Test Page",
			RuntimeVariables.replace("Bookmarks Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Bookmark Folder Name Edit"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[contains(.,'Bookmark Folder Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("0"),
			selenium.getText("//tr[contains(.,'Bookmark Folder Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Bookmark Folder Name')]/td[4]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Bookmark Folder Name')]/td[1]/a/strong",
			RuntimeVariables.replace("Bookmark Folder Name Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a/span[contains(.,'Home')]"));
		assertEquals(RuntimeVariables.replace("Recent"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a/span[contains(.,'Recent')]"));
		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span/a/span[contains(.,'Mine')]"));
		assertTrue(selenium.isVisible("//input[@title='Search Bookmarks']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace("Bookmark Folder Name Edit"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("\u00ab Back to Home"),
			selenium.getText("//span[@class='header-back-to']"));
		assertTrue(selenium.isVisible(
				"//div[@class='lfr-asset-icon lfr-asset-date']"));
		assertEquals(RuntimeVariables.replace("0 Subfolders"),
			selenium.getText(
				"//div[@class='lfr-asset-icon lfr-asset-subfolders']"));
		assertEquals(RuntimeVariables.replace("0 Entries"),
			selenium.getText(
				"//div[@class='lfr-asset-icon lfr-asset-items last']"));
		assertEquals(RuntimeVariables.replace("Bookmarks"),
			selenium.getText("//div[@class='lfr-panel-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"There are no bookmarks in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isVisible("//span[@class='lfr-asset-avatar']/img"));
		assertEquals(RuntimeVariables.replace("Bookmark Folder Name Edit"),
			selenium.getText("//div[@class='lfr-asset-name']/h4"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Edit')]"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Permissions')]"));
		assertEquals(RuntimeVariables.replace("Subscribe"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Subscribe')]"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Move to the Recycle Bin')]"));
		assertEquals(RuntimeVariables.replace("Add Subfolder"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Subfolder')]"));
		assertEquals(RuntimeVariables.replace("Add Bookmark"),
			selenium.getText(
				"//div[contains(@class,'lfr-component lfr-menu-list')]/ul/li/a[contains(.,'Add Bookmark')]"));
	}
}