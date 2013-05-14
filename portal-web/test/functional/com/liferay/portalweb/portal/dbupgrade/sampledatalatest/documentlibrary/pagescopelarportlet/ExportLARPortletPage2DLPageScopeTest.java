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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.documentlibrary.pagescopelarportlet;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.BrowserCommands;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ExportLARPortletPage2DLPageScopeTest extends BaseTestCase {
	public void testExportLARPortletPage2DLPageScope()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-page-scope-community/");
		selenium.waitForVisible("link=DL Page2 Name");
		selenium.clickAt("link=DL Page2 Name",
			RuntimeVariables.replace("DL Page2 Name"));
		selenium.waitForPageToLoad("30000");
		Thread.sleep(5000);
		selenium.waitForText("//span[@class='portlet-title-text']",
			"Documents and Media (DL Page2 Name)");
		assertEquals(RuntimeVariables.replace(
				"Documents and Media (DL Page2 Name)"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Options"),
			selenium.getText("//strong/a"));
		selenium.clickAt("//strong/a", RuntimeVariables.replace("Options"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a");
		assertEquals(RuntimeVariables.replace("Export / Import"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[3]/a"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_86_exportFileName']",
			RuntimeVariables.replace("DL_Page_Scope.Page2.Portlet.lar"));
		assertFalse(selenium.isChecked(
				"//input[@id='_86_PORTLET_USER_PREFERENCESCheckbox']"));
		selenium.clickAt("//input[@id='_86_PORTLET_USER_PREFERENCESCheckbox']",
			RuntimeVariables.replace("User Preferences"));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_PORTLET_USER_PREFERENCESCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_86_PORTLET_DATA_20Checkbox']"));
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
		BrowserCommands.downloadTempFile("DL_Page_Scope.Page2.Portlet.lar");
	}
}