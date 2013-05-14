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

package com.liferay.portalweb.portal.controlpanel.roles.role.addregrole;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownRoleTest extends BaseTestCase {
	public void testTearDownRole() throws Exception {
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
					RuntimeVariables.replace("Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");

				boolean role1Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!role1Present) {
					label = 6;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");

				boolean role2Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!role2Present) {
					label = 5;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");

				boolean role3Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!role3Present) {
					label = 4;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");

				boolean role4Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!role4Present) {
					label = 3;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");

				boolean role5Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!role5Present) {
					label = 2;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
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