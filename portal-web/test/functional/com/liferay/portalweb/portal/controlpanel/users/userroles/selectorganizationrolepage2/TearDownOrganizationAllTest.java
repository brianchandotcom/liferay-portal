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

package com.liferay.portalweb.portal.controlpanel.users.userroles.selectorganizationrolepage2;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownOrganizationAllTest extends BaseTestCase {
	public void testTearDownOrganizationAll() throws Exception {
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
				selenium.clickAt("link=Users and Organizations",
					RuntimeVariables.replace("Users and Organizations"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("organization"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean organization1Present = selenium.isElementPresent(
						"//input[@name='_125_rowIds']");

				if (!organization1Present) {
					label = 6;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_125_allRowIds']"));
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("Select All"));
				assertTrue(selenium.isChecked("//input[@name='_125_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("organization"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean organization2Present = selenium.isElementPresent(
						"//input[@name='_125_rowIds']");

				if (!organization2Present) {
					label = 5;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_125_allRowIds']"));
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("Select All"));
				assertTrue(selenium.isChecked("//input[@name='_125_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("organization"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean organization3Present = selenium.isElementPresent(
						"//input[@name='_125_rowIds']");

				if (!organization3Present) {
					label = 4;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_125_allRowIds']"));
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("Select All"));
				assertTrue(selenium.isChecked("//input[@name='_125_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("organization"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean organization4Present = selenium.isElementPresent(
						"//input[@name='_125_rowIds']");

				if (!organization4Present) {
					label = 3;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_125_allRowIds']"));
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("Select All"));
				assertTrue(selenium.isChecked("//input[@name='_125_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				selenium.type("//input[@id='_125_keywords']",
					RuntimeVariables.replace("organization"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean organization5Present = selenium.isElementPresent(
						"//input[@name='_125_rowIds']");

				if (!organization5Present) {
					label = 2;

					continue;
				}

				assertFalse(selenium.isChecked(
						"//input[@name='_125_allRowIds']"));
				selenium.clickAt("//input[@name='_125_allRowIds']",
					RuntimeVariables.replace("Select All"));
				assertTrue(selenium.isChecked("//input[@name='_125_allRowIds']"));
				selenium.click(RuntimeVariables.replace(
						"//input[@value='Delete']"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 100:
				label = -1;
			}
		}
	}
}