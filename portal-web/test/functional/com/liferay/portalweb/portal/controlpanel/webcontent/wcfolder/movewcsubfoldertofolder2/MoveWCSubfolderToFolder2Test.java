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
				"//div/a[contains(.,'WC Folder1')]/div/img"));
		assertEquals(RuntimeVariables.replace("WC Folder1"),
			selenium.getText("//div/a[contains(.,'WC Folder1')]/span"));
		assertTrue(selenium.isVisible(
				"//div/a[contains(.,'WC Folder2')]/div/img"));
		assertEquals(RuntimeVariables.replace("WC Folder2"),
			selenium.getText("//div/a[contains(.,'WC Folder2')]/span"));
		selenium.clickAt("//div/a[contains(.,'WC Folder1')]/span",
			RuntimeVariables.replace("WC Folder1"));
		Thread.sleep(1000);
		selenium.waitForVisible("//div/a[contains(.,'WC Subfolder')]/div/img");
		assertEquals(RuntimeVariables.replace("WC Subfolder"),
			selenium.getText("//div/a[contains(.,'WC Subfolder')]/span"));
		selenium.clickAt("//div[@data-title='WC Subfolder']/span[2]/span/ul/li/strong/a",
			RuntimeVariables.replace("Folder Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Move')]");
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Move')]"));
		selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Move')]",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Parent Folder"),
			selenium.getText(
				"//form[contains(@action,'move_folder')]/fieldset/div/div/div/label"));
		assertEquals(RuntimeVariables.replace("WC Folder1"),
			selenium.getText("//a[@id='_15_parentFolderName']"));
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Web Content");
		selenium.waitForVisible(
			"//ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li/span/a[contains(.,'Home')]");
		selenium.clickAt("//ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li/span/a[contains(.,'Home')]",
			RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		selenium.click(
			"//tr[contains(.,'WC Folder2')]/td[4]/input[@value='Choose']");
		selenium.selectWindow("null");
		Thread.sleep(1000);
		selenium.waitForVisible("//a[@id='_15_parentFolderName']");
		assertEquals(RuntimeVariables.replace("WC Folder2"),
			selenium.getText("//a[@id='_15_parentFolderName']"));
		selenium.clickAt("//input[@value='Move']",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("No Web Content was found."),
			selenium.getText("//div[contains(@class,'portlet-msg-info')]"));
		assertEquals(RuntimeVariables.replace("WC Folder2"),
			selenium.getText("//div[@class='body-row']/div/div/ul/li[3]/a[2]"));
		selenium.clickAt("//div[@class='body-row']/div/div/ul/li[3]/a[2]",
			RuntimeVariables.replace("WC Folder2"));
		Thread.sleep(1000);
		selenium.waitForVisible("//div/a[contains(.,'WC Subfolder')]/div/img");
		assertEquals(RuntimeVariables.replace("WC Subfolder"),
			selenium.getText("//div/a[contains(.,'WC Subfolder')]/span"));
	}
}