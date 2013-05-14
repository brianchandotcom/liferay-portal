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

package com.liferay.portalweb.portal.controlpanel.sites.sitepage.addsiteprivatepage;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSitesPrivatePageTest extends BaseTestCase {
	public void testAddSitesPrivatePage() throws Exception {
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
				selenium.clickAt("link=Sites", RuntimeVariables.replace("Sites"));
				selenium.waitForPageToLoad("30000");
				selenium.type("//input[@name='_134_keywords']",
					RuntimeVariables.replace("Site Name"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForText("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Pages')]",
					"Manage Pages");
				assertEquals(RuntimeVariables.replace("Manage Pages"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Pages')]"));
				selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Pages')]",
					RuntimeVariables.replace("Manage Pages"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("link=Private Pages",
					RuntimeVariables.replace("Private Pages"));
				selenium.waitForPageToLoad("30000");
				selenium.clickAt("//div/span/button[1]",
					RuntimeVariables.replace("Add Page"));
				selenium.waitForVisible("//input[@name='_156_name_en_US']");
				selenium.type("//input[@name='_156_name_en_US']",
					RuntimeVariables.replace("Private Page"));
				selenium.clickAt("//input[@value='Add Page']",
					RuntimeVariables.replace("Add Page"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				selenium.clickAt("//div[@class='tree-hitarea']",
					RuntimeVariables.replace("Drop Down Arrow"));
				selenium.waitForElementPresent(
					"xPath=(//a[@class='layout-tree'])[2]");

				boolean pageVisible = selenium.isVisible(
						"xPath=(//a[@class='layout-tree'])[2]");

				if (pageVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//div[@class='tree-hitarea']",
					RuntimeVariables.replace("Drop Down Arrow"));

			case 2:
				selenium.waitForVisible("//li/ul/li/div/div[2]/a");
				assertEquals(RuntimeVariables.replace("Private Page"),
					selenium.getText("//li/ul/li/div/div[2]/a"));

			case 100:
				label = -1;
			}
		}
	}
}