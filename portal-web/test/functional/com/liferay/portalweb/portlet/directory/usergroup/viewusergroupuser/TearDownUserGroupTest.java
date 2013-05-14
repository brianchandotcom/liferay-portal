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

package com.liferay.portalweb.portlet.directory.usergroup.viewusergroupuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownUserGroupTest extends BaseTestCase {
	public void testTearDownUserGroup() throws Exception {
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
				selenium.clickAt("link=User Groups",
					RuntimeVariables.replace("User Groups"));
				selenium.waitForPageToLoad("30000");

				boolean userGroup1Present = selenium.isElementPresent(
						"//input[@name='_127_rowIds']");

				if (!userGroup1Present) {
					label = 7;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]");
				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.waitForPageToLoad("30000");

				boolean user1Assigned = selenium.isVisible(
						"//input[@name='_127_allRowIds']");

				if (!user1Assigned) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");

			case 2:
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_127_TabsBack']"));
				selenium.clickAt("//a[@id='_127_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
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

				boolean userGroup2Present = selenium.isElementPresent(
						"//input[@name='_127_rowIds']");

				if (!userGroup2Present) {
					label = 8;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]");
				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.waitForPageToLoad("30000");

				boolean user2Assigned = selenium.isVisible(
						"//input[@name='_127_allRowIds']");

				if (!user2Assigned) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");

			case 3:
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_127_TabsBack']"));
				selenium.clickAt("//a[@id='_127_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
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

				boolean userGroup3Present = selenium.isElementPresent(
						"//input[@name='_127_rowIds']");

				if (!userGroup3Present) {
					label = 9;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]");
				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.waitForPageToLoad("30000");

				boolean user3Assigned = selenium.isVisible(
						"//input[@name='_127_allRowIds']");

				if (!user3Assigned) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");

			case 4:
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_127_TabsBack']"));
				selenium.clickAt("//a[@id='_127_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
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

				boolean userGroup4Present = selenium.isElementPresent(
						"//input[@name='_127_rowIds']");

				if (!userGroup4Present) {
					label = 10;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]");
				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.waitForPageToLoad("30000");

				boolean user4Assigned = selenium.isVisible(
						"//input[@name='_127_allRowIds']");

				if (!user4Assigned) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");

			case 5:
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_127_TabsBack']"));
				selenium.clickAt("//a[@id='_127_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
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

				boolean userGroup5Present = selenium.isElementPresent(
						"//input[@name='_127_rowIds']");

				if (!userGroup5Present) {
					label = 11;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]");
				assertEquals(RuntimeVariables.replace("Assign Members"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Members')]"));
				selenium.waitForPageToLoad("30000");

				boolean user5Assigned = selenium.isVisible(
						"//input[@name='_127_allRowIds']");

				if (!user5Assigned) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@name='_127_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				selenium.clickAt("//input[@value='Update Associations']",
					RuntimeVariables.replace("Update Associations"));
				selenium.waitForPageToLoad("30000");

			case 6:
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//a[@id='_127_TabsBack']"));
				selenium.clickAt("//a[@id='_127_TabsBack']",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
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

			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 100:
				label = -1;
			}
		}
	}
}