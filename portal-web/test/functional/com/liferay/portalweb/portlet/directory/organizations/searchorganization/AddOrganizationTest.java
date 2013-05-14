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

package com.liferay.portalweb.portlet.directory.organizations.searchorganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddOrganizationTest extends BaseTestCase {
	public void testAddOrganization() throws Exception {
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
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText("//span[@title='Add']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Regular Organization')]");
		assertEquals(RuntimeVariables.replace("Regular Organization"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Regular Organization')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Regular Organization')]"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_125_name']",
			RuntimeVariables.replace("Test Organization"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertEquals(RuntimeVariables.replace("Test Organization"),
			selenium.getText("//h1[@class='header-title']"));
		assertTrue(selenium.isPartialText("//a[@id='_125_addressesLink']",
				"Addresses"));
		selenium.clickAt("//a[@id='_125_addressesLink']",
			RuntimeVariables.replace("Addresses"));
		selenium.waitForVisible("//input[contains(@id,'addressStreet1')]");
		selenium.type("//input[contains(@id,'addressStreet1')]",
			RuntimeVariables.replace("12345 Test Street"));
		selenium.select("//select[contains(@id,'addressType')]",
			RuntimeVariables.replace("Billing"));
		selenium.type("//input[contains(@id,'addressZip')]",
			RuntimeVariables.replace("11111"));
		selenium.type("//input[contains(@id,'addressCity')]",
			RuntimeVariables.replace("Diamond Bar"));
		selenium.clickAt("//input[contains(@id,'addressPrimary')]",
			RuntimeVariables.replace("Primary"));
		selenium.waitForPartialText("//select[contains(@id,'addressCountry')]",
			"United States");
		selenium.select("//select[contains(@id,'addressCountry')]",
			RuntimeVariables.replace("United States"));
		selenium.clickAt("//input[contains(@id,'addressMailing') and contains(@type,'checkbox')]",
			RuntimeVariables.replace("Mailing"));
		selenium.waitForPartialText("//select[contains(@id,'addressRegion')]",
			"California");
		selenium.select("//select[contains(@id,'addressRegion')]",
			RuntimeVariables.replace("California"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
	}
}