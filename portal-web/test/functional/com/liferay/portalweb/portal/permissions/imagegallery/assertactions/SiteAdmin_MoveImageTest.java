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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteAdmin_MoveImageTest extends BaseTestCase {
	public void testSiteAdmin_MoveImage() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder"),
			selenium.getText("xpath=(//span[@class='image-title'])[1]"));
		selenium.clickAt("xpath=(//span[@class='image-title'])[1]",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("Media Gallery Permissions Test Subfolder"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image Test"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace("Permissions Image Test"));
		Thread.sleep(1000);
		selenium.waitForVisible("//img[@title='View']");
		selenium.clickAt("//img[@title='View']",
			RuntimeVariables.replace("View"));
		selenium.waitForVisible("//button[3]");
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText("//button[3]"));
		selenium.clickAt("//button[3]", RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//input[@value='Select']");
		selenium.clickAt("//input[@value='Select']",
			RuntimeVariables.replace("Select"));
		Thread.sleep(5000);
		selenium.selectWindow("title=Media Gallery");
		selenium.waitForVisible("link=Home");
		selenium.clickAt("link=Home", RuntimeVariables.replace("Home"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//tr[contains(.,'Media Gallery Permissions Test Folder 2')]/td[1]/a");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder 2"),
			selenium.getText(
				"//tr[contains(.,'Media Gallery Permissions Test Folder 2')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Media Gallery Permissions Test Folder 2')]/td[1]/a",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder 2"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//tr[contains(.,'Media Gallery Permissions Test Subfolder 2')]/td[1]/a");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"),
			selenium.getText(
				"//tr[contains(.,'Media Gallery Permissions Test Subfolder 2')]/td[1]/a"));
		selenium.clickAt("//tr[contains(.,'Media Gallery Permissions Test Subfolder 2')]/td[1]/a",
			RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible(
			"//ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li[@class='last']/span/a");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"),
			selenium.getText(
				"//ul[@class='breadcrumbs breadcrumbs-horizontal lfr-component']/li[@class='last']/span/a"));
		selenium.click("//input[@value='Choose This Folder']");
		selenium.selectWindow("null");
		Thread.sleep(1000);
		selenium.waitForText("//a[@id='_31_folderName']",
			"Media Gallery Permissions Test Subfolder 2");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"),
			selenium.getText("//a[@id='_31_folderName']"));
		selenium.waitForVisible("//input[@value='Move']");
		selenium.clickAt("//input[@value='Move']",
			RuntimeVariables.replace("Move"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForElementPresent(
			"//script[contains(@src,'/liferay/navigation_interaction.js')]");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace(
				"There are no media files in this folder."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder 2"),
			selenium.getText("xpath=(//span[@class='image-title'])[2]"));
		selenium.clickAt("xpath=(//span[@class='image-title'])[2]",
			RuntimeVariables.replace("Media Gallery Permissions Test Folder 2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image Test"),
			selenium.getText("//span[@class='image-title']"));
	}
}