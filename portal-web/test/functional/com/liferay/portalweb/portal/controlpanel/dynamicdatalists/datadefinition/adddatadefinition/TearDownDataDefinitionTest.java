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

package com.liferay.portalweb.portal.controlpanel.dynamicdatalists.datadefinition.adddatadefinition;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownDataDefinitionTest extends BaseTestCase {
	public void testTearDownDataDefinition() throws Exception {
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
				selenium.clickAt("link=Dynamic Data Lists",
					RuntimeVariables.replace("Dynamic Data Lists"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Manage Data Definitions"),
					selenium.getText(
						"//span[@class='lfr-toolbar-button view-structures ']/a"));
				selenium.clickAt("//span[@class='lfr-toolbar-button view-structures ']/a",
					RuntimeVariables.replace("Manage Data Definitions"));
				selenium.waitForVisible(
					"//iframe[contains(@src,'dynamicdatalists')]");
				selenium.selectFrame(
					"//iframe[contains(@src,'dynamicdatalists')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/aui/event-tap/event-tap-min.js')]");
				selenium.waitForVisible("//input[@name='_166_keywords']");
				selenium.type("//input[@name='_166_keywords']",
					RuntimeVariables.replace("Data Definition"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean allRowsCheckbox = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!allRowsCheckbox) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='_166_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_166_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}