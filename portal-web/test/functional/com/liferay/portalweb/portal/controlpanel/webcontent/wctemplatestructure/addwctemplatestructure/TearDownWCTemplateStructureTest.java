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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.addwctemplatestructure;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class TearDownWCTemplateStructureTest extends BaseTestCase {
	public void testTearDownWCTemplateStructure() throws Exception {
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
				selenium.clickAt("link=Web Content",
					RuntimeVariables.replace("Web Content"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Manage"),
					selenium.getText("//span[@title='Manage']/ul/li/strong/a"));
				selenium.clickAt("//span[@title='Manage']/ul/li/strong/a",
					RuntimeVariables.replace("Manage"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]");
				assertEquals(RuntimeVariables.replace("Structures"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Structures')]",
					RuntimeVariables.replace("Structures"));
				selenium.waitForVisible(
					"//iframe[contains(@src,'scopeStructureType')]");
				selenium.selectFrame(
					"//iframe[contains(@src,'scopeStructureType')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/store.js')]");
				Thread.sleep(1000);

				boolean structure1Present = selenium.isElementPresent(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[1]");

				if (!structure1Present) {
					label = 7;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[1]"));
				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a)[1]",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]");
				assertEquals(RuntimeVariables.replace("Manage Templates"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]",
					RuntimeVariables.replace("Manage Templates"));
				selenium.waitForPageToLoad("30000");

				boolean structure1TemplatePresent = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!structure1TemplatePresent) {
					label = 2;

					continue;
				}

				selenium.clickAt("//input[@name='_166_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_166_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 2:
				assertEquals(RuntimeVariables.replace("There are no templates."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//span[@class='header-back-to']/a"));
				selenium.clickAt("//span[@class='header-back-to']/a",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);

				boolean structure2Present = selenium.isElementPresent(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[2]");

				if (!structure2Present) {
					label = 8;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[2]"));
				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a)[2]",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]");
				assertEquals(RuntimeVariables.replace("Manage Templates"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]",
					RuntimeVariables.replace("Manage Templates"));
				selenium.waitForPageToLoad("30000");

				boolean structure2TemplatePresent = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!structure2TemplatePresent) {
					label = 3;

					continue;
				}

				selenium.clickAt("//input[@name='_166_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_166_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 3:
				assertEquals(RuntimeVariables.replace("There are no templates."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//span[@class='header-back-to']/a"));
				selenium.clickAt("//span[@class='header-back-to']/a",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);

				boolean structure3Present = selenium.isElementPresent(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[3]");

				if (!structure3Present) {
					label = 9;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[3]"));
				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a)[3]",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]");
				assertEquals(RuntimeVariables.replace("Manage Templates"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]",
					RuntimeVariables.replace("Manage Templates"));
				selenium.waitForPageToLoad("30000");

				boolean structure3TemplatePresent = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!structure3TemplatePresent) {
					label = 4;

					continue;
				}

				selenium.clickAt("//input[@name='_166_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_166_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 4:
				assertEquals(RuntimeVariables.replace("There are no templates."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//span[@class='header-back-to']/a"));
				selenium.clickAt("//span[@class='header-back-to']/a",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);

				boolean structure4Present = selenium.isElementPresent(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[4]");

				if (!structure4Present) {
					label = 10;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[4]"));
				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a)[4]",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]");
				assertEquals(RuntimeVariables.replace("Manage Templates"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]",
					RuntimeVariables.replace("Manage Templates"));
				selenium.waitForPageToLoad("30000");

				boolean structure4TemplatePresent = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!structure4TemplatePresent) {
					label = 5;

					continue;
				}

				selenium.clickAt("//input[@name='_166_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_166_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 5:
				assertEquals(RuntimeVariables.replace("There are no templates."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertEquals(RuntimeVariables.replace("\u00ab Back"),
					selenium.getText("//span[@class='header-back-to']/a"));
				selenium.clickAt("//span[@class='header-back-to']/a",
					RuntimeVariables.replace("\u00ab Back"));
				selenium.waitForPageToLoad("30000");
				Thread.sleep(1000);

				boolean structure5Present = selenium.isElementPresent(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[5]");

				if (!structure5Present) {
					label = 11;

					continue;
				}

				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"xPath=(//span[@title='Actions']/ul/li/strong/a)[5]"));
				selenium.clickAt("xPath=(//span[@title='Actions']/ul/li/strong/a)[5]",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]");
				assertEquals(RuntimeVariables.replace("Manage Templates"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]",
					RuntimeVariables.replace("Manage Templates"));
				selenium.waitForPageToLoad("30000");

				boolean structure5TemplatePresent = selenium.isElementPresent(
						"//span[@title='Actions']/ul/li/strong/a/span");

				if (!structure5TemplatePresent) {
					label = 6;

					continue;
				}

				selenium.clickAt("//input[@name='_166_allRowIds']",
					RuntimeVariables.replace("All Rows"));
				assertTrue(selenium.isChecked("//input[@name='_166_allRowIds']"));
				selenium.clickAt("//input[@value='Delete']",
					RuntimeVariables.replace("Delete"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForConfirmation(
					"Are you sure you want to delete this? It will be deleted immediately.");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));

			case 6:
				assertEquals(RuntimeVariables.replace("There are no templates."),
					selenium.getText("//div[@class='portlet-msg-info']"));

			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}