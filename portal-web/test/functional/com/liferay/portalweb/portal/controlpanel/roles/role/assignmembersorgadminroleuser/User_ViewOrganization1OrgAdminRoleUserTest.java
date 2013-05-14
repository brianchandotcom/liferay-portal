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

package com.liferay.portalweb.portal.controlpanel.roles.role.assignmembersorgadminroleuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class User_ViewOrganization1OrgAdminRoleUserTest extends BaseTestCase {
	public void testUser_ViewOrganization1OrgAdminRoleUser()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("//div[@id='dockbar']",
			RuntimeVariables.replace("Dockbar"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'liferay/navigation_interaction.js')]");
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
			RuntimeVariables.replace("Organization1"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Users and Organizations"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertEquals(RuntimeVariables.replace("Browse"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'Browse')]"));
		assertEquals(RuntimeVariables.replace("View Organizations"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'View Organizations')]"));
		assertEquals(RuntimeVariables.replace("View Users"),
			selenium.getText(
				"//div[@class='lfr-portlet-toolbar']/span/a[contains(.,'View Users')]"));
		assertTrue(selenium.isVisible(
				"//input[@title='Search Users and Organizations']"));
		assertTrue(selenium.isVisible("//input[@value='Search']"));
		assertEquals(RuntimeVariables.replace(
				"Organizations (Search All Organizations)"),
			selenium.getText(
				"//div[@class='lfr-panel-title']/span[contains(.,'Organizations')]"));
		assertTrue(selenium.isVisible("//input[@value='Delete']"));
		assertEquals(RuntimeVariables.replace("Organization1 Name"),
			selenium.getText(
				"//tr[contains(.,'Organization1 Name')]/td[2]/a/strong"));
		assertEquals(RuntimeVariables.replace("Regular Organization"),
			selenium.getText("//tr[contains(.,'Organization1 Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Organization1 Name')]/td[4]/span/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Organization1 Name')]/td[4]/span/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		assertEquals(RuntimeVariables.replace("Edit"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Edit')]"));
		assertEquals(RuntimeVariables.replace("Assign Organization Roles"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Organization Roles')]"));
		assertEquals(RuntimeVariables.replace("Assign Users"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Users')]"));
		assertEquals(RuntimeVariables.replace("Add User"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Add User')]"));
		assertEquals(RuntimeVariables.replace("Add Regular Organization"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Add Regular Organization')]"));
		assertEquals(RuntimeVariables.replace("Add Location"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Add Location')]"));
		assertEquals(RuntimeVariables.replace("Delete"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Delete')]"));
		assertEquals(RuntimeVariables.replace("Showing 1 result."),
			selenium.getText("//div[@class='search-results']"));
	}
}