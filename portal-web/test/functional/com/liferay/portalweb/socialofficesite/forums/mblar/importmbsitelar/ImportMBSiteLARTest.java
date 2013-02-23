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

package com.liferay.portalweb.socialofficesite.forums.mblar.importmbsitelar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportMBSiteLARTest extends BaseTestCase {
	public void testImportMBSiteLAR() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/joebloggs/so/dashboard/");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//div[@id='so-sidebar']/h3"));
		assertTrue(selenium.isVisible("//input[@class='search-input']"));
		selenium.type("//input[@class='search-input']",
			RuntimeVariables.replace("Open"));
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Open Site Name"),
			selenium.getText(
				"//li[contains(@class, 'social-office-enabled')]/span[2]/a"));
		selenium.clickAt("//li[contains(@class, 'social-office-enabled')]/span[2]/a",
			RuntimeVariables.replace("Open Site Name"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/aui-editable/aui-editable-min.js')]");
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//li[@id='_145_manageContent']/a/span"));
		selenium.mouseOver("//li[@id='_145_manageContent']/a/span");
		selenium.waitForVisible("//li[contains(.,'Site Pages')]/a");
		assertEquals(RuntimeVariables.replace("Site Pages"),
			selenium.getText("//li[contains(.,'Site Pages')]/a"));
		selenium.clickAt("//li[contains(.,'Site Pages')]/a",
			RuntimeVariables.replace("Site Pages"));
		selenium.waitForVisible("//iframe[@id='manageContentDialog']");
		selenium.selectFrame("//iframe[@id='manageContentDialog']");
		selenium.waitForVisible("//button[contains(.,'Import')]");
		selenium.clickAt("//button[contains(.,'Import')]",
			RuntimeVariables.replace("Import"));
		selenium.selectFrame("relative=top");
		selenium.waitForVisible("//iframe[@id='_88_importDialog']");
		selenium.selectFrame("//iframe[@id='_88_importDialog']");
		selenium.waitForVisible("//input[@id='_88_importFileName']");
		selenium.uploadFile("//input[@id='_88_importFileName']",
			RuntimeVariables.replace(
				"L:\\portal\\build\\portal-web\\test\\functional\\com\\liferay\\portalweb\\socialofficesite\\forums\\dependencies\\SO_Site_Forums-Selenium.lar"));
		selenium.check("//input[@id='_88_PORTLET_DATACheckbox']");
		assertTrue(selenium.isChecked("//input[@id='_88_PORTLET_DATACheckbox']"));
		selenium.check("//input[@id='_88_DELETE_PORTLET_DATACheckbox']");
		assertTrue(selenium.isChecked(
				"//input[@id='_88_DELETE_PORTLET_DATACheckbox']"));
		selenium.clickAt("//input[@id='_88_PERMISSIONSCheckbox']",
			RuntimeVariables.replace("Permissions"));
		assertTrue(selenium.isChecked("//input[@id='_88_PERMISSIONSCheckbox']"));
		assertTrue(selenium.isChecked(
				"//input[@id='_88_permissionsAssignedToRolesCheckbox']"));
		selenium.clickAt("//input[@id='_88_CATEGORIESCheckbox']",
			RuntimeVariables.replace("Categories"));
		assertTrue(selenium.isChecked("//input[@id='_88_CATEGORIESCheckbox']"));
		selenium.clickAt("//input[@value='Import']",
			RuntimeVariables.replace("Import"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		selenium.selectFrame("relative=top");
	}
}