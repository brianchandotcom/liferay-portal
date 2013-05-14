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

package com.liferay.portalweb.portal.controlpanel.webcontent.wctemplatestructure.advancedsearchwctemplatestructure;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AdvancedSearchWCTemplateStructureNameTest extends BaseTestCase {
	public void testAdvancedSearchWCTemplateStructureName()
		throws Exception {
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
				selenium.waitForVisible("//iframe[contains(@src,'Structures')]");
				selenium.selectFrame("//iframe[contains(@src,'Structures')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/store.js')]");
				selenium.waitForVisible("//input[@name='_166_keywords']");
				selenium.type("//input[@name='_166_keywords']",
					RuntimeVariables.replace("WC Structure Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//tr[contains(.,'WC Structure Name')]/td[3]/a");
				assertEquals(RuntimeVariables.replace("WC Structure Name"),
					selenium.getText(
						"//tr[contains(.,'WC Structure Name')]/td[3]/a"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//tr[contains(.,'WC Structure Name')]/td[6]/span[@title='Actions']/ul/li/strong/a"));
				selenium.clickAt("//tr[contains(.,'WC Structure Name')]/td[6]/span[@title='Actions']/ul/li/strong/a",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]");
				assertEquals(RuntimeVariables.replace("Manage Templates"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Templates')]",
					RuntimeVariables.replace("Manage Templates"));
				selenium.waitForPageToLoad("30000");

				boolean advancedVisible = selenium.isVisible(
						"//a[.='Advanced \u00bb']");

				if (!advancedVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='Advanced \u00bb']",
					RuntimeVariables.replace("Advanced \u00bb"));

			case 2:
				selenium.waitForVisible(
					"//div[@class=\"taglib-search-toggle-advanced\"]");
				selenium.waitForVisible("//input[@id='_166_name']");
				selenium.type("//input[@id='_166_name']",
					RuntimeVariables.replace("Name"));
				selenium.clickAt("xPath=(//input[@value='Search'])[2]",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible(
					"//tr[contains(.,'WC Template Structure Name')]/td[3]/a");
				assertEquals(RuntimeVariables.replace(
						"WC Template Structure Name"),
					selenium.getText(
						"//tr[contains(.,'WC Template Structure Name')]/td[3]/a"));
				selenium.type("//input[@id='_166_name']",
					RuntimeVariables.replace("Name1"));
				selenium.clickAt("xPath=(//input[@value='Search'])[2]",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForVisible("//div[@class='portlet-msg-info']");
				assertEquals(RuntimeVariables.replace("There are no templates."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertFalse(selenium.isTextPresent("WC Template Structure Name"));
				Thread.sleep(1000);
				assertEquals(RuntimeVariables.replace("\u00ab Basic"),
					selenium.getText("//a[contains(.,'\u00ab Basic')]"));
				selenium.clickAt("//a[contains(.,'\u00ab Basic')]",
					RuntimeVariables.replace("\u00ab Basic"));
				selenium.waitForVisible(
					"//div[@class=\"taglib-search-toggle-basic\"]");
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}