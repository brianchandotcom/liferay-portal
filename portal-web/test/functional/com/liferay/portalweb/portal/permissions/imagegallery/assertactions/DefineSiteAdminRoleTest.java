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
public class DefineSiteAdminRoleTest extends BaseTestCase {
	public void testDefineSiteAdminRole() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
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
				selenium.clickAt("link=Roles", RuntimeVariables.replace("Roles"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_128_keywords']",
					RuntimeVariables.replace("SiteAdmin"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("SiteAdmin"),
					selenium.getText("//tr[3]/td/a"));
				selenium.clickAt("//tr[3]/td/a",
					RuntimeVariables.replace("SiteAdmin"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Define Permissions"),
					selenium.getText("link=Define Permissions"));
				selenium.clickAt("link=Define Permissions",
					RuntimeVariables.replace("Define Permissions"));
				selenium.waitForPageToLoad("30000");
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("label=Media Gallery"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//h3");
				assertEquals(RuntimeVariables.replace("Documents and Media"),
					selenium.getText("//h3"));

				boolean addDocumentChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT']");

				if (addDocumentChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT']",
					RuntimeVariables.replace("Media Gallery Add Document"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT']"));

				boolean addDocumentTypeChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT_TYPE']");

				if (addDocumentTypeChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT_TYPE']",
					RuntimeVariables.replace("Media Gallery Add Document Type"));

			case 3:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT_TYPE']"));

				boolean addFolderChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_FOLDER']");

				if (addFolderChecked) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_FOLDER']",
					RuntimeVariables.replace("Media Gallery Add Folder"));

			case 4:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_FOLDER']"));

				boolean addRepositoryChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_REPOSITORY']");

				if (addRepositoryChecked) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_REPOSITORY']",
					RuntimeVariables.replace("Media Gallery Add Repository"));

			case 5:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_REPOSITORY']"));

				boolean addShortcutChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_SHORTCUT']");

				if (addShortcutChecked) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_SHORTCUT']",
					RuntimeVariables.replace("Media Gallery Add Shortcut"));

			case 6:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_SHORTCUT']"));

				boolean addStructureChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_STRUCTURE']");

				if (addStructureChecked) {
					label = 7;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryADD_STRUCTURE']",
					RuntimeVariables.replace("Media Gallery Add Structure"));

			case 7:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_STRUCTURE']"));

				boolean permissionsChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryPERMISSIONS']");

				if (permissionsChecked) {
					label = 8;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryPERMISSIONS']",
					RuntimeVariables.replace("Media Gallery Permissions"));

			case 8:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryPERMISSIONS']"));

				boolean updateChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryUPDATE']");

				if (updateChecked) {
					label = 9;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryUPDATE']",
					RuntimeVariables.replace("Media Gallery Update"));

			case 9:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryUPDATE']"));

				boolean viewChecked = selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryVIEW']");

				if (viewChecked) {
					label = 10;

					continue;
				}

				selenium.clickAt("//input[@value='com.liferay.portlet.documentlibraryVIEW']",
					RuntimeVariables.replace("Media Gallery View"));

			case 10:
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryVIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("label=Media Gallery"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_DOCUMENT_TYPE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_FOLDER']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_REPOSITORY']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_SHORTCUT']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryADD_STRUCTURE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryPERMISSIONS']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryUPDATE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='com.liferay.portlet.documentlibraryVIEW']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("index=55"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Media Gallery"),
					selenium.getText("//h3"));

				boolean addToPage = selenium.isChecked(
						"//input[@value='31ADD_TO_PAGE']");

				if (addToPage) {
					label = 11;

					continue;
				}

				selenium.clickAt("//input[@value='31ADD_TO_PAGE']",
					RuntimeVariables.replace("Media Gallery Add to Page"));

			case 11:
				assertTrue(selenium.isChecked("//input[@value='31ADD_TO_PAGE']"));

				boolean configuration = selenium.isChecked(
						"//input[@value='31CONFIGURATION']");

				if (configuration) {
					label = 12;

					continue;
				}

				selenium.clickAt("//input[@value='31CONFIGURATION']",
					RuntimeVariables.replace("Media Gallery Configuration"));

			case 12:
				assertTrue(selenium.isChecked(
						"//input[@value='31CONFIGURATION']"));

				boolean permissionsMediaGallery = selenium.isChecked(
						"//input[@value='31PERMISSIONS']");

				if (permissionsMediaGallery) {
					label = 13;

					continue;
				}

				selenium.clickAt("//input[@value='31PERMISSIONS']",
					RuntimeVariables.replace("Media Gallery Permissions"));

			case 13:
				assertTrue(selenium.isChecked("//input[@value='31PERMISSIONS']"));

				boolean viewMediaGallery = selenium.isChecked(
						"//input[@value='31VIEW']");

				if (viewMediaGallery) {
					label = 14;

					continue;
				}

				selenium.clickAt("//input[@value='31VIEW']",
					RuntimeVariables.replace("Media Gallery View"));

			case 14:
				assertTrue(selenium.isChecked("//input[@value='31VIEW']"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"The role permissions were updated."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.select("//select[@id='_128_add-permissions']",
					RuntimeVariables.replace("index=55"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Media Gallery"),
					selenium.getText("//h3"));
				assertTrue(selenium.isChecked("//input[@value='31ADD_TO_PAGE']"));
				assertTrue(selenium.isChecked(
						"//input[@value='31CONFIGURATION']"));
				assertTrue(selenium.isChecked("//input[@value='31PERMISSIONS']"));
				assertTrue(selenium.isChecked("//input[@value='31VIEW']"));

			case 100:
				label = -1;
			}
		}
	}
}