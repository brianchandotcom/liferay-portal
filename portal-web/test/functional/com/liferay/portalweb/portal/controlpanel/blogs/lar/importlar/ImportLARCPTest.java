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

package com.liferay.portalweb.portal.controlpanel.blogs.lar.importlar;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ImportLARCPTest extends BaseTestCase {
	public void testImportLARCP() throws Exception {
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
				selenium.clickAt("link=Blogs", RuntimeVariables.replace("Blogs"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Export / Import')]");
				assertEquals(RuntimeVariables.replace("Export / Import"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Export / Import')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Export / Import')]"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Import",
					RuntimeVariables.replace("Import"));
				selenium.waitForPageToLoad("30000");
				selenium.uploadFile("//input[@id='_86_importFileName']",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\functional\\com\\liferay\\portalweb\\portal\\controlpanel\\blogs\\dependencies\\Selenium-Blogs.portlet.lar"));

				boolean deletePortletDataCheckbox = selenium.isChecked(
						"//input[@id='_86_DELETE_PORTLET_DATACheckbox']");

				if (deletePortletDataCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_DELETE_PORTLET_DATACheckbox']",
					RuntimeVariables.replace(
						"Delete Portlet data before importing Checkbox"));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_86_DELETE_PORTLET_DATACheckbox']"));

				boolean dataCheckbox = selenium.isChecked(
						"//input[@id='_86_PORTLET_DATACheckbox']");

				if (dataCheckbox) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_86_PORTLET_DATACheckbox']",
					RuntimeVariables.replace("Data Checkbox"));

			case 3:
				assertTrue(selenium.isChecked(
						"//input[@id='_86_PORTLET_DATACheckbox']"));
				selenium.clickAt("//input[@value='Import']",
					RuntimeVariables.replace("Import"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-success']");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}