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

package com.liferay.portalweb.socialofficesite.documents.dmdocument.editpermissionsfolderdocument2guestnoview;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class EditPermissionsFolderDocument2GuestNoViewTest extends BaseTestCase {
	public void testEditPermissionsFolderDocument2GuestNoView()
		throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				assertEquals(RuntimeVariables.replace("Documents"),
					selenium.getText(
						"//nav/ul/li[contains(.,'Documents')]/a/span"));
				selenium.clickAt("//nav/ul/li[contains(.,'Documents')]/a/span",
					RuntimeVariables.replace("Documents"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("DM Folder Name"),
					selenium.getText(
						"//a[contains(@class,'document-link')]/span[@class='entry-title']"));
				selenium.clickAt("//a[contains(@class,'document-link')]/span[@class='entry-title']",
					RuntimeVariables.replace("DM Folder Name"));
				selenium.waitForText("//li[@class='folder selected']/a",
					"DM Folder Name");
				assertEquals(RuntimeVariables.replace("DM Folder Name"),
					selenium.getText("//li[@class='folder selected']/a"));
				assertEquals(RuntimeVariables.replace(
						"DM Folder Document1 Title"),
					selenium.getText(
						"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[1]"));
				assertEquals(RuntimeVariables.replace(
						"DM Folder Document2 Title"),
					selenium.getText(
						"xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]"));
				selenium.clickAt("xPath=(//a[contains(@class,'document-link')]/span[@class='entry-title'])[2]",
					RuntimeVariables.replace("DM Folder Document2 Title"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"DM Folder Document2 Title"),
					selenium.getText("//span[@class='document-title']"));
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText("//button[5]"));
				selenium.clickAt("//button[5]",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForVisible("//iframe[@id='_20_permissions']");
				selenium.selectFrame("//iframe[@id='_20_permissions']");
				selenium.waitForVisible("//input[@id='guest_ACTION_VIEW']");

				boolean guestActionChecked = selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']");

				if (!guestActionChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='guest_ACTION_VIEW']",
					RuntimeVariables.replace("Guest Action Checkbox"));

			case 2:
				assertFalse(selenium.isChecked(
						"//input[@id='guest_ACTION_VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}