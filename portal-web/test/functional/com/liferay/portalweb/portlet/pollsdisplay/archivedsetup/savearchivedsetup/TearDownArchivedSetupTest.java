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

package com.liferay.portalweb.portlet.pollsdisplay.archivedsetup.savearchivedsetup;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownArchivedSetupTest extends BaseTestCase {
	public void testTearDownArchivedSetup() throws Exception {
		int label = 1;

		while (label >= 1) {
			switch (label) {
			case 1:
				selenium.selectWindow("null");
				selenium.selectFrame("relative=top");
				selenium.open("/web/guest/home/");
				selenium.clickAt("link=Polls Display Test Page",
					RuntimeVariables.replace("Polls Display Test Page"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Options"),
					selenium.getText("//span[@title='Options']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Options']/ul/li/strong/a",
					RuntimeVariables.replace("Options"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]");
				assertEquals(RuntimeVariables.replace("Configuration"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Configuration')]",
					RuntimeVariables.replace("Configuration"));
				selenium.waitForVisible(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				selenium.selectFrame(
					"//iframe[contains(@id,'configurationIframeDialog')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/navigation_interaction.js')]");
				selenium.waitForVisible(
					"//div[@class='archived-setups']/span/a/span");
				assertEquals(RuntimeVariables.replace("Archive/Restore Setup"),
					selenium.getText(
						"//div[@class='archived-setups']/span/a/span"));
				selenium.clickAt("//div[@class='archived-setups']/span/a/span",
					RuntimeVariables.replace("Archive/Restore Setup"));
				selenium.waitForPageToLoad("30000");

				boolean pdArchivedSetup1Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!pdArchivedSetup1Present) {
					label = 2;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//a[@id='_86_ocerSearchContainer_1_menu_delete']");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//a[@id='_86_ocerSearchContainer_1_menu_delete']"));
				selenium.clickAt("//a[@id='_86_ocerSearchContainer_1_menu_delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean pdArchivedSetup2Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!pdArchivedSetup2Present) {
					label = 3;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//a[@id='_86_ocerSearchContainer_1_menu_delete']");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//a[@id='_86_ocerSearchContainer_1_menu_delete']"));
				selenium.clickAt("//a[@id='_86_ocerSearchContainer_1_menu_delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean pdArchivedSetup3Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!pdArchivedSetup3Present) {
					label = 4;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//a[@id='_86_ocerSearchContainer_1_menu_delete']");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//a[@id='_86_ocerSearchContainer_1_menu_delete']"));
				selenium.clickAt("//a[@id='_86_ocerSearchContainer_1_menu_delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean pdArchivedSetup4Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!pdArchivedSetup4Present) {
					label = 5;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//a[@id='_86_ocerSearchContainer_1_menu_delete']");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//a[@id='_86_ocerSearchContainer_1_menu_delete']"));
				selenium.clickAt("//a[@id='_86_ocerSearchContainer_1_menu_delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

				boolean pdArchivedSetup5Present = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!pdArchivedSetup5Present) {
					label = 6;

					continue;
				}

				Thread.sleep(5000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//a[@id='_86_ocerSearchContainer_1_menu_delete']");
				assertEquals(RuntimeVariables.replace("Delete"),
					selenium.getText(
						"//a[@id='_86_ocerSearchContainer_1_menu_delete']"));
				selenium.clickAt("//a[@id='_86_ocerSearchContainer_1_menu_delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				assertTrue(selenium.getConfirmation()
								   .matches("^Are you sure you want to delete this[\\s\\S] It will be deleted immediately.$"));

			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}