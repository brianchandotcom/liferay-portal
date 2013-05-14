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
public class ViewRegRoleTest extends BaseTestCase {
	public void testViewRegRole() throws Exception {
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
		selenium.type("//input[@name='_128_keywords']",
			RuntimeVariables.replace("Regrole"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Roles Regrole Name"),
			selenium.getText("//tr[contains(.,'Roles Regrole Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Regular"),
			selenium.getText("//tr[contains(.,'Roles Regrole Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]");
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]",
			RuntimeVariables.replace("Edit"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Roles Regrole Name"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("\u00ab Back"),
			selenium.getText("//a[@id='_128_TabsBack']"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[contains(.,'Edit')]/ul[@class='tabview-list']/li[1]/span/a"));
		assertEquals(RuntimeVariables.replace("Define Permissions"),
			selenium.getText(
				"//div[contains(.,'Define Permissions')]/ul[@class='tabview-list']/li[2]/span/a"));
		assertEquals(RuntimeVariables.replace("Type"),
			selenium.getText("//div[contains(.,'Type')]/label"));
		assertTrue(selenium.isPartialText(
				"//fieldset[contains(.,'Regular')]/div/div", "Regular"));
		assertEquals(RuntimeVariables.replace("New Name (Required)"),
			selenium.getText("//span[contains(.,'New Name (Required)')]/label"));
		assertEquals("Roles Regrole Name",
			selenium.getValue("//input[@id='_128_name']"));
		assertEquals(RuntimeVariables.replace("Title"),
			selenium.getText("//span[contains(.,'Title')]/label"));
		assertEquals("", selenium.getValue("//input[@id='_128_title_en_US']"));
		assertEquals(RuntimeVariables.replace("Description"),
			selenium.getText("//span[contains(.,'Description')]/label"));
		assertEquals("",
			selenium.getValue("//textarea[@id='_128_description_en_US']"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
	}
}