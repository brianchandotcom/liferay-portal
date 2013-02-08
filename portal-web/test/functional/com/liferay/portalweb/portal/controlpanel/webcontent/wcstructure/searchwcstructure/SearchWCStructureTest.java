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

package com.liferay.portalweb.portal.controlpanel.webcontent.wcstructure.searchwcstructure;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchWCStructureTest extends BaseTestCase {
	public void testSearchWCStructure() throws Exception {
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
					"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]");
				assertEquals(RuntimeVariables.replace("Structures"),
					selenium.getText(
						"//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]"));
				selenium.clickAt("//div[@class='lfr-component lfr-menu-list']/ul/li/a[contains(.,'Structures')]",
					RuntimeVariables.replace("Structures"));
				selenium.waitForVisible("//iframe[contains(@src,'Structures')]");
				selenium.selectFrame("//iframe[contains(@src,'Structures')]");
				selenium.waitForElementPresent(
					"//script[contains(@src,'/liferay/store.js')]");

				boolean basicVisible = selenium.isVisible(
						"//a[contains(.,'\u00ab Basic')]");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[contains(.,'\u00ab Basic')]",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.waitForVisible("//input[@name='_166_keywords']");
				selenium.type("//input[@name='_166_keywords']",
					RuntimeVariables.replace("Structure"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForVisible(
					"//tr[contains(.,'WC Structure Name')]/td[3]/a");
				assertEquals(RuntimeVariables.replace("WC Structure Name"),
					selenium.getText(
						"//tr[contains(.,'WC Structure Name')]/td[3]/a"));
				selenium.type("//input[@name='_166_keywords']",
					RuntimeVariables.replace("Structure1"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForVisible("//div[@class='portlet-msg-info']");
				assertEquals(RuntimeVariables.replace("There are no results."),
					selenium.getText("//div[@class='portlet-msg-info']"));
				assertFalse(selenium.isTextPresent("WC Structure Name"));
				selenium.type("//input[@name='_166_keywords']",
					RuntimeVariables.replace(""));
				assertEquals(RuntimeVariables.replace("View All"),
					selenium.getText(
						"//span[@class='lfr-toolbar-button view-button current']/a"));
				selenium.clickAt("//span[@class='lfr-toolbar-button view-button current']/a",
					RuntimeVariables.replace("View All"));
				selenium.waitForPageToLoad("30000");
				selenium.selectFrame("relative=top");

			case 100:
				label = -1;
			}
		}
	}
}