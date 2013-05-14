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

package com.liferay.portalweb.portlet.directory.organizations.viewsuborganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddSuborganizationTest extends BaseTestCase {
	public void testAddSuborganization() throws Exception {
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
				selenium.clickAt("link=Search All Organizations",
					RuntimeVariables.replace("Search All Organizations"));
				selenium.waitForPageToLoad("30000");

				boolean basicVisible = selenium.isVisible(
						"//a[.='\u00ab Basic']");

				if (!basicVisible) {
					label = 2;

					continue;
				}

				selenium.clickAt("//a[.='\u00ab Basic']",
					RuntimeVariables.replace("\u00ab Basic"));

			case 2:
				selenium.type("//input[@name='_125_keywords']",
					RuntimeVariables.replace("Test Organization"));
				selenium.clickAt("//input[@value='Search']",
					RuntimeVariables.replace("Search"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace("Actions"),
					selenium.getText(
						"//span[@title='Actions']/ul/li/strong/a/span"));
				selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
					RuntimeVariables.replace("Actions"));
				selenium.waitForVisible(
					"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Add Regular Organization')]");
				assertEquals(RuntimeVariables.replace(
						"Add Regular Organization"),
					selenium.getText(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Add Regular Organization')]"));
				selenium.click(RuntimeVariables.replace(
						"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Add Regular Organization')]"));
				selenium.waitForPageToLoad("30000");
				selenium.waitForText("//tr[contains(.,'Test Organization')]/td[1]/a",
					"Test Organization");
				assertEquals(RuntimeVariables.replace("Test Organization"),
					selenium.getText(
						"//tr[contains(.,'Test Organization')]/td[1]/a"));
				selenium.type("//input[@id='_125_name']",
					RuntimeVariables.replace("Test Suborganization"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
				selenium.waitForPageToLoad("30000");
				assertEquals(RuntimeVariables.replace(
						"Your request completed successfully."),
					selenium.getText("//div[@class='portlet-msg-success']"));
				assertEquals(RuntimeVariables.replace("Test Suborganization"),
					selenium.getText("//h1[@class='header-title']"));
				assertTrue(selenium.isPartialText(
						"//a[@id='_125_addressesLink']", "Addresses"));
				selenium.clickAt("//a[@id='_125_addressesLink']",
					RuntimeVariables.replace("Addresses"));
				selenium.waitForVisible("//input[@id='_125_addressStreet1_0']");
				selenium.waitForVisible("//input[@id='_125_addressStreet1_0']");
				selenium.type("//input[@id='_125_addressStreet1_0']",
					RuntimeVariables.replace("11111 Main Street USA"));
				selenium.select("//select[@id='_125_addressTypeId0']",
					RuntimeVariables.replace("Billing"));
				selenium.type("//input[@id='_125_addressZip0']",
					RuntimeVariables.replace("90210"));
				selenium.type("//input[@id='_125_addressCity0']",
					RuntimeVariables.replace("Cerritos"));
				selenium.clickAt("//input[@name='_125_addressPrimary']",
					RuntimeVariables.replace("Primary"));
				selenium.waitForPartialText("//select[@id='_125_addressCountryId0']",
					"United States");
				selenium.select("//select[@id='_125_addressCountryId0']",
					RuntimeVariables.replace("United States"));
				selenium.clickAt("//input[@id='_125_addressMailing0Checkbox']",
					RuntimeVariables.replace("Mailing"));
				selenium.waitForPartialText("//select[@id='_125_addressRegionId0']",
					"Florida");
				selenium.select("//select[@id='_125_addressRegionId0']",
					RuntimeVariables.replace("Florida"));
				selenium.clickAt("//input[@value='Save']",
					RuntimeVariables.replace("Save"));
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