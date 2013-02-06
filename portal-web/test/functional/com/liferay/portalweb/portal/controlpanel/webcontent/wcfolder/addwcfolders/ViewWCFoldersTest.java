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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.addwcfolders;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewWCFoldersTest extends BaseTestCase {
	public void testViewWCFolders() throws Exception {
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
		selenium.clickAt("link=Web Content",
			RuntimeVariables.replace("Web Content"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//h1[@class='portlet-title']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//div[@class='parent-title']/span"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Home')]"));
		assertEquals(RuntimeVariables.replace("Recent"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Recent')]"));
		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Mine')]"));
		assertTrue(selenium.isVisible("//input[@id='_15_allRowIdsCheckbox']"));
		assertFalse(selenium.isVisible(
				"//span[@title='Actions']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
		assertTrue(selenium.isVisible("//button[@title='Icon View']"));
		assertTrue(selenium.isVisible("//button[@title='Descriptive View']"));
		assertTrue(selenium.isVisible("//button[@title='List View']"));
		assertTrue(selenium.isVisible("//input[@id='_15_keywords']"));
		assertEquals("Search", selenium.getValue("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@id='_15_showAdvancedSearch']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='journal-breadcrumb']/ul/li[@class='only']/span/a"));
		assertTrue(selenium.isVisible(
				"//div[@data-title='WC Folder1 Name']/a/div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("WC Folder1 Name"),
			selenium.getText(
				"//div[@data-title='WC Folder1 Name']/a/span[@class='entry-title']"));
		assertTrue(selenium.isVisible(
				"//div[@data-title='WC Folder2 Name']/a/div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("WC Folder2 Name"),
			selenium.getText(
				"//div[@data-title='WC Folder2 Name']/a/span[@class='entry-title']"));
		assertTrue(selenium.isVisible(
				"//div[@data-title='WC Folder3 Name']/a/div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("WC Folder3 Name"),
			selenium.getText(
				"//div[@data-title='WC Folder3 Name']/a/span[@class='entry-title']"));
		assertEquals(RuntimeVariables.replace("<<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[1]"));
		assertEquals(RuntimeVariables.replace("<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[2]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[1]"));
		assertEquals(RuntimeVariables.replace(">"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[3]"));
		assertEquals(RuntimeVariables.replace(">>"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[4]"));
		assertEquals(RuntimeVariables.replace("1 of 1"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[2]"));
		assertEquals(RuntimeVariables.replace("(Total 3)"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[3]"));
		assertEquals("20",
			selenium.getSelectedLabel(
				"//div[contains(@class,'article-entries-paginator')]/select"));
		selenium.clickAt("//div[@data-title='WC Folder1 Name']/a/span[@class='entry-title']",
			RuntimeVariables.replace("WC Folder1 Name"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//h1[@class='portlet-title']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//div[@class='parent-title']/span"));
		assertEquals(RuntimeVariables.replace("Up"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Up')]"));
		assertTrue(selenium.isVisible(
				"//li[@class='app-view-navigation-entry folder selected']/a[contains(.,'WC Folder1 Name')]"));
		assertEquals(RuntimeVariables.replace("WC Folder1 Name"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder1 Name')]"));
		assertEquals(RuntimeVariables.replace("WC Folder2 Name"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder2 Name')]"));
		assertEquals(RuntimeVariables.replace("WC Folder3 Name"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder3 Name')]"));
		assertFalse(selenium.isVisible(
				"//span[@title='Actions']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
		assertTrue(selenium.isVisible("//button[@title='Icon View']"));
		assertTrue(selenium.isVisible("//button[@title='Descriptive View']"));
		assertTrue(selenium.isVisible("//button[@title='List View']"));
		assertTrue(selenium.isVisible("//input[@id='_15_keywords']"));
		assertEquals("Search", selenium.getValue("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@id='_15_showAdvancedSearch']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='journal-breadcrumb']/ul/li[@class='first']/span/a"));
		assertEquals(RuntimeVariables.replace("WC Folder1 Name"),
			selenium.getText(
				"//div[@class='journal-breadcrumb']/ul/li[@class='last']/span/a"));
		assertEquals(RuntimeVariables.replace("No Web Content was found."),
			selenium.getText("//div[@class='entries-empty portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("<<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[1]"));
		assertEquals(RuntimeVariables.replace("<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[1]"));
		assertEquals(RuntimeVariables.replace(">"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[3]"));
		assertEquals(RuntimeVariables.replace(">>"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[4]"));
		assertEquals(RuntimeVariables.replace("0 of 0"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[2]"));
		assertEquals(RuntimeVariables.replace("(Total 0)"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[3]"));
		assertEquals("20",
			selenium.getSelectedLabel(
				"//div[contains(@class,'article-entries-paginator')]/select"));
		selenium.clickAt("//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Up')]",
			RuntimeVariables.replace("Up"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("WC Folder2 Name"),
			selenium.getText(
				"//div[@data-title='WC Folder2 Name']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='WC Folder2 Name']/a/span[@class='entry-title']",
			RuntimeVariables.replace("WC Folder2 Name"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//h1[@class='portlet-title']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//div[@class='parent-title']/span"));
		assertEquals(RuntimeVariables.replace("Up"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Up')]"));
		assertEquals(RuntimeVariables.replace("WC Folder1 Name"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder1 Name')]"));
		assertTrue(selenium.isVisible(
				"//li[@class='app-view-navigation-entry folder selected']/a[contains(.,'WC Folder2 Name')]"));
		assertEquals(RuntimeVariables.replace("WC Folder2 Name"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder2 Name')]"));
		assertEquals(RuntimeVariables.replace("WC Folder3 Name"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder3 Name')]"));
		assertFalse(selenium.isVisible(
				"//span[@title='Actions']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
		assertTrue(selenium.isVisible("//button[@title='Icon View']"));
		assertTrue(selenium.isVisible("//button[@title='Descriptive View']"));
		assertTrue(selenium.isVisible("//button[@title='List View']"));
		assertTrue(selenium.isVisible("//input[@id='_15_keywords']"));
		assertEquals("Search", selenium.getValue("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@id='_15_showAdvancedSearch']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='journal-breadcrumb']/ul/li[@class='first']/span/a"));
		assertEquals(RuntimeVariables.replace("WC Folder2 Name"),
			selenium.getText(
				"//div[@class='journal-breadcrumb']/ul/li[@class='last']/span/a"));
		assertEquals(RuntimeVariables.replace("No Web Content was found."),
			selenium.getText("//div[@class='entries-empty portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("<<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[1]"));
		assertEquals(RuntimeVariables.replace("<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[1]"));
		assertEquals(RuntimeVariables.replace(">"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[3]"));
		assertEquals(RuntimeVariables.replace(">>"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[4]"));
		assertEquals(RuntimeVariables.replace("0 of 0"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[2]"));
		assertEquals(RuntimeVariables.replace("(Total 0)"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[3]"));
		assertEquals("20",
			selenium.getSelectedLabel(
				"//div[contains(@class,'article-entries-paginator')]/select"));
		selenium.clickAt("//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Up')]",
			RuntimeVariables.replace("Up"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("WC Folder3 Name"),
			selenium.getText(
				"//div[@data-title='WC Folder3 Name']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='WC Folder3 Name']/a/span[@class='entry-title']",
			RuntimeVariables.replace("WC Folder3 Name"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Web Content"),
			selenium.getText("//h1[@class='portlet-title']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText("//div[@class='parent-title']/span"));
		assertEquals(RuntimeVariables.replace("Up"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'Up')]"));
		assertEquals(RuntimeVariables.replace("WC Folder1 Name"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder1 Name')]"));
		assertEquals(RuntimeVariables.replace("WC Folder2 Name"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder2 Name')]"));
		assertTrue(selenium.isVisible(
				"//li[@class='app-view-navigation-entry folder selected']/a[contains(.,'WC Folder3 Name')]"));
		assertEquals(RuntimeVariables.replace("WC Folder3 Name"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder3 Name')]"));
		assertFalse(selenium.isVisible(
				"//span[@title='Actions']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a"));
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
		assertTrue(selenium.isVisible("//button[@title='Icon View']"));
		assertTrue(selenium.isVisible("//button[@title='Descriptive View']"));
		assertTrue(selenium.isVisible("//button[@title='List View']"));
		assertTrue(selenium.isVisible("//input[@id='_15_keywords']"));
		assertEquals("Search", selenium.getValue("//input[@value='Search']"));
		assertTrue(selenium.isVisible("//input[@id='_15_showAdvancedSearch']"));
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//div[@class='journal-breadcrumb']/ul/li[@class='first']/span/a"));
		assertEquals(RuntimeVariables.replace("WC Folder3 Name"),
			selenium.getText(
				"//div[@class='journal-breadcrumb']/ul/li[@class='last']/span/a"));
		assertEquals(RuntimeVariables.replace("No Web Content was found."),
			selenium.getText("//div[@class='entries-empty portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("<<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[1]"));
		assertEquals(RuntimeVariables.replace("<"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[2]"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[1]"));
		assertEquals(RuntimeVariables.replace(">"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[3]"));
		assertEquals(RuntimeVariables.replace(">>"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/a[4]"));
		assertEquals(RuntimeVariables.replace("0 of 0"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[2]"));
		assertEquals(RuntimeVariables.replace("(Total 0)"),
			selenium.getText(
				"//div[contains(@class,'article-entries-paginator')]/span[3]"));
		assertEquals("20",
			selenium.getSelectedLabel(
				"//div[contains(@class,'article-entries-paginator')]/select"));
	}
}