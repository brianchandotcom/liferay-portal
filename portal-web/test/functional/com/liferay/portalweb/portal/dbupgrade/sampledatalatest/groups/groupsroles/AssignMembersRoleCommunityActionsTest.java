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

package com.liferay.portalweb.portal.dbupgrade.sampledatalatest.groups.groupsroles;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AssignMembersRoleCommunityActionsTest extends BaseTestCase {
	public void testAssignMembersRoleCommunityActions()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
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
			RuntimeVariables.replace("RolesGroupsRolesName"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("RolesGroupsRolesName"),
			selenium.getText("//tr[3]/td[1]"));
		assertEquals(RuntimeVariables.replace("Actions"),
			selenium.getText("//span[@title='Actions']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Actions']/ul/li/strong/a/span",
			RuntimeVariables.replace("Actions"));
		selenium.waitForVisible(
			"//div[@class='lfr-menu-list unstyled']/ul/li[4]/a");
		assertEquals(RuntimeVariables.replace("Assign Members"),
			selenium.getText(
				"//div[@class='lfr-menu-list unstyled']/ul/li[4]/a"));
		selenium.click(RuntimeVariables.replace(
				"//div[@class='lfr-menu-list unstyled']/ul/li[4]/a"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Sites"),
			selenium.getText("//form/ul[1]/li[2]/span/a"));
		selenium.clickAt("//form/ul[1]/li[2]/span/a",
			RuntimeVariables.replace("Sites"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Available", RuntimeVariables.replace("Available"));
		selenium.waitForPageToLoad("30000");
		selenium.type("//input[@id='_128_name']",
			RuntimeVariables.replace("Community Groups Roles"));
		selenium.clickAt("//input[@value='Search']",
			RuntimeVariables.replace("Search"));
		selenium.waitForPageToLoad("30000");
		assertFalse(selenium.isChecked("//input[@name='_128_rowIds']"));
		selenium.clickAt("//input[@name='_128_rowIds']",
			RuntimeVariables.replace("Checkbox"));
		selenium.clickAt("//input[@value='Update Associations']",
			RuntimeVariables.replace("Update Associations"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div[@class='portlet-msg-success']");
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isChecked("//input[@name='_128_rowIds']"));
		assertEquals(RuntimeVariables.replace("Community Groups Roles"),
			selenium.getText("//tr[3]/td[2]"));
	}
}