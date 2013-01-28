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
public class SA_GrantMemberAddImageTest extends BaseTestCase {
	public void testSA_GrantMemberAddImage() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
					RuntimeVariables.replace(
						"Media Gallery Permissions Test Folder"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Permissions')]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Permissions')]/a",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForPageToLoad("30000");

				boolean folderAddDocumentChecked = selenium.isChecked(
						"//input[@id='member_ACTION_ADD_DOCUMENT']");

				if (folderAddDocumentChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='member_ACTION_ADD_DOCUMENT']",
					RuntimeVariables.replace("Member Folder Add Document"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='member_ACTION_ADD_DOCUMENT']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked(
						"//input[@id='member_ACTION_ADD_DOCUMENT']"));
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//span/a[@id='_86_TabsBack']"));
				selenium.clickAt("//span/a[@id='_86_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Media Gallery Permissions Test Subfolder"),
					selenium.getText(
						"//a[@title='Media Gallery Permissions Test Subfolder - ']"));
				selenium.clickAt("//a[@title='Media Gallery Permissions Test Subfolder - ']",
					RuntimeVariables.replace(
						"Media Gallery Permissions Test Subfolder"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Permissions"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Permissions')]/a"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li[contains(.,'Permissions')]/a",
					RuntimeVariables.replace("Permissions"));
				selenium.waitForPageToLoad("30000");

				boolean subfolderAddDocumentChecked = selenium.isChecked(
						"//input[@id='member_ACTION_ADD_DOCUMENT']");

				if (subfolderAddDocumentChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='member_ACTION_ADD_DOCUMENT']",
					RuntimeVariables.replace("Member Subfolder Add Document"));

			case 3:
				assertTrue(selenium.isChecked(
						"//input[@id='member_ACTION_ADD_DOCUMENT']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertTrue(selenium.isChecked(
						"//input[@id='member_ACTION_ADD_DOCUMENT']"));

			case 100:
				label = -1;
			}
		}
	}
}