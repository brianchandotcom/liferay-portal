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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.blogs.pagescopelarcp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.BrowserCommands;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ExportLARCPPage2BlogsPageScopeTest extends BaseTestCase {
	public void testExportLARCPPage2BlogsPageScope() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/blogs-page-scope-community/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("link=Blogs");
		selenium.clickAt("link=Blogs", RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Scope: Default"),
			selenium.getText("//div/span/ul/li/strong/a/span"));
		selenium.clickAt("//div/span/ul/li/strong/a/span",
			RuntimeVariables.replace("Scope: Default"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a");
		assertEquals(RuntimeVariables.replace("Blogs Test Page2"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[2]/a"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li[2]/a",
			RuntimeVariables.replace("Blogs Test Page2"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForText("//div/span/ul/li/strong/a/span",
			"Scope: Blogs Test Page2");
		assertEquals(RuntimeVariables.replace("Scope: Blogs Test Page2"),
			selenium.getText("//div/span/ul/li/strong/a/span"));
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//menu/span/ul/li/strong/a"));
		selenium.clickAt("//menu/span/ul/li/strong/a",
			RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a");
		assertEquals(RuntimeVariables.replace("Export / Import"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[1]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_86_exportFileName']",
			RuntimeVariables.replace("Blogs_Page_Scope.Page2.CP.lar"));
		assertFalse(selenium.isChecked(
				"//input[@id='_86_PORTLET_DATA_161Checkbox']"));
		selenium.clickAt("//input[@id='_86_PORTLET_DATA_161Checkbox']",
			RuntimeVariables.replace("Data"));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_PORTLET_DATA_161Checkbox']"));
		assertFalse(selenium.isChecked("//input[@id='_86_PERMISSIONSCheckbox']"));
		selenium.clickAt("//input[@id='_86_PERMISSIONSCheckbox']",
			RuntimeVariables.replace("Permissions"));
		assertTrue(selenium.isChecked("//input[@id='_86_PERMISSIONSCheckbox']"));
		assertFalse(selenium.isChecked("//input[@id='_86_CATEGORIESCheckbox']"));
		selenium.clickAt("//input[@id='_86_CATEGORIESCheckbox']",
			RuntimeVariables.replace("Categories"));
		assertTrue(selenium.isChecked("//input[@id='_86_CATEGORIESCheckbox']"));
		Thread.sleep(5000);
		selenium.clickAt("//input[@value='Export']",
			RuntimeVariables.replace("Export"));
		BrowserCommands.downloadTempFile("Blogs_Page_Scope.Page2.CP.lar");
	}
}