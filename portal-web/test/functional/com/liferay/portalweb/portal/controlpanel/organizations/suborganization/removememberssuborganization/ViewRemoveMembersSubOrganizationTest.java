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

package com.liferay.portalweb.portal.controlpanel.organizations.suborganization.removememberssuborganization;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewRemoveMembersSubOrganizationTest extends BaseTestCase {
	public void testViewRemoveMembersSubOrganization()
		throws Exception {
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
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("Suborganization*"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Suborganization Name"),
			selenium.getText(
				"//tr[contains(.,'Suborganization Name')]/td[2]/a/strong"));
		assertEquals(RuntimeVariables.replace("Regular Organization"),
			selenium.getText("//tr[contains(.,'Suborganization Name')]/td[3]/a"));
		selenium.clickAt("//tr[contains(.,'Suborganization Name')]/td[2]/a/strong",
			RuntimeVariables.replace("Suborganization Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Users and Organizations"),
			selenium.getText("//h1[@class='portlet-title']/span"));
		assertEquals(RuntimeVariables.replace("Browse"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'Browse')]"));
		assertEquals(RuntimeVariables.replace("View Organizations"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'View Organizations')]"));
		assertEquals(RuntimeVariables.replace("View Users"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'View Users')]"));
		assertEquals(RuntimeVariables.replace("Add"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span/ul/li/strong/a[contains(.,'Add')]"));
		assertEquals(RuntimeVariables.replace("Export Users"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'Export Users')]"));
		assertEquals(RuntimeVariables.replace("Suborganization Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace(
				"\u00ab Back to Organization Name"),
			selenium.getText("//span[@class='header-back-to']/a"));
		assertFalse(selenium.isTextPresent("1 User"));
		assertTrue(selenium.isElementNotPresent("//input[@value='Deactivate']"));
		assertFalse(selenium.isTextPresent("userfn"));
		assertFalse(selenium.isTextPresent("userln"));
		assertFalse(selenium.isTextPresent("usersn"));
		assertEquals(RuntimeVariables.replace(
				"This organization does not have any users."),
			selenium.getText("//div[@class='portlet-msg-info']"));
		assertTrue(selenium.isVisible("//div[@class='lfr-asset-summary']/img"));
		assertEquals(RuntimeVariables.replace("Suborganization Name"),
			selenium.getText("//div[@class='lfr-asset-summary']/div/h4"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Edit')]"));
		assertEquals(RuntimeVariables.replace("Assign Organization Roles"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Organization Roles')]"));
		assertEquals(RuntimeVariables.replace("Assign Users"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Assign Users')]"));
		assertEquals(RuntimeVariables.replace("Add User"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add User')]"));
		assertEquals(RuntimeVariables.replace("Add Regular Organization"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Regular Organization')]"));
		assertEquals(RuntimeVariables.replace("Add Location"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Add Location')]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list lfr-menu-expanded align-right null']/ul/li/a[contains(.,'Delete')]"));
	}
}