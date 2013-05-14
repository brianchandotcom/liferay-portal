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

package com.liferay.portalweb.portlet.pagecomments.lar.importlar;

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
				selenium.clickAt("link=Page Comments Test Page",
					RuntimeVariables.replace("Page Comments Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Export / Import')]");
				assertEquals(RuntimeVariables.replace("Export / Import"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Export / Import')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Export / Import')]",
					RuntimeVariables.replace("Export / Import"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Import"),
					selenium.getText("link=Import"));
				selenium.clickAt("link=Import",
					RuntimeVariables.replace("Import"));
				selenium.waitForPageToLoad("30000");
				selenium.uploadFile("//input[@id='_86_importFileName']",
					RuntimeVariables.replace(
						"L:\\portal\\build\\portal-web\\test\\functional\\com\\liferay\\portalweb\\portlet\\pagecomments\\lar\\importlar\\dependencies\\Page_Comments-Selenium.portlet.lar"));

				boolean deletePortletDataNotChecked = selenium.isChecked(
						"//input[@id='_86_DELETE_PORTLET_DATACheckbox']");

				if (deletePortletDataNotChecked) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@id='_86_DELETE_PORTLET_DATACheckbox']",
					RuntimeVariables.replace(
						"Delete portlet data before importing."));

			case 2:
				assertTrue(selenium.isChecked(
						"//input[@id='_86_DELETE_PORTLET_DATACheckbox']"));

				boolean dataNotChecked = selenium.isChecked(
						"//input[@id='_86_PORTLET_DATACheckbox']");

				if (dataNotChecked) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@id='_86_PORTLET_DATACheckbox']",
					RuntimeVariables.replace("Data"));

			case 3:
				assertTrue(selenium.isChecked(
						"//input[@id='_86_PORTLET_DATACheckbox']"));
				selenium.clickAt("//input[@value='Import']",
					RuntimeVariables.replace("Import"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 100:
				label = -1;
			}
		}
	}
}