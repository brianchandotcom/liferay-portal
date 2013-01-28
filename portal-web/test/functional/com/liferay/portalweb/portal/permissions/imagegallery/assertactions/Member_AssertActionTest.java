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
public class Member_AssertActionTest extends BaseTestCase {
	public void testMember_AssertAction() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isElementNotPresent(
				"//span[@title='Options']/ul/li/strong/a"));
		assertTrue(selenium.isElementNotPresent("link=Look and Feel"));
		assertTrue(selenium.isElementNotPresent("link=Configuration"));
		assertTrue(selenium.isElementNotPresent("link=Export / Import"));
		assertTrue(selenium.isElementNotPresent("//img[@title='Remove']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Subfolder')]/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Permissions')]/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Folder')]/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Repository')]/a"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Shortcut')]/a"));
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
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Subfolder')]/a"));
		assertEquals(RuntimeVariables.replace("Multiple Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Multiple Media')]/a"));
		assertEquals(RuntimeVariables.replace("Add Media"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Add Media')]/a"));
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Media Gallery Test Page",
			RuntimeVariables.replace("Media Gallery Test Page"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Mine", RuntimeVariables.replace("Mine"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image 3 Test Edited"),
			selenium.getText("//span[@class='image-title']"));
		selenium.clickAt("link=Recent", RuntimeVariables.replace("Recent"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Permissions Image 2 Test"),
			selenium.getText("xpath=(//span[@class='image-title'])[1]"));
		assertEquals(RuntimeVariables.replace("Permissions Image 3 Test Edited"),
			selenium.getText("xpath=(//span[@class='image-title'])[2]"));
	}
}