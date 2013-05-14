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

package com.liferay.portalweb.portal.permissions.imagegallery.assertactions;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SiteAdmin_AssertActionTest extends BaseTestCase {
	public void testSiteAdmin_AssertAction() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Look and Feel')]/a");
		assertEquals(RuntimeVariables.replace("Look and Feel"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Look and Feel')]/a"));
		assertEquals(RuntimeVariables.replace("Configuration"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Configuration')]/a"));
		assertEquals(RuntimeVariables.replace("Export / Import"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Export / Import')]/a"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Permissions')]/a"));
		assertEquals(RuntimeVariables.replace("Add Folder"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Folder')]/a"));
		assertEquals(RuntimeVariables.replace("Add Repository"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Repository')]/a"));
		assertEquals(RuntimeVariables.replace("Multiple Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Multiple Media')]/a"));
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Media')]/a"));
		assertEquals(RuntimeVariables.replace("Add Shortcut"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Shortcut')]/a"));
		assertEquals(RuntimeVariables.replace("Access from Desktop"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Access from Desktop')]/a"));
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder 2 Edited"),
			selenium.getText("xpath=(//span[@class='image-title'])[2]"));
		selenium.clickAt("xpath=(//span[@class='image-title'])[2]",
			RuntimeVariables.replace(
				"Media Gallery Permissions Test Folder 2 Edited"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Edit')]/a"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Move')]/a"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Permissions')]/a"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Move to the Recycle Bin')]/a"));
		assertEquals(RuntimeVariables.replace("Add Subfolder"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Subfolder')]/a"));
		assertEquals(RuntimeVariables.replace("Multiple Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Multiple Media')]/a"));
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Media')]/a"));
		assertEquals(RuntimeVariables.replace("Add Shortcut"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Shortcut')]/a"));
		assertEquals(RuntimeVariables.replace("Access from Desktop"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Access from Desktop')]/a"));
		assertEquals(RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("//span[@class='image-title']",
			RuntimeVariables.replace(
				"Media Gallery Permissions Test Subfolder 2"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Edit')]/a"));
		assertEquals(RuntimeVariables.replace("Move"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Move')]/a"));
		assertEquals(RuntimeVariables.replace("Permissions"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Permissions')]/a"));
		assertEquals(RuntimeVariables.replace("Move to the Recycle Bin"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Move to the Recycle Bin')]/a"));
		assertEquals(RuntimeVariables.replace("Add Subfolder"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Subfolder')]/a"));
		assertEquals(RuntimeVariables.replace("Multiple Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Multiple Media')]/a"));
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Media')]/a"));
		assertEquals(RuntimeVariables.replace("View Slide Show"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'View Slide Show')]/a"));
		assertEquals(RuntimeVariables.replace("Add Shortcut"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Shortcut')]/a"));
		assertEquals(RuntimeVariables.replace("Access from Desktop"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Access from Desktop')]/a"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Mine"),
			selenium.getText(
				"//ul[@class='top-links-navigation']/li/span[contains(.,'Mine')]/a"));
		selenium.clickAt("//ul[@class='top-links-navigation']/li/span[contains(.,'Mine')]/a",
			RuntimeVariables.replace("Mine"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image Test Edited"),
			selenium.getText("//span[@class='image-title']"));
		Thread.sleep(1000);
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//span[@title='Options']/ul/li/strong/a"));
		selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Export / Import')]/a");
		assertEquals(RuntimeVariables.replace("Export / Import"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Export / Import')]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[contains(.,'Export / Import')]/a",
			RuntimeVariables.replace("Export / Import"));
		selenium.waitForVisible("//input[@value='Export']");
		assertTrue(selenium.isVisible("//input[@value='Export']"));
		selenium.clickAt("link=Import", RuntimeVariables.replace("Import"));
		selenium.waitForVisible("//input[@value='Import']");
		assertTrue(selenium.isVisible("//input[@value='Import']"));
	}
}