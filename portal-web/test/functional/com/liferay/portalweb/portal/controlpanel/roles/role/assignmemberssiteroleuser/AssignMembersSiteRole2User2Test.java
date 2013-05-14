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

package com.liferay.portalweb.portal.controlpanel.roles.role.assignmemberssiteroleuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersSiteRole2User2Test extends BaseTestCase {
	public void testAssignMembersSiteRole2User2() throws Exception {
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
		selenium.type("//input[@id='_134_name']",
			RuntimeVariables.replace("Site"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//tr[contains(.,'Site Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Site Name')]/td[7]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Site Name')]/td[7]/span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]");
		assertEquals(RuntimeVariables.replace("Manage Memberships"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Manage Memberships')]"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Site Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Add Site Roles to"),
			selenium.getText(
				"//span[@title='Add Site Roles to']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Add Site Roles to']/ul/li/strong/a/span",
			RuntimeVariables.replace("Add Site Roles to"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Users')]");
		assertEquals(RuntimeVariables.replace("Users"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Users')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Users')]",
			RuntimeVariables.replace("Users"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_174_keywords']",
			RuntimeVariables.replace("Siterole2"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Roles Siterole2 Name"),
			selenium.getText("//tr[contains(.,'Roles Siterole2 Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Site"),
			selenium.getText("//tr[contains(.,'Roles Siterole2 Name')]/td[2]/a"));
		selenium.clickAt("//tr[contains(.,'Roles Siterole2 Name')]/td[1]/a",
			RuntimeVariables.replace("Roles Siterole2 Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Available"),
			selenium.getText(
				"//ul[@class='tabview-list']/li/span/a[contains(.,'Available')]"));
		selenium.clickAt("//ul[@class='tabview-list']/li/span/a[contains(.,'Available')]",
			RuntimeVariables.replace("Available"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_174_keywords']",
			RuntimeVariables.replace("usersn"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("userfn2 userln2"),
			selenium.getText("//tr[contains(.,'userfn2 userln2')]/td[2]"));
		assertEquals(RuntimeVariables.replace("usersn2"),
			selenium.getText("//tr[contains(.,'userfn2 userln2')]/td[3]"));
		assertFalse(selenium.isChecked(
				"//tr[contains(.,'userfn2 userln2')]/td[1]/input"));
		selenium.check("//tr[contains(.,'userfn2 userln2')]/td[1]/input");
		assertTrue(selenium.isChecked(
				"//tr[contains(.,'userfn2 userln2')]/td[1]/input"));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace("Update Associations"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isChecked(
				"//tr[contains(.,'userfn2 userln2')]/td[1]/input"));
	}
}