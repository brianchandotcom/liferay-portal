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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcfolder.movewcsubfoldertofolder2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class MoveWCSubfolderToFolder2Test extends BaseTestCase {
	public void testMoveWCSubfolderToFolder2() throws Exception {
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
		selenium.clickAt("//div[@data-title='WC Folder1 Name']/a/span[@class='entry-title']",
			RuntimeVariables.replace("WC Folder1 Name"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//div[@data-title='WC Subfolder Name']/a/div[@class='entry-thumbnail']/img");
		assertTrue(selenium.isVisible(
				"//div[@data-title='WC Subfolder Name']/a/div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("WC Subfolder Name"),
			selenium.getText(
				"//div[@data-title='WC Subfolder Name']/a/span[@class='entry-title']"));
		selenium.clickAt("//div[@data-title='WC Subfolder Name']/span[@class='entry-action overlay']/span/ul/li/strong/a",
			RuntimeVariables.replace("WC Subfolder Name Action Overlay"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move')]");
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Move')]",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Move WC Subfolder Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Parent Folder"),
			selenium.getText("//div/label"));
		assertEquals(RuntimeVariables.replace("WC Folder1 Name"),
			selenium.getText("//a[@id='_15_parentFolderName']"));
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Web Content");
		selenium.waitForVisible(
			"//ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li/span/a[contains(.,'Home')]");
		assertEquals(RuntimeVariables.replace("Home"),
			selenium.getText(
				"//ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li/span/a[contains(.,'Home')]"));
		selenium.clickAt("//ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li/span/a[contains(.,'Home')]",
			RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("WC Folder2 Name"),
			selenium.getText("//tr[contains(.,'WC Folder2 Name')]/td[1]/a"));
		selenium.click(
			"//tr[contains(.,'WC Folder2 Name')]/td[4]/input[@value='Choose']");
		selenium.selectWindow("null");
		Thread.sleep(1000);
		selenium.waitForVisible("//a[@id='_15_parentFolderName']");
		assertEquals(RuntimeVariables.replace("WC Folder2 Name"),
			selenium.getText("//a[@id='_15_parentFolderName']"));
		selenium.clickAt("//input[@value='Move']",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("No Web Content was found."),
			selenium.getText("//div[@class='entries-empty portlet-msg-info']"));
		assertEquals(RuntimeVariables.replace("WC Folder2 Name"),
			selenium.getText(
				"//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder2 Name')]"));
		selenium.clickAt("//div[contains(@class,'lfr-list-view-content folder-display')]/ul/li/a[contains(.,'WC Folder2 Name')]",
			RuntimeVariables.replace("WC Folder2 Name"));
		Thread.sleep(1000);
		selenium.waitForVisible(
			"//div[@data-title='WC Subfolder Name']/a/div[@class='entry-thumbnail']/img");
		assertTrue(selenium.isVisible(
				"//div[@data-title='WC Subfolder Name']/a/div[@class='entry-thumbnail']/img"));
		assertEquals(RuntimeVariables.replace("WC Subfolder Name"),
			selenium.getText(
				"//div[@data-title='WC Subfolder Name']/a/span[@class='entry-title']"));
	}
}