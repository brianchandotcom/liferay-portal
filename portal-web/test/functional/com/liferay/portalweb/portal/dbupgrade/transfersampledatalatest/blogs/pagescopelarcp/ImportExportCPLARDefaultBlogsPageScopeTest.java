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

package com.liferay.portalweb.portal.dbupgrade.transfersampledatalatest.blogs.pagescopelarcp;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportExportCPLARDefaultBlogsPageScopeTest extends BaseTestCase {
	public void testImportExportCPLARDefaultBlogsPageScope()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/blogs-page-scope-community/");
		selenium.waitForElementPresent("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Blogs", RuntimeVariables.replace("Blogs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Scope: Default"),
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
		selenium.clickAt("link=Import", RuntimeVariables.replace("Import"));
		selenium.waitForVisible("//input[@id='_86_importFileName']");
		selenium.uploadTempFile("//input[@id='_86_importFileName']",
			RuntimeVariables.replace("Blogs_Page_Scope.Default.CP.lar"));
		assertFalse(selenium.isChecked(
				"//input[@id='_86_DELETE_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@id='_86_DELETE_PORTLET_DATACheckbox']",
			RuntimeVariables.replace("Delete portlet data before importing."));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_DELETE_PORTLET_DATACheckbox']"));
		assertFalse(selenium.isChecked(
				"//input[@id='_86_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@id='_86_PORTLET_DATACheckbox']",
			RuntimeVariables.replace("Data"));
		assertTrue(selenium.isChecked("//input[@id='_86_PORTLET_DATACheckbox']"));
		assertFalse(selenium.isChecked("//input[@id='_86_PERMISSIONSCheckbox']"));
		selenium.clickAt("//input[@id='_86_PERMISSIONSCheckbox']",
			RuntimeVariables.replace("Permissions"));
		assertTrue(selenium.isChecked("//input[@id='_86_PERMISSIONSCheckbox']"));
		assertFalse(selenium.isChecked("//input[@id='_86_CATEGORIESCheckbox']"));
		selenium.clickAt("//input[@id='_86_CATEGORIESCheckbox']",
			RuntimeVariables.replace("Categories"));
		assertTrue(selenium.isChecked("//input[@id='_86_CATEGORIESCheckbox']"));
		selenium.clickAt("//input[@value='Import']",
			RuntimeVariables.replace("Import"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}