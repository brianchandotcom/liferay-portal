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

package com.liferay.portalweb.portal.controlpanel.sites.lar.importlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportLARTest extends BaseTestCase {
	public void testImportLAR() throws Exception {
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
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@name='_134_keywords']",
					RuntimeVariables.replace("Site Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Pages')]");
				assertEquals(RuntimeVariables.replace("Manage Pages"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Pages')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Pages')]",
					RuntimeVariables.replace("Manage Pages"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Import"),
					selenium.getText(
						"//div[@class='lfr-header-row-content']/div/span/button/span[contains(.,'Import')]"));
				selenium.clickAt("//div[@class='lfr-header-row-content']/div/span/button/span[contains(.,'Import')]",
					RuntimeVariables.replace("Import"));
				selenium.waitForVisible("//iframe");
				selenium.selectFrame("//iframe");
				selenium.waitForVisible("//input[@id='_156_importFileName']");
				selenium.uploadFile("//input[@id='_156_importFileName']",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\functional\\com\\liferay\\portalweb\\portal\\controlpanel\\sites\\lar\\importlar\\dependencies\\Sites_Public_Pages.lar"));

				boolean deleteMissingPagesCheckbox = selenium.isChecked(
						"//input[@id='_156_DELETE_MISSING_LAYOUTSCheckbox']");

				if (deleteMissingPagesCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_156_DELETE_MISSING_LAYOUTSCheckbox']",
					RuntimeVariables.replace("Delete Missing Pages Checkbox"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_156_DELETE_MISSING_LAYOUTSCheckbox']"));

				boolean permissionsCheckbox = selenium.isChecked(
						"//input[@id='_156_PERMISSIONSCheckbox']");

				if (permissionsCheckbox) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_156_PERMISSIONSCheckbox']",
					RuntimeVariables.replace("Permissions Checkbox"));

			case 3:
				assertTrue(selenium.isChecked(
						"//input[@id='_156_PERMISSIONSCheckbox']"));

				boolean categoriesCheckbox = selenium.isChecked(
						"//input[@id='_156_CATEGORIESCheckbox']");

				if (categoriesCheckbox) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@id='_156_CATEGORIESCheckbox']",
					RuntimeVariables.replace("Categories Checkbox"));

			case 4:
				assertTrue(selenium.isChecked(
						"//input[@id='_156_CATEGORIESCheckbox']"));

				boolean deletePortletDataBeforeImportingCheckbox = selenium.isChecked(
						"//input[@id='_156_DELETE_PORTLET_DATACheckbox']");

				if (deletePortletDataBeforeImportingCheckbox) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@id='_156_DELETE_PORTLET_DATACheckbox']",
					RuntimeVariables.replace(
						"Delete portlet data before importing Checkbox"));

			case 5:
				assertTrue(selenium.isChecked(
						"//input[@id='_156_DELETE_PORTLET_DATACheckbox']"));
				selenium.clickAt("//input[@value='Import']",
					RuntimeVariables.replace("Import"));
				selenium.waitForPageToLoad("30000");
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