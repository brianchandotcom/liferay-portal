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

package com.liferay.portalweb.portal.controlpanel.roles.role.assignmembersorgroleuser;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersOrgRole1User1Test extends BaseTestCase {
	public void testAssignMembersOrgRole1User1() throws Exception {
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
		selenium.type("//input[@id='_125_keywords']",
			RuntimeVariables.replace("Organization Name"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText(
				"//tr[contains(.,'Organization Name')]/td[2]/a/strong"));
		assertEquals(RuntimeVariables.replace("Regular Organization"),
			selenium.getText("//tr[contains(.,'Organization Name')]/td[3]/a"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText(
				"//tr[contains(.,'Organization Name')]/td[4]/span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//tr[contains(.,'Organization Name')]/td[4]/span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Organization Role')]");
		assertEquals(RuntimeVariables.replace("Assign Organization Roles"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Organization Role')]"));
		selenium.clickAt("//div[@class='lfr-menu-list unstyled']/ul/li/a[contains(.,'Assign Organization Role')]",
			RuntimeVariables.replace("Assign Organization Roles"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Organization Name"),
			selenium.getText("//h1[@class='header-title']/span"));
		assertEquals(RuntimeVariables.replace("Roles Orgrole1 Name"),
			selenium.getText("//tr[contains(.,'Roles Orgrole1 Name')]/td[1]/a"));
		assertEquals(RuntimeVariables.replace("Organization"),
			selenium.getText("//tr[contains(.,'Roles Orgrole1 Name')]/td[2]/a"));
		assertEquals(RuntimeVariables.replace(""),
			selenium.getText("//tr[contains(.,'Roles Orgrole1 Name')]/td[3]/a"));
		selenium.clickAt("//tr[contains(.,'Roles Orgrole1 Name')]/td[1]/a",
			RuntimeVariables.replace("Roles Orgrole1 Name"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Available"),
			selenium.getText(
				"//ul[@class='tabview-list']/li/span/a[contains(.,'Available')]"));
		selenium.clickAt("//ul[@class='tabview-list']/li/span/a[contains(.,'Available')]",
			RuntimeVariables.replace("Available"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@name='_125_keywords']",
			RuntimeVariables.replace("usersn1"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("userfn1 userln1"),
			selenium.getText("//tr[contains(.,'userfn1 userln1')]/td[2]"));
		assertEquals(RuntimeVariables.replace("usersn1"),
			selenium.getText("//tr[contains(.,'userfn1 userln1')]/td[3]"));
		assertFalse(selenium.isChecked(
				"//tr[contains(.,'userfn1 userln1')]/td[1]/input"));
		selenium.check("//tr[contains(.,'userfn1 userln1')]/td[1]/input");
		assertTrue(selenium.isChecked(
				"//tr[contains(.,'userfn1 userln1')]/td[1]/input"));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace("Update Associations"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isChecked(
				"//tr[contains(.,'userfn1 userln1')]/td[1]/input"));
	}
}